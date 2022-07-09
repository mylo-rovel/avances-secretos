/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Evento;
import cl.ucn.fondef.sata.mini.model.Secuencia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Core dao simulacion.
 */
@Slf4j
@Repository
@Transactional
public class CoreDaoSimulacionImpl implements CoreDaoSimulacion {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CoreDaoEquipoImpl coreDaoEquipo;

    @Autowired
    CoreDaoUsuario coreDaoUsuario;

    private Domain.Secuencia createSecuenciaGrpc(long idComponente, List<Evento> listaEventosDB) {
        Domain.Secuencia.Builder secuenciaEnviar = Domain.Secuencia.newBuilder();
        for (int i = 0; i < listaEventosDB.size(); i++){
            Evento eventoDB = listaEventosDB.get(i);
            Domain.Evento eventoEnviar = Domain.Evento.newBuilder()
                    .setIntensidad(eventoDB.getIntensidad())
                    .setDuracion(eventoDB.getDuracion())
                    .setPosicion(eventoDB.getPosicion()).build();
            secuenciaEnviar.addEvento(eventoEnviar);
            secuenciaEnviar.setIdComponente(idComponente);
        }
        return secuenciaEnviar.build();
    }
    @Override
    public List<Domain.Secuencia> getGrpcSecuenciasSimulacion(IdElementoReq idSimulacionReq) {
        String secuenciasQuery = "SELECT secuencia FROM Secuencia as secuencia WHERE secuencia.idSimulacion = :idSimulacion";
        List <Secuencia> listaSecuenciasDB = entityManager.createQuery(secuenciasQuery).setParameter("idSimulacion", idSimulacionReq.getId()).getResultList();
        if(listaSecuenciasDB.isEmpty()) {
            log.warn("getGrpcSecuenciasSimulacion: La lista no contiene elementos");
            return null;
        }
        List<Domain.Secuencia> listaSecuenciasGrpc= new ArrayList<>();
        for (int i = 0; i < listaSecuenciasDB.size(); i++) {
            long idSecuencia = listaSecuenciasDB.get(i).getId();
            long idComponente = listaSecuenciasDB.get(i).getIdComponente();
            String eventosQuery = "SELECT evento FROM Evento as evento WHERE evento.idSecuencia = :idSecuencia";
            List<Evento> listaEventosDB = entityManager.createQuery(eventosQuery).setParameter("idSecuencia", idSecuencia).getResultList();
            listaSecuenciasGrpc.add(this.createSecuenciaGrpc(idComponente, listaEventosDB));
        }
        return listaSecuenciasGrpc;
    }




    @Override
    public Simulacion getSimulacionDB(Domain.IdElementoReq idSimulacionReq){
        String sqlQuery = "SELECT simulacion FROM Simulacion as simulacion WHERE simulacion.id = :idSimulacion";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("idSimulacion", idSimulacionReq.getId()).getResultList();
        if(listaResultado.isEmpty()) {
            log.warn("getSimulacion: La lista no contiene elementos");
            return null;
        }
        return (Simulacion) listaResultado.get(0);
    }




