/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.List;

/**
 * The interface Core dao.
 */
public interface CoreDaoUsuario {
    /**
     * Credenciales son correctas boolean.
     *
     * @param reqCredenciales the req credenciales
     * @return the boolean
     */
    boolean credencialesSonCorrectas(CredencialesEntityReq reqCredenciales);

    /**
     * Gets usuario por correo.
     *
     * @param correoUsuario the correo usuario
     * @return the usuario por correo
     */
    Usuario getUsuarioPorCorreo(String correoUsuario);

    /**
     * Add usuario string.
     *
     * @param usuarioNuevo the usuario nuevo
     * @return the string
     */
    String addUsuario(UsuarioEntityReq usuarioNuevo);

    /**
     * Gets usuario.
     *
     * @param rutEntityReq the rut entity req
     * @return the usuario
     */
    Usuario getUsuario(RutEntityReq rutEntityReq);

    /**
     * Gets usuarios.
     *
     * @return the usuarios
     */
    List<Usuario> getUsuarios();

    /**
     * Update usuario string.
     *
     * @param usuarioEditar the usuario editar
     * @return the string
     */
    String updateUsuario(UsuarioEntityReq usuarioEditar);
}