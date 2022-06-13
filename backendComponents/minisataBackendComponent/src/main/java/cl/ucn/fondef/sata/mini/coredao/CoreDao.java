/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuario;
import cl.ucn.fondef.sata.mini.model.Usuario;

public interface CoreDao {
    boolean credencialesSonCorrectas(String correoUsuario, String contrasenaUsuario);
    Usuario obtenerUsuarioPorCorreo(String correoUsuario);

    String anadirUsuario(String rutAdmin, GrpcUsuario usuarioNuevo);
}
