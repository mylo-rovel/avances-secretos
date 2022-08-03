/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Registro;
import cl.ucn.fondef.sata.mini.model.Usuario;

import java.util.List;

/**
 * The interface Core dao extra.
 */
public interface CoreDaoExtra {

    /**
     * Fecha hora actual string.
     *
     * @return the string
     */
    String fechaHoraActual();

    /**
     * Add registro creacion usuario string.
     *
     * @param usuarioRegistrar     the usuario registrar
     * @param usuarioAdministrador the usuario administrador
     * @return the string
     */
    String addRegistroCreacionUsuario(Usuario usuarioRegistrar, Usuario usuarioAdministrador);

    /**
     * Add registro login usuario string.
     *
     * @param usuarioLogin the usuario login
     * @return the string
     */
    String addRegistroLoginUsuario(Usuario usuarioLogin);

    /**
     * Add registro modificacion usuario string.
     *
     * @param usuarioUpdate        the usuario update
     * @param usuarioAdministrador the usuario administrador
     * @return the string
     */
    String addRegistroModificacionUsuario(Usuario usuarioUpdate, Usuario usuarioAdministrador);

    /**
     * Add registro creacion simulacion string.
     *
     * @param usuarioOperador the usuario operador
     * @return the string
     */
    String addRegistroCreacionSimulacion(Usuario usuarioOperador);

    /**
     * Add registro inicio simulacion string.
     *
     * @param usuarioOperador the usuario operador
     * @return the string
     */
    String addRegistroInicioSimulacion(Usuario usuarioOperador);

    /**
     * Add registro upload archivo string.
     *
     * @param usuario the usuario
     * @return the string
     */
    String addRegistroUploadArchivo(Usuario usuario);

    /**
     * Add registro creacion equipo string.
     *
     * @param usuario the usuario
     * @return the string
     */
    String addRegistroCreacionEquipo(Usuario usuario);

    /**
     * Add registro modificacion equipo string.
     *
     * @param usuarioConfigurador the usuario configurador
     * @return the string
     */
    String addRegistroModificacionEquipo(Usuario usuarioConfigurador);

    /**
     * Gets registros.
     *
     * @param rutEntityReq the rut entity req
     * @return the registros
     */
    List<Registro> getRegistros(Domain.RutEntityReq rutEntityReq);

    /**
     * Gets inner join.
     *
     * @return the inner join
     */
    String getInnerJoin();
}