    @Override
    public List<Simulacion> getSimulaciones(){
        String sqlQuery = "SELECT simulacion FROM Simulacion as simulacion";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            log.warn("getSimulaciones: La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }




    @Override
    public String addSimulacion(SimulacionReq simulacionReq) {
        Usuario usuarioOperador = coreDaoUsuario.getUsuario(    Domain.RutEntityReq.newBuilder().setRut(simulacionReq.getRutOperador()).build());
        Equipo equipoUsado = coreDaoEquipo.getEquipoPorNombre(  simulacionReq.getNombreEquipo());
        if (equipoUsado == null ) {return "Simulacion no guardada";}

        Simulacion simulacionGuardar = new Simulacion();
        simulacionGuardar.setNombre(        simulacionReq.getNombre());
        simulacionGuardar.setDescripcion(   simulacionReq.getDescripcion());
        simulacionGuardar.setIdOperador(    usuarioOperador.getId());
        simulacionGuardar.setIdEquipo(      equipoUsado.getId());
        entityManager.persist(simulacionGuardar);

        long idSimulacion = simulacionGuardar.getId();

        List<Domain.Secuencia> listaSecuencias = simulacionReq.getSecuenciaList();
        for (int i = 0; i < listaSecuencias.size(); i++) {
            long idComponente = listaSecuencias.get(i).getIdComponente();

            Secuencia secuenciaGuardar = new Secuencia();
            secuenciaGuardar.setIdComponente(idComponente);
            secuenciaGuardar.setIdSimulacion(idSimulacion);
            entityManager.persist(secuenciaGuardar);
            long idSecuencia = secuenciaGuardar.getId();

            List<Domain.Evento> listaEventos = listaSecuencias.get(i).getEventoList();
            for (int j = 0; j < listaEventos.size(); j ++) {
                Evento eventoGuardar = new Evento();
                eventoGuardar.setIntensidad(    listaEventos.get(j).getIntensidad());
                eventoGuardar.setDuracion(      listaEventos.get(j).getDuracion());
                eventoGuardar.setPosicion(      listaEventos.get(j).getPosicion());
                eventoGuardar.setIdSecuencia(   idSecuencia);
                entityManager.persist(eventoGuardar);
            }
        }
        return "Simulacion guardada";
    }



    private List<Secuencia> getListaIdsSecuencias (Domain.IdElementoReq idSimulacionReq) {
        String sqlQuery = "SELECT secuencia FROM Secuencia as secuencia WHERE secuencia.idSimulacion = :idSimulacion";
        List listaResultado = entityManager.createQuery(sqlQuery).setParameter("idSimulacion", idSimulacionReq.getId()).getResultList();
        if(listaResultado.isEmpty()){
            log.warn("getSimulaciones: La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }
    private void addMultiEjecucionSecuencia(long idEjecucion, Domain.IdElementoReq idSimulacionReq) {
        List<Secuencia> listaIdsSecuencias = this.getListaIdsSecuencias(idSimulacionReq);
        if (listaIdsSecuencias != null) {
            for (int i = 0; i < listaIdsSecuencias.size(); i++) {
                EjecucionSecuencia ejecucionSecuenciaGuardar = new EjecucionSecuencia();
                ejecucionSecuenciaGuardar.setIdEjecucion(idEjecucion);
                ejecucionSecuenciaGuardar.setIdSecuencia(listaIdsSecuencias.get(i).getId());
                entityManager.persist(ejecucionSecuenciaGuardar);
            }
        }
    }
    @Override
    public String startSimulacion(StartSimulacionReq startSimulacionReq) {
        // todo: guardar en Ejecucion y EjecucionSecuencia
        Domain.IdElementoReq idSimulacionReq = IdElementoReq.newBuilder().setId(startSimulacionReq.getIdSimulacion()).build();
        Simulacion simulacionEjecutar = this.getSimulacionDB(idSimulacionReq);
        if (simulacionEjecutar == null) { return "Simulacion no existente"; }

        Ejecucion ejecucionNueva = new Ejecucion();
        ejecucionNueva.setIdSimulacion(startSimulacionReq.getIdSimulacion());
        ejecucionNueva.setAguaCaida(0.0);
        entityManager.persist(ejecucionNueva);
        long idEjecucion = ejecucionNueva.getId();
        this.addMultiEjecucionSecuencia(idEjecucion, idSimulacionReq);

        // ENVIAR LAS SECUENCIAS AL RASPI
        List<Domain.Secuencia> listaSecuenciasGrpc = this.getGrpcSecuenciasSimulacion(idSimulacionReq);
        Domain.SimulacionBoardReq simulacionBoardReq = SimulacionBoardReq.newBuilder()
                .setIdSimulacion(idSimulacionReq.getId())
                .addAllSecuencia(listaSecuenciasGrpc)
                .build();

        return "Simulacion iniciada. IdEjecucion" + ejecucionNueva.getId();
    }





    @Override
    public Ejecucion getEjecucionDB(Domain.IdElementoReq idSimulacionReq) {
        String sqlQuery = "SELECT ejecucion FROM Ejecucion as ejecucion WHERE ejecucion.idSimulacion = :idSimulacion";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("idSimulacion", idSimulacionReq.getId()).getResultList();
        if(listaResultado.isEmpty()) {
            log.warn("getEjecucionDB: La lista no contiene elementos");
            return null;
        }
        return (Ejecucion) listaResultado.get(0);
    }

    @Override
    public List<Ejecucion> getEjecucionesDB() {
        String sqlQuery = "SELECT ejecucion FROM Ejecucion as ejecucion";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();
        if(listaResultado.isEmpty()) {
            log.warn("getEjecucionesDB: La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }

}
