/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.List;

public interface CoreDaoEquipo {
    String addEquipo(EquipoEntityReq equipoEntityReq);
    String setEquipo(EquipoEntityReq equipoEntityReq);
    Equipo getEquipo(IdElementoReq idEquipo);
    List<Equipo> getEquipos();
    List<ComponenteFisico> getComponentesFisicosEquipo(IdElementoReq idElementoReq);
}
