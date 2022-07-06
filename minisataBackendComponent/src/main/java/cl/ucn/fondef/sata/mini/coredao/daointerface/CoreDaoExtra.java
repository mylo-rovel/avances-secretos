/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Registro;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;

import java.util.List;

/**
 * The interface Core dao extra.
 */
public interface CoreDaoExtra {

    String fechaHoraActual();

    String addRegistroCreacionUsuario(Usuario usuarioRegistrar, Usuario usuarioAdministrador);

    String addRegistroLoginUsuario(Usuario usuarioLogin);

    String addRegistroModificacionUsuario(Usuario usuarioUpdate, Usuario usuarioAdministrador);

    String addRegistroCreacionSimulacion(Usuario usuarioOperador, Domain.SimulacionReq simulacion);

    String addRegistroInicioSimulacion(Usuario usuarioOperador, Simulacion simulacion);

    String addRegistroUploadArchivo(Usuario usuario);

    String addRegistroCreacionEquipo(Usuario usuario, Equipo equipo);

    String addRegistroModificacionEquipo(Usuario usuarioConfigurador, Equipo equipo);

    List<Registro> getRegistros(Domain.RutEntityReq rutEntityReq);
}
