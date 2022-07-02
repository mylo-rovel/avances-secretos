/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.Componente;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;
import cl.ucn.fondef.sata.mini.model.Pin;
import cl.ucn.fondef.sata.mini.model.Placa;

import java.util.List;

public interface CoreDaoEquipo {
    String addEquipo(EquipoEntityReq equipoEntityReq);

    String updateEquipo(EquipoEntityReq equipoEntityReq);

    List<Placa> getPlacas(IdElementoReq idEquipo);

    List<Componente> getComponentesFisicos(IdElementoReq idElementoReq);

    List<Pin> getPines(long IdComponente);

    Equipo getEquipo(IdElementoReq idEquipo);

    List<Equipo> getEquipos();

    List<Componente> getValvulasEquipo(IdElementoReq idElementoReq);

}
