/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Ejecucion;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;
import cl.ucn.fondef.sata.mini.utilities.InformacionBoard;

import java.util.HashMap;
import java.util.List;

/**
 * The interface Core dao simulacion.
 */
public interface CoreDaoSimulacion {

    /**
     * Gets simulacion db.
     *
     * @param idSimulacionReq the id simulacion req
     * @return the simulacion db
     */
    Simulacion getSimulacionDB(Domain.IdElementoReq idSimulacionReq);

    /**
     * Gets simulaciones.
     *
     * @return the simulaciones
     */
    List<Simulacion> getSimulaciones();

    /**
     * Add simulacion string.
     *
     * @param simulacionReq the simulacion req
     * @return the string
     */
    String addSimulacion(SimulacionReq simulacionReq);

    /**
     * Gets grpc secuencias simulacion.
     *
     * @param idSimulacionReq the id simulacion req
     * @return the grpc secuencias simulacion
     */
    List<Domain.Secuencia> getGrpcSecuenciasSimulacion(IdElementoReq idSimulacionReq);

    /**
     * Gets ejecucion db.
     *
     * @param idEjecucionReq the id ejecucion req
     * @return the ejecucion db
     */
    Ejecucion getEjecucionDB(Domain.IdElementoReq idEjecucionReq);

    /**
     * Gets ejecuciones db.
     *
     * @return the ejecuciones db
     */
    List<Ejecucion> getEjecucionesDB();

    /**
     * Ya existe nombre simulacion en db boolean.
     *
     * @param nombreSimulacion the nombre simulacion
     * @return the boolean
     */
    boolean yaExisteNombreSimulacionEnDB(String nombreSimulacion);

    /**
     * Start simulacion string.
     *
     * @param startSimulacionReq the start simulacion req
     * @param ejecucionesEquipo  the ejecuciones equipo
     * @return the string
     */
    String startSimulacion(StartSimulacionReq startSimulacionReq, HashMap<String, InformacionBoard> ejecucionesEquipo);

    List<cl.ucn.fondef.sata.mini.model.Simulacion> getSimulacionesEjectuadasDB (long idEquipo, int mes);
    List<cl.ucn.fondef.sata.mini.model.Simulacion> getDatosResumenDB (long idEquipo, long caudal, long temperatura, long pluviometro, long presion, long humedad);
    List<cl.ucn.fondef.sata.mini.model.Simulacion> getMedidasDB (int idEjecucion, int idSensor);
    String getUltimaMedidasDB (int idEjecucion, int idSensor);
    List<cl.ucn.fondef.sata.mini.model.Simulacion> getUltimasMedidasDB (int idEjecucion, int idSensor, String  timestamp, int lastSecond, int lastEntrities);
}
