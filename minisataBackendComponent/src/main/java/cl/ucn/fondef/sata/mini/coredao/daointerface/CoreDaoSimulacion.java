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

    Simulacion getSimulacionDB(Domain.IdElementoReq idSimulacionReq);

    List<Simulacion> getSimulaciones();

    String addSimulacion(SimulacionReq simulacionReq);

    List<Domain.Secuencia> getGrpcSecuenciasSimulacion(IdElementoReq idSimulacionReq);

    Ejecucion getEjecucionDB(Domain.IdElementoReq idSimulacionReq);

    List<Ejecucion> getEjecucionesDB();

    boolean yaExisteNombreSimulacionEnDB(String nombreSimulacion);

    String startSimulacion(StartSimulacionReq startSimulacionReq, HashMap<String, InformacionBoard> ejecucionesEquipo);
}
