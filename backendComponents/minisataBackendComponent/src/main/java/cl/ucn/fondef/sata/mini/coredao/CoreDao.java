/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipo;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuario;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;

import java.util.List;

public interface CoreDao {
    boolean credencialesSonCorrectas(String correoUsuario, String contrasenaUsuario);
    Usuario obtenerUsuarioPorCorreo(String correoUsuario);
    String anadirUsuario(String rutAdmin, GrpcUsuario usuarioNuevo);
    String anadirEquipo(String rutConfigurador, GrpcEquipo equipo);
    List<Simulacion> obtenerSimulaciones();
    Equipo obtenerEquipoEspecifico(Long idEquipo);
    Simulacion obtenerSimulacionEspecifica(int idSimulacion);
}
