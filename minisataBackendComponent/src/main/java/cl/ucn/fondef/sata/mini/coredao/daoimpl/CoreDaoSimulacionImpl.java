/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Componente;
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

    @Override
    public Simulacion getSimulacion(long idSimulacion){
        String sqlQuery = "FROM Simulacion WHERE id = :idSimulacion";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("idSimulacion", idSimulacion).getResultList();
        if(listaResultado.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        return (Simulacion) listaResultado.get(0);
    }

    @Override
    public List<Simulacion> getSimulaciones(){
        String sqlQuery = "FROM Simulacion";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            log.warn("La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }

/*    @Override
    private void getSecuenciasSimulacion () {

    }*/

    @Override
    public String addSimulacion(SimulacionReq simulacionReq) {
        Usuario usuarioOperador = coreDaoUsuario.getUsuario(    Domain.RutEntityReq.newBuilder().setRut(simulacionReq.getRutOperador()).build());
        Equipo equipoUsado = coreDaoEquipo.getEquipoPorNombre(  simulacionReq.getNombreEquipo());

        Simulacion simulacionGuardar = new Simulacion();
        simulacionGuardar.setNombre(        simulacionReq.getNombre());
        simulacionGuardar.setDescripcion(   simulacionReq.getDescripcion());
        simulacionGuardar.setIdEquipo(      equipoUsado.getId());
        simulacionGuardar.setIdOperador(    usuarioOperador.getId());
        entityManager.persist(simulacionGuardar);

        long idSimulacion = simulacionGuardar.getId();

        List<Domain.Secuencia> listaSecuencias = simulacionReq.getSecuenciaList();
        for (int i = 0; i < listaSecuencias.size(); i++) {
            long idComponente = listaSecuencias.get(i).getIdComponente();

            // guardar en 'simulacioncomponente'
            SimulacionComponente simCompGuardar = new SimulacionComponente();
            simCompGuardar.setIdSimulacion(idSimulacion);
            simCompGuardar.setIdComponente(idComponente);
            entityManager.persist(simCompGuardar);

            Secuencia secuenciaGuardar = new Secuencia();
            secuenciaGuardar.setIdComponente(idComponente);
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

    public String startSimulacion(StartSimulacionReq startSimulacionReq) {
        // todo: guardar en Ejecucion y EjecucionSecuencia
        Simulacion simulacionEjecutar = this.getSimulacion(startSimulacionReq.getIdSimulacion());
        if (simulacionEjecutar == null) { return "Simulacion no existente"; }

        Ejecucion ejecucionNueva = new Ejecucion();
        ejecucionNueva.setIdSimulacion(startSimulacionReq.getIdSimulacion());
        ejecucionNueva.setAguaCaida(0.0);
        entityManager.persist(ejecucionNueva);

        // todo: obtener las id de los componentes asociados a la simulacion
        // todo: => tabla 'simulacioncomponente'
        // todo: luego obtener las secuencias de esos componentes
        // ENVIAR LAS SECUENCIAS AL RASPI
        return "Simulacion iniciada. IdEjecucion" + ejecucionNueva.getId();
    }
}
