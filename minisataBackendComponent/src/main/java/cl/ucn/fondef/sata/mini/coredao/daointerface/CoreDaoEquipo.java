/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;
import cl.ucn.fondef.sata.mini.model.Evento;
import cl.ucn.fondef.sata.mini.model.Pin;
import cl.ucn.fondef.sata.mini.model.Placa;
import cl.ucn.fondef.sata.mini.model.Secuencia;

import java.util.List;

public interface CoreDaoEquipo {
    String addEquipo(EquipoEntityReq equipoEntityReq);

    String updateEquipo(EquipoEntityReq equipoEntityReq);

    List<Placa> getPlacas(IdElementoReq idEquipo);

    ComponenteFisico getComponenteFisico(IdElementoReq idElementoReq);
    List<ComponenteFisico> getComponentesFisicos(IdElementoReq idElementoReq);

    List<Pin> getPines(long IdComponente);

    List<Evento> getEventos(long idSecuencia);

    Equipo getEquipo(IdElementoReq idEquipo);

    List<Equipo> getEquipos();

    List<ComponenteFisico> getValvulasEquipo(IdElementoReq idElementoReq);

    List<Secuencia> getSecuenciasComponente(IdElementoReq idElementoReq);

}
