/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.List;

public interface CoreDaoSimulacion {
    Simulacion getSimulacion(IdElementoReq idElemento);
    List<Simulacion> getSimulaciones();
}
