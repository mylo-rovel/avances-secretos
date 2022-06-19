/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.List;

public interface CoreDao {
    boolean credencialesSonCorrectas(CredencialesEntityReq reqCredenciales);

    Usuario getUsuarioPorCorreo(String correoUsuario);

    String addUsuario(UsuarioEntityReq usuarioNuevo);

    String addEquipo(EquipoEntityReq equipoEntityReq);

    Equipo getEquipo(IdElementoReq idEquipo);

    Simulacion getSimulacion(IdElementoReq idElemento);

    List<Simulacion> getSimulaciones();
}
