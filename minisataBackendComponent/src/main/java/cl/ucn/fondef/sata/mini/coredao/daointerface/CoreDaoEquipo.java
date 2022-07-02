/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.Componente;
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

    Componente getComponente(IdElementoReq idElementoReq);

    List<Componente> getComponentes(IdElementoReq idElementoReq);

    List<Pin> getPines(long IdComponente);

    Equipo getEquipo(IdElementoReq idEquipo);

    List<Equipo> getEquipos();

    List<Componente> getValvulasEquipo(IdElementoReq idElementoReq);

    List<Secuencia> getSecuenciasComponente(IdElementoReq idElementoReq);

    List<Evento> getEventos(long idSecuencia);
}
