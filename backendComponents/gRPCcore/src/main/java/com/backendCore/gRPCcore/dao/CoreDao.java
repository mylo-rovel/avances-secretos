package com.backendCore.gRPCcore.dao;

import com.backendCore.gRPCcore.models.Usuario;

import java.util.List;

public interface CoreDao {
    List<Usuario> getUsuarios();

    void inhabilitarUsuario(int id);

    void registrarUsuario(Usuario usuario);
}
