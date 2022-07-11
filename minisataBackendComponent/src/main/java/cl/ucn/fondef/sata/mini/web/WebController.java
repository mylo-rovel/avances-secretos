/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpc.webcoreclient.*;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.utilities.BytesChunker;
import cl.ucn.fondef.sata.mini.utilities.EnumValuesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH} )
@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH} )
public class WebController {
    // ESTE SERVIDOR ES EL PUENTE ENTRE EL WEBBROWSER Y EL "CENTRAL CORE"
    @Autowired
    private WebCoreClientGrpcUsuario webCoreClientGrpcUsuario;

    @Autowired
    private WebCoreClientGrpcEquipo webCoreClientGrpcEquipo;

    @Autowired
    private WebCoreClientGrpcSimulacion webCoreClientGrpcSimulacion;

    @Autowired
    private WebCoreClientGrpcExtra webCoreClientGrpcExtra;

    @Autowired
    private WebCoreStreamClientGrpc webCoreStreamClientGrpc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CoreDaoUsuario coreDaoUsuario;

    @Autowired
    private CoreDaoExtra coreDaoExtra;

    private String getTokenKey(String jsonwebtoken) {
        return jwtUtil.getKey(jsonwebtoken);
    }

     private boolean tokenEsValido(String jsonwebtoken) {
        try {
            if (jsonwebtoken == null) {return false;}
            // si al obtener la llave del token (el correo) se retorna null, entonces el token está malo
            return jwtUtil.getKey(jsonwebtoken) != null;
        }
        catch (Exception ex) {
            log.warn("Error al validar el token");
            log.warn(ex.getMessage());
            return false;
        }
     }

    // ---- USUARIOS ----------------------------------------------------------------------------------------
    // rpc authenticate(CredencialesEntityReq) returns (SesionEntityReply){}
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String authenticate(@RequestBody GrpcCredencialesEntityReq credenciales) {
        return webCoreClientGrpcUsuario.authenticate(credenciales);
    }


// rpc addUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public String addUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo, @RequestHeader(value="Authorization") String jwt) {
       return webCoreClientGrpcUsuario.addUsuario(usuarioNuevo);
  /*      if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuarioAdmin = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuarioAdmin!=null){
            if(usuarioAdmin.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())){
                String json = webCoreClientGrpcUsuario.addUsuario(usuarioNuevo);
                Domain.RutEntityReq rutUsuarioAgregado = Domain.RutEntityReq.newBuilder().setRut(usuarioNuevo.getUsuario().getRut()).build();
                Usuario usuarioAgregado = coreDaoUsuario.getUsuario(rutUsuarioAgregado);
                coreDaoExtra.addRegistroCreacionUsuario(usuarioAgregado, usuarioAdmin);
                return json;
            }
        }
        return "Usuario sin permisos";*/
    }

    // rpc getUsuario(RutEntityReq)  returns (UsuarioEntityReply) {}
    @RequestMapping(value = "api/usuarios/{rut}", method = RequestMethod.GET)
    public String getUsuario(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null){
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())){
                return webCoreClientGrpcUsuario.getUsuario(rut);
            }
        }
        return "Error. Token invalido";
    }

    // rpc getUsuarios(EmptyReq) returns (UsuariosEntityReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
     public String getUsuarios(@RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null){
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())) {
                return webCoreClientGrpcUsuario.getUsuarios();
            }
        }
        return "Usuario sin permisos";
    }

    // rpc updateUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.PATCH)
    public String updateUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())) {
                return webCoreClientGrpcUsuario.updateUsuario(usuarioModificar);
            }
        }
        return "Usuario sin permisos";
    }


    // ---- USUARIOS ----------------------------------------------------------------------------------------
    // ---- EQUIPOS  ----------------------------------------------------------------------------------------


    //   rpc addEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.POST)
     public String addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                equipoNuevo.setRutConfigurador(this.getTokenKey(jwt));
                return webCoreClientGrpcEquipo.addEquipo(equipoNuevo);
            }
        }
        return "Error. Token invalido";
    }

    //   rpc updateEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.PATCH)
    public String updateEquipo(@RequestBody GrpcEquipoEntityReq equipoModificado, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                equipoModificado.setRutConfigurador(this.getTokenKey(jwt));
                return webCoreClientGrpcEquipo.updateEquipo(equipoModificado);
            }
        }
        return "Usuario sin permisos";
    }

    //   rpc getEquipo(IdElementoReq)  returns (EquipoEntityReply) {}
    @RequestMapping(value = "api/equipos/{id}", method = RequestMethod.GET)
    public String getEquipo(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null){
            if(usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())
                    || usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())){
                return webCoreClientGrpcEquipo.getEquipo(id, rutUsuario.getRut());
            }
        }
        return "Usario sin permisos";
    }

    //   rpc getEquipos(EmptyReq)  returns (EquiposEntityReply) {}
    @RequestMapping(value = "api/equipos", method = RequestMethod.GET)
    public String getEquipos(@RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null){
            if(usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) || usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())){
                return webCoreClientGrpcEquipo.getEquipos(rutUsuario.getRut());
            }
        }
        return "Usario sin permisos";
    }

