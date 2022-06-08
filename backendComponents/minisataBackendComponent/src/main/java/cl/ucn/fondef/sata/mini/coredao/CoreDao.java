/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.model.Login;
import cl.ucn.fondef.sata.mini.model.RegistroUsuarios;
import cl.ucn.fondef.sata.mini.model.Usuario;

import java.util.List;

public interface CoreDao {
    boolean iniciarSesion(Login usuarioLogin);

    void registrarUsuario(Usuario usuario);

    List<RegistroUsuarios> obtenerAdmins ();
}
