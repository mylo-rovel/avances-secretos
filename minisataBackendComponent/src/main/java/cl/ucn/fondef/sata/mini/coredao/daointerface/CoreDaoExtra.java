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

    String fechaHoraActual();

    String addRegistroCreacionUsuario(Usuario usuarioRegistrar, Usuario usuarioAdministrador);

    String addRegistroLoginUsuario(Usuario usuarioLogin);

    String addRegistroModificacionUsuario(Usuario usuarioUpdate, Usuario usuarioAdministrador);

    String addRegistroCreacionSimulacion(Usuario usuarioOperador);

    String addRegistroInicioSimulacion(Usuario usuarioOperador);

    String addRegistroUploadArchivo(Usuario usuario);

    String addRegistroCreacionEquipo(Usuario usuario);

    String addRegistroModificacionEquipo(Usuario usuarioConfigurador);

    List<Registro> getRegistros(Domain.RutEntityReq rutEntityReq);

    String getInnerJoin();
}
