/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;
import cl.ucn.fondef.sata.mini.model.Componente;
import cl.ucn.fondef.sata.mini.model.Evento;
import cl.ucn.fondef.sata.mini.model.Pin;
import cl.ucn.fondef.sata.mini.model.Placa;
import cl.ucn.fondef.sata.mini.model.Secuencia;

import java.util.List;

/**
 * The interface Core dao equipo.
 */
public interface CoreDaoEquipo {
    /**
     * Add equipo string.
     *
     * @param equipoEntityReq the equipo entity req
     * @return the string
     */
    String addEquipo(EquipoEntityReq equipoEntityReq);

    /**
     * Update equipo string.
     *
     * @param equipoEntityReq the equipo entity req
     * @return the string
     */
    String updateEquipo(EquipoEntityReq equipoEntityReq);

    /**
     * Gets placas por id equipo.
     *
     * @param idEquipo the id equipo
     * @return the placas por id equipo
     */
    List<Placa> getPlacasPorIdEquipo(IdElementoReq idEquipo);

    /**
     * Gets componente por id.
     *
     * @param idElementoReq the id elemento req
     * @return the componente por id
     */
    Componente getComponentePorId(IdElementoReq idElementoReq);

    /**
     * Gets componentes por id equipo.
     *
     * @param idElementoReq the id elemento req
     * @return the componentes por id equipo
     */
    List<Componente> getComponentesPorIdEquipo(IdElementoReq idElementoReq);

    /**
     * Gets pines por id componente.
     *
     * @param IdComponente the id componente
     * @return the pines por id componente
     */
    List<Pin> getPinesPorIdComponente(long IdComponente);

    /**
     * Gets equipo.
     *
     * @param idEquipoYrutUsuario the id equipo yrut usuario
     * @return the equipo
     */
    Equipo getEquipo(IdElementoConRutReq idEquipoYrutUsuario);

    /**
     * Gets equipo por nombre.
     *
     * @param nombreEquipo the nombre equipo
     * @return the equipo por nombre
     */
    Equipo getEquipoPorNombre(String nombreEquipo);

    /**
     * Gets equipos.
     *
     * @param rutEntityReq the rut entity req
     * @return the equipos
     */
    List<Equipo> getEquipos(Domain.RutEntityReq rutEntityReq);

    /**
     * Gets valvulas equipo.
     *
     * @param idElementoReq the id elemento req
     * @return the valvulas equipo
     */
    List<Componente> getValvulasEquipo(IdElementoReq idElementoReq);

    /**
     * Gets eventos.
     *
     * @param idSecuencia the id secuencia
     * @return the eventos
     */
    List<Evento> getEventos(long idSecuencia);

    /**
     * Gets secuencias componente.
     *
     * @param idElementoReq the id elemento req
     * @return the secuencias componente
     */
    List<Secuencia> getSecuenciasComponente(IdElementoReq idElementoReq);


    List<Componente> getSensoresDB(long idEquipo);

    List<Componente> getUmbralesPorSensorDB(long idSensor);
}
