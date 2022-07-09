/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.grpc.Domain;
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

    List<Placa> getPlacasPorIdEquipo(IdElementoReq idEquipo);

    Componente getComponentePorId(IdElementoReq idElementoReq);

    List<Componente> getComponentesPorIdEquipo(IdElementoReq idElementoReq);

    List<Pin> getPinesPorIdComponente(long IdComponente);

    Equipo getEquipo(IdElementoConRutReq idEquipoYrutUsuario);

    Equipo getEquipoPorNombre(String nombreEquipo);

    List<Equipo> getEquipos(Domain.RutEntityReq rutEntityReq);

    List<Componente> getValvulasEquipo(IdElementoReq idElementoReq);

    List<Evento> getEventos(long idSecuencia);

    List<Secuencia> getSecuenciasComponente(IdElementoReq idElementoReq);
}