// ***---- IMPLEMENTAR ----
    //   rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos/archivos", method = RequestMethod.POST)
    public String uploadArchivo(@RequestParam("file") MultipartFile file, @RequestHeader(value="Authorization") String jwt) throws IOException, InterruptedException {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                webCoreStreamClientGrpc.uploadArchivo(file);
                return file.getOriginalFilename();
            }
        }
        return "Usuario sin permisos";
        //        ResponseEntity<?>
        //        ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getArchivos(IdElementoReq)  returns (ArchivosEquipoEntityReply){}
    @RequestMapping(value = "api/equipos/archivo/{idEquipo}", method = RequestMethod.GET)
    public String getArchivos(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                return webCoreClientGrpcEquipo.getArchivos(idEquipo);
            }
        }
        return "Usuario sin permisos";
    }

    //   rpc getValvulasEquipo(IdElementoReq) returns (ComponentesEquipoReply) {}
    @RequestMapping(value = "api/equipos/valvulas/{idEquipo}", method = RequestMethod.GET)
    public String getValvulasEquipo(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                return webCoreClientGrpcEquipo.getValvulasEquipo(idEquipo);
            }
        }
        return "Usuario sin permisos";
    }


    // ***---- TESTEAR ----
    //    rpc getSecuenciasComponente(IdElementoReq) returns (SecuenciasComponenteReply) {}
    @RequestMapping(value = "api/equipos/secuencias/{idComponente}", method = RequestMethod.GET)
    public String getSecuenciasComponente(@PathVariable long idComponente, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                return webCoreClientGrpcEquipo.getSecuenciasComponente(idComponente);
            }
        }
        return "Usuario sin permisos";
    }


    // ---- EQUIPOS      ------------------------------------------------------------------------------------
    // ---- SIMULACIONES ------------------------------------------------------------------------------------

    // rpc addSimulacion(SimulacionReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones/secuencias/", method = RequestMethod.POST)
    public String addSimulacion(@RequestBody GrpcSimulacionReq simulacionReq, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                simulacionReq.setRutOperador(this.getTokenKey(jwt));
                return webCoreClientGrpcSimulacion.addSimulacion(simulacionReq);
            }
        }
        return "Usuario sin permisos";
    }

    //   rpc getSimulacion(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/simulaciones/{id}", method = RequestMethod.GET)
    public String getSimulacion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getSimulacion(id, this.getTokenKey(jwt));
            }
        }
        return "Usuario sin permisos";
    }

    //   rpc getSimulaciones(EmptyReq) returns (SimulacionesReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.GET)
    public String getSimulaciones(@RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getSimulaciones(this.getTokenKey(jwt));
            }
        }
        return "Usuario sin permisos";
    }

    // ***---- IMPLEMENTAR: TODO: ARREGLAR DOMAIN.PROTO => Sólo recibimos equipo y simulacion ----
    //   rpc startSimulacion(SimulacionReq) returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.POST)
    public String startSimulacion(@RequestBody GrpcStartSimulacionReq startSimulacionReq, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.startSimulacion(startSimulacionReq);
            }
        }
        return "Usuario sin permisos";
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getSimulacionActual(IdElementoReq) returns (SimulacionReply){}
    //TODO: REVISAR GETSIMULACIONACTUAL
    @RequestMapping(value = "api/ejecuciones/actual/{id}", method = RequestMethod.GET)
    public String getSimulacionActual(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getSimulacionActual();
            }
        }
        return "Usuario sin permisos";
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getEjecucion(IdElementoReq) returns (EjecucionReply){}
    @RequestMapping(value = "api/ejecuciones/{id}", method = RequestMethod.GET)
    public String getEjecucion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getEjecucion(id, this.getTokenKey(jwt));
            }
        }
        return "Usuario sin permisos";
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getEjecuciones(EmptyReq) returns (EjecucionesReply){}
    @RequestMapping(value = "api/ejecuciones/", method = RequestMethod.GET)
    public String getEjecuciones(@RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getEjecuciones(this.getTokenKey(jwt));
            }
        }
        return "Usuario sin permisos";
    }

    // ---- SIMULACIONES       ------------------------------------------------------------------------------
    // ---- OPERACIONES EXTRA  ------------------------------------------------------------------------------


    // ***---- IMPLEMENTAR ----
    //   rpc getLecturaSensores(IdElementoReq) returns (stream LecturaSensoresReply) {}
    @RequestMapping(value = "api/ejecuciones/lecturas/{id}", method = RequestMethod.GET)
    public String getLecturaSensores(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcExtra.getLecturaSensores(id);
            }
        }
        return "Usuario sin permisos";
    }

    //   rpc getRegistros(RutEntityReq) returns (RegistrosReply){}
    @RequestMapping(value = "api/registros/{rut}", method = RequestMethod.GET)
    public String getRegistros(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())) {
                return webCoreClientGrpcExtra.getRegistros(rut);
            }
        }
        return "Usuario sin permisos";
    }


    @RequestMapping(value = "api/extras/enums", method = RequestMethod.GET)
    public EnumValuesResponse getEnumDict(@RequestHeader(value="Authorization") String jwt) {
        EnumValuesResponse objetoRetornar = new EnumValuesResponse();
        if(!this.tokenEsValido(jwt)) { return null; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            objetoRetornar.addEnumList("RolUsuario", Domain.UsuarioEntity.RolUsuario.values());
            objetoRetornar.addEnumList("EstadoUsuario", Domain.UsuarioEntity.EstadoUsuario.values());
            objetoRetornar.addEnumList("TipoRegistro", Domain.Registro.TipoRegistro.values());
            objetoRetornar.addEnumList("TipoComponente", Domain.Componente.TipoComponente.values());
            objetoRetornar.addEnumList("ConexionPin", Domain.Pin.ConexionPin.values());
            objetoRetornar.addEnumList("EstadoComponente", Domain.Componente.EstadoComponente.values());
            objetoRetornar.addEnumList("EstadoEquipo", Domain.EstadoEquipo.values());
            objetoRetornar.addEnumList("TipoPlaca", Domain.TipoPlaca.values());
            objetoRetornar.addEnumList("TipoArchivo", Domain.ArchivoEntity.TipoArchivo.values());
        }
        return objetoRetornar;
    }
}