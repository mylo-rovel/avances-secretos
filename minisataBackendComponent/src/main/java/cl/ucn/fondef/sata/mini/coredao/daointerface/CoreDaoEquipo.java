/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

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
     * Gets equipo.
     *
     * @param idEquipo the id equipo
     * @return the equipo
     */
    Equipo getEquipo(IdElementoReq idEquipo);

    /**
     * Gets equipos.
     *
     * @return the equipos
     */
    List<Equipo> getEquipos();

    /**
     * Gets componentes fisicos equipo.
     *
     * @param idElementoReq the id elemento req
     * @return the componentes fisicos equipo
     */
    List<ComponenteFisico> getComponentesFisicosEquipo(IdElementoReq idElementoReq);

    /**
     * Gets valvulas equipo.
     *
     * @param idElementoReq the id elemento req
     * @return the valvulas equipo
     */
    List<ComponenteFisico> getValvulasEquipo(IdElementoReq idElementoReq);
}
