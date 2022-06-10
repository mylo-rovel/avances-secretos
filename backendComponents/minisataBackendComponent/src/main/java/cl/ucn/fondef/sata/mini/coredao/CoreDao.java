/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.model.Login;
import cl.ucn.fondef.sata.mini.model.RegistroUsuarios;
import cl.ucn.fondef.sata.mini.model.Usuario;

import java.util.List;

public interface CoreDao {
    boolean credencialesSonCorrectas(String correoUsuario, String contrasenaUsuario);

    Usuario obtenerUsuarioPorCorreo(String correoUsuario);
}
