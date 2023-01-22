/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpc.CoreBoardClientGrpcBase;
import cl.ucn.fondef.sata.mini.model.Componente;
import cl.ucn.fondef.sata.mini.model.Evento;
import cl.ucn.fondef.sata.mini.model.Secuencia;
import cl.ucn.fondef.sata.mini.utilities.InformacionBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * The Core dao equipo.
     */
    @Autowired
    CoreDaoEquipoImpl coreDaoEquipo;

    /**
     * The Core dao usuario.
     */
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
    public boolean yaExisteNombreSimulacionEnDB(String nombreSimulacion){
        String sqlQuery = "SELECT simulacion FROM Simulacion as simulacion WHERE simulacion.nombre = :nombreSimulacion";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("nombreSimulacion", nombreSimulacion).getResultList();
        return (!listaResultado.isEmpty());
    }
    @Override
    public String addSimulacion(SimulacionReq simulacionReq) {
        Usuario usuarioOperador = coreDaoUsuario.getUsuario(    Domain.RutEntityReq.newBuilder().setRut(simulacionReq.getRutOperador()).build());
        Equipo equipoUsado = coreDaoEquipo.getEquipoPorNombre(  simulacionReq.getNombreEquipo());
        if (equipoUsado == null ) {return "Simulacion no guardada";}
        // CHEQUEAR SI EL NOMBRE DE LA SIMULACION YA FUE TOMADO
        if(this.yaExisteNombreSimulacionEnDB(simulacionReq.getNombre())){return "Nombre simulacion ya existe";}

        Simulacion simulacionGuardar = new Simulacion();
        simulacionGuardar.setNombre(        simulacionReq.getNombre());
        simulacionGuardar.setDescripcion(   simulacionReq.getDescripcion());
        simulacionGuardar.setId_operador(    usuarioOperador.getId());
        simulacionGuardar.setId_equipo(      equipoUsado.getId());
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
                eventoGuardar.setPosicion(      listaEventos.get(j).getPosicion()+1);
                eventoGuardar.setIdSecuencia(   idSecuencia);
                entityManager.persist(eventoGuardar);
            }
        }
        return "Simulacion guardada";
    }




    @Override
    public Ejecucion getEjecucionDB(Domain.IdElementoReq idEjecucionReq) {
        String sqlQuery = "SELECT ejecucion FROM Ejecucion as ejecucion WHERE ejecucion.id = :idEjecucion";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("idEjecucion", idEjecucionReq.getId()).getResultList();
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





    private List<Secuencia> getListaIdsSecuencias (Domain.IdElementoReq idSimulacionReq) {
        String sqlQuery = "SELECT secuencia FROM Secuencia as secuencia WHERE secuencia.idSimulacion = :idSimulacion";
        List listaResultado = entityManager.createQuery(sqlQuery).setParameter("idSimulacion", idSimulacionReq.getId()).getResultList();
        if(listaResultado.isEmpty()){
            log.warn("getSimulaciones: La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }
    private void saveIntoDBMultiEjecucionSecuencia(long idEjecucion, Domain.IdElementoReq idSimulacionReq) {
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
    public String startSimulacion(StartSimulacionReq startSimulacionReq, HashMap<String, InformacionBoard> ejecucionesEquipo) {
        // SI EL EQUIPO NO ESTA EN EL HASHMAP, NO ESTA ENCENDIDO => NO CONTINUAR CON EL PROCESO
        if (!(ejecucionesEquipo.containsKey(startSimulacionReq.getNombreEquipo()))){
            log.warn(startSimulacionReq.getNombreEquipo() + " NO SE ENCUENTRA ACTIVO");
            return "❌ Equipo no encendido y disponible para operar ❌";
        }
        // SI EL EQUIPO ESTÁ ENCENDIDO PERO OCUPADO NO CONTINUAR CON EL PROCESO
        if (ejecucionesEquipo.get(startSimulacionReq.getNombreEquipo()).isEstaEjecutandose()) {
            log.warn(startSimulacionReq.getNombreEquipo() + " ESTA OCUPADO");
            return "❌ Equipo ocupado. NO disponible ❌";
        }

        log.info(startSimulacionReq.getNombreEquipo() + " INICIANDO SIMULACION");
        // PROCESO GUARDAR EN BASE DE DATOS
        Domain.IdElementoReq idSimulacionReq = IdElementoReq.newBuilder().setId(startSimulacionReq.getIdSimulacion()).build();
        Simulacion simulacionEjecutar = this.getSimulacionDB(idSimulacionReq);
        if (simulacionEjecutar == null) { return "❌ Simulacion no existente ❌"; }

        Ejecucion ejecucionNueva = new Ejecucion();
        ejecucionNueva.setIdSimulacion(startSimulacionReq.getIdSimulacion());
        ejecucionNueva.setAguaCaida(0.0);
        entityManager.persist(ejecucionNueva);
        long idEjecucion = ejecucionNueva.getId();
        this.saveIntoDBMultiEjecucionSecuencia(idEjecucion, idSimulacionReq);

        // PREPARAR LAS SECUENCIAS A ENVIAR AL RASPI
        List<Domain.Secuencia> listaSecuenciasGrpc = this.getGrpcSecuenciasSimulacion(idSimulacionReq);
        Domain.SimulacionBoardReq simulacionBoardReq = SimulacionBoardReq.newBuilder()
                .setIdSimulacion(idSimulacionReq.getId())
                .addAllSecuencia(listaSecuenciasGrpc)
                .build();

        // OBTENIENDO EL OBJETO QUE CONTIENE OBJETO QUE HACE LAS LLAMADAS GRPC AL EQUIPO SELECCIONADO
        InformacionBoard objetoEquipoDisponible = ejecucionesEquipo.get(startSimulacionReq.getNombreEquipo());
        CoreBoardClientGrpcBase coreBoardClient = objetoEquipoDisponible.getCoreBoardClient();

        // HACIENDO LA LLAMADA GRPC AL EQUIPO
        MensajeReply mensajeBoard = coreBoardClient.startSimulacion(simulacionBoardReq);

        if (mensajeBoard.getMensajeTexto().equals("Error al intentar conectar con la placa")) {
            return "❌ Error al intentar conectar con la placa ❌";
        }
        // GUARDANDO EL ESTADO OCUPADO DEL EQUIPO
        objetoEquipoDisponible.setEstaEjecutandose(true);

        // BORRANDO DATOS SIMULACION ANTERIOR
        objetoEquipoDisponible.setValoresGrafico(new ArrayList<>());
        objetoEquipoDisponible.setAguaCaidaActual(0.0);

        return "✔ Simulacion iniciada. IdEjecucion" + ejecucionNueva.getId() +" ✔";
    }
    public List<Simulacion> getSimulacionesEjectuadasDB (long idEquipo, int mes) {
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        System.out.println("Aaaaaaaaaaaaaa");
        String sqlQuery = "SELECT s FROM Simulacion s WHERE " +
                "s.id_equipo = : id_equipo AND" +
                ":mes = s.mes";
        System.out.println("Bbbbbbbbbbbbbb");
        var listas = (List<Simulacion>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idEquipo)
                .setParameter("mes", mes)
                .getResultList();
        System.out.println(listas);
        System.out.println("cccccccccccccccccccccccccc");
        return listas;
    }
    public List<Simulacion> getDatosResumenDB (long idEquipo, long caudal, long temperatura, long pluviometro, long presion, long humedad) {
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "SELECT c FROM Simulacion c WHERE " +
                "c.id_equipo = :id_equipo AND " +
                "c.caudal = :caudal AND " +
                "c.temperatura = :temperatura AND " +
                "c.pluviometro = :pluviometro AND " +
                "c.presion = :presion AND " +
                "c.humedad = :humedad";
        return (List<cl.ucn.fondef.sata.mini.model.Simulacion>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idEquipo)
                .setParameter("caudal", caudal)
                .setParameter("temperatura",temperatura)
                .setParameter("pluviometro", pluviometro)
                .setParameter("presion", presion)
                .setParameter("humedad", humedad)
                .getResultList();
    }

    // Si el atributo está en rojo es porque no existe en la respectiva clase del paquete Model
    public List<Simulacion> getMedidasDB (long idEjecucion, long idSensor) {
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "SELECT s FROM Simulacion s WHERE " +
                "s.id_ejecucion = :id_ejecucion AND " +
                "s.id_sensor = :id_sensor";

        // OJO => EL PROTOBUFF QUE ENTREGA LOS ARGUMENTOS TIENE MÁS INFORMACIÓN

        //System.out.println("idEjecucion: " + idEjecucion);
        //System.out.println("idSensor: " + idSensor);
        //System.out.println("timestamp: " + timestamp);
        //System.out.println("lastSecond: " + lastSecond);
        //System.out.println("lastEntities: " + lastEntities);
        //System.out.println("\n");
        //System.out.println("reqObject: " + reqObject);

        return (List<Simulacion>) entityManager.createQuery(sqlQuery)
                .setParameter("id_ejecucion", idEjecucion)
                .setParameter("id_sensor", idSensor)
                .getResultList();
    }


    // Si el atributo está en rojo es porque no existe en la respectiva clase del paquete Model
    public String getUltimaMedidasDB (long idEjecucion, long idSensor) {
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "SELECT s FROM Simulacion s WHERE "+
                "s.id_ejecucion = :id_ejecucion AND " +
                "s.id_sensor = :id_sensor";

        var ListaSimulacion = entityManager.createQuery(sqlQuery)
                .setParameter("id_ejecucion", idEjecucion)
                .setParameter("id_sensor", idSensor)
                .getResultList();

        var listSize = ListaSimulacion.size();
        if (listSize == 0) {
            return "null#null#null#null";
        }
        var ultimoElemento = (Simulacion) ListaSimulacion.get(listSize - 1);
        return ultimoElemento.getCaudal()+"#"+ ultimoElemento.getTemperatura()+"#"+ultimoElemento.getPluviometro()+"#"+ultimoElemento.getPresion()+"#"+ultimoElemento.getHumedad();
    }

    // Si el atributo está en rojo es porque no existe en la respectiva clase del paquete Model
    public List<Simulacion> getUltimasMedidasDB (int idEjecucion, int idSensor, String  timestamp, int lastSecond, int lastEntrities) {
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "SELECT s FROM Simulacion s WHERE " +
                "s.id_ejecucion = :id_ejecucion AND " +
                "s.id_sensor = :id_sensor AND " +
                "s.timestamp = :timestamp AND " +
                "s.last_second = :last_second AND " +
                "s.last_entrities = :last_entrities";
        return (List<cl.ucn.fondef.sata.mini.model.Simulacion>) entityManager.createQuery(sqlQuery)
                .setParameter("id_ejecucion", idEjecucion)
                .setParameter("idSensor", idSensor)
                .setParameter("timestamp", timestamp)
                .setParameter("lastSecond", lastSecond)
                .setParameter("lastEntrities", lastEntrities)
                .getResultList();
    }

}
