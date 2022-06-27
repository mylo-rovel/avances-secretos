/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.List;

/**
 * The interface Core dao simulacion.
 */
public interface CoreDaoSimulacion {
    /**
     * Gets simulacion.
     *
     * @param idElemento the id elemento
     * @return the simulacion
     */
    Simulacion getSimulacion(IdElementoReq idElemento);

    /**
     * Gets simulaciones.
     *
     * @return the simulaciones
     */
    List<Simulacion> getSimulaciones();
}
