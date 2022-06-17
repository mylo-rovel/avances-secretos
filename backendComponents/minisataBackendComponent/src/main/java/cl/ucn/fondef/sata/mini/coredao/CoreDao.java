/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntity;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuarioEntity;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;

import java.util.List;

public interface CoreDao {
    boolean credencialesSonCorrectas(String correoUsuario, String contrasenaUsuario);
    Usuario getUsuarioPorCorreo(String correoUsuario);
    String addUsuario(GrpcUsuarioEntity usuarioNuevo);
    String addEquipo(String rutConfigurador, GrpcEquipoEntity equipo);
    List<Simulacion> getSimulaciones();
    Equipo getEquipo(Long idEquipo);
    Simulacion getSimulacion(int idSimulacion);
}
