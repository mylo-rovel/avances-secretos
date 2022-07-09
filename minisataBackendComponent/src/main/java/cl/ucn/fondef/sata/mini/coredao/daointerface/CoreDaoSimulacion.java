/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Ejecucion;
import cl.ucn.fondef.sata.mini.model.Secuencia;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.List;

/**
 * The interface Core dao simulacion.
 */
public interface CoreDaoSimulacion {

    Simulacion getSimulacionDB(Domain.IdElementoReq idSimulacionReq);

    List<Simulacion> getSimulaciones();

    String addSimulacion(SimulacionReq simulacionReq);

    String startSimulacion(StartSimulacionReq startSimulacionReq);

    List<Domain.Secuencia> getGrpcSecuenciasSimulacion(IdElementoReq idSimulacionReq);

    Ejecucion getEjecucionDB(Domain.IdElementoReq idSimulacionReq);

    List<Ejecucion> getEjecucionesDB();
}
