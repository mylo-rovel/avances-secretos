/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.List;

public interface CoreDao {
    boolean credencialesSonCorrectas(CredencialesEntityReq reqCredenciales);

    Usuario getUsuarioPorCorreo(String correoUsuario);

    String addUsuario(UsuarioEntityReq usuarioNuevo);
    Usuario getUsuario(RutEntityReq rutEntityReq);
    List<Usuario> getUsuarios();
    String setUsuario(UsuarioEntityReq usuarioEditar);

    String addEquipo(EquipoEntityReq equipoEntityReq);
    String setEquipo(EquipoEntityReq equipoEntityReq);
    Equipo getEquipo(IdElementoReq idEquipo);
    List<Equipo> getEquipos();

    Simulacion getSimulacion(IdElementoReq idElemento);
    List<Simulacion> getSimulaciones();
    List<ComponenteFisico> getComponentesFisicosEquipo(IdElementoReq idElementoReq);
<<<<<<< HEAD
}
=======
}
>>>>>>> 03847a5eda3bc8c8e1d7f17bdf1f00a572617f0e
