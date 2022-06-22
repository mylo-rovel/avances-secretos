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
    boolean credencialesSonCorrectas(CredencialesEntityReq reqCredenciales);
    Usuario getUsuarioPorCorreo(String correoUsuario);

    String addUsuario(UsuarioEntityReq usuarioNuevo);
    Usuario getUsuario(RutEntityReq rutEntityReq);
    List<Usuario> getUsuarios();
    String setUsuario(UsuarioEntityReq usuarioEditar);
}