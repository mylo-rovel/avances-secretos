/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

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


/**
 * The type Web controller.
 */
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

    /**
     * Authenticate string.
     *
     * @param credenciales the credenciales
     * @return the string
     */
// ---- USUARIOS ----------------------------------------------------------------------------------------
    // rpc authenticate(CredencialesEntityReq) returns (SesionEntityReply){}
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String authenticate(@RequestBody GrpcCredencialesEntityReq credenciales) {
        return webCoreClientGrpcUsuario.authenticate(credenciales);
    }


// rpc addUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public String addUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null){
            if(usuario.getRol().equals("ADMINISTRADOR")){
                return webCoreClientGrpcUsuario.addUsuario(usuarioNuevo);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets usuario.
     *
     * @param rut the rut
     * @param jwt the jwt
     * @return the usuario
     */
// rpc getUsuario(RutEntityReq)  returns (UsuarioEntityReply) {}
    @RequestMapping(value = "api/usuarios/{rut}", method = RequestMethod.GET)
    public String getUsuario(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null){
            if (usuario.getRol().equals("ADMINISTRADOR")){
                return webCoreClientGrpcUsuario.getUsuario(rut);
            }
        }
        return "Error. Token invalido";
    }

    /**
     * Gets usuarios.
     *
     * @param jwt the jwt
     * @return the usuarios
     */
// rpc getUsuarios(EmptyReq) returns (UsuariosEntityReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
     public String getUsuarios(@RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null){
            if (usuario.getRol().equals("ADMINISTRADOR")) {
                return webCoreClientGrpcUsuario.getUsuarios();
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Update usuario string.
     *
     * @param usuarioModificar the usuario modificar
     * @param jwt              the jwt
     * @return the string
     */
// rpc updateUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.PATCH)
    public String updateUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("ADMINISTRADOR")) {
                return webCoreClientGrpcUsuario.updateUsuario(usuarioModificar);
            }
        }
        return "Usuario sin permisos";
    }


    // ---- USUARIOS ----------------------------------------------------------------------------------------
    // ---- EQUIPOS  ----------------------------------------------------------------------------------------


    /**
     * Add equipo string.
     *
     * @param equipoNuevo the equipo nuevo
     * @param jwt         the jwt
     * @return the string
     */
//   rpc addEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.POST)
     public String addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("CONFIGURADOR")) {
                equipoNuevo.setRutConfigurador(this.getTokenKey(jwt));
                return webCoreClientGrpcEquipo.addEquipo(equipoNuevo);
            }
        }
        return "Error. Token invalido";
    }

    /**
     * Update equipo string.
     *
     * @param equipoModificado the equipo modificado
     * @return the string
     */
    //   rpc updateEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.PATCH)
    public String updateEquipo(@RequestBody GrpcEquipoEntityReq equipoModificado, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("CONFIGURADOR")) {
                equipoModificado.setRutConfigurador(this.getTokenKey(jwt));
                return webCoreClientGrpcEquipo.updateEquipo(equipoModificado);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets equipo.
     *
     * @param id the id
     * @return the equipo
     */
    //   rpc getEquipo(IdElementoReq)  returns (EquipoEntityReply) {}
    @RequestMapping(value = "api/equipos/{id}", method = RequestMethod.GET)
    public String getEquipo(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null){
            if(this.tokenEsValido(jwt)){
                if(usuario.getRol().equals("OPERADOR") || usuario.getRol().equals("CONFIGURADOR")){
                    return webCoreClientGrpcEquipo.getEquipoOC(id, rutUsuario.getRut());
                }
                return webCoreClientGrpcEquipo.getEquipo(id);
            }
        }
        return "Usario sin permisos";
    }

    /**
     * Gets equipos.
     *
     * @return the equipos
     */
    //   rpc getEquipos(EmptyReq)  returns (EquiposEntityReply) {}
    @RequestMapping(value = "api/equipos", method = RequestMethod.GET)
    public String getEquipos(@RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario.getRol().equals("OPERADOR") || usuario.getRol().equals("CONFIGURADOR")){
            return webCoreClientGrpcEquipo.getEquiposOC(rutUsuario.getRut());
        }
        return webCoreClientGrpcEquipo.getEquipos();
    }

// ***---- IMPLEMENTAR ----
    //   rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos/archivos", method = RequestMethod.POST)
    public String uploadArchivo(@RequestParam("file") MultipartFile file, @RequestHeader(value="Authorization") String jwt) throws IOException, InterruptedException {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            webCoreStreamClientGrpc.uploadArchivo(file);
            return file.getOriginalFilename();
        }
        return "Usuario sin permisos";
        //        ResponseEntity<?>
        //        ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    /**
     * Gets archivos.
     *
     * @param idEquipo the id equipo
     * @return the archivos
     */
// ***---- IMPLEMENTAR ----
    //   rpc getArchivos(IdElementoReq)  returns (ArchivosEquipoEntityReply){}
    @RequestMapping(value = "api/equipos/archivo/{idEquipo}", method = RequestMethod.GET)
    public String getArchivos(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            return webCoreClientGrpcEquipo.getArchivos(idEquipo);
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets valvulas equipo.
     *
     * @param idEquipo the id equipo
     * @return the valvulas equipo
     */
    //   rpc getValvulasEquipo(IdElementoReq) returns (ComponentesEquipoReply) {}
    @RequestMapping(value = "api/equipos/valvulas/{idEquipo}", method = RequestMethod.GET)
    public String getValvulasEquipo(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            return webCoreClientGrpcEquipo.getValvulasEquipo(idEquipo);
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
            return webCoreClientGrpcEquipo.getSecuenciasComponente(idComponente);
        }
        return "Usuario sin permisos";
    }


    // ---- EQUIPOS      ------------------------------------------------------------------------------------
    // ---- SIMULACIONES ------------------------------------------------------------------------------------

    // rpc addSecuencias(SecuenciasReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones/secuencias/", method = RequestMethod.POST)
    public String addSecuencias(@RequestBody GrpcSecuenciasReq secuenciasReq, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("OPERADOR")) {
                return webCoreClientGrpcSimulacion.addSecuencias(secuenciasReq);

            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets simulacion.
     *
     * @param id the id
     * @return the simulacion
     */
    // ***---- ACTUALIZAR: AGREGAR AGUA CAIDA Y SECUENCIAS ----
//   rpc getSimulacion(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/simulaciones/{id}", method = RequestMethod.GET)
    public String getSimulacion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt){
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("OPERADOR")) {
                return webCoreClientGrpcSimulacion.getSimulacion(id);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets simulaciones.
     *
     * @return the simulaciones
     */
    // ***---- ACTUALIZAR: ARREGLAR DATO AGUA CAIDA ----
//   rpc getSimulaciones(EmptyReq) returns (SimulacionesReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.GET)
    public String getSimulaciones(@RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("OPERADOR")) {
                return webCoreClientGrpcSimulacion.getSimulaciones();
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Start simulacion string.
     *
     * @param simulacionNueva the simulacion nueva
     * @return the string
     */
// ***---- IMPLEMENTAR: TODO: ARREGLAR DOMAIN.PROTO => Sólo recibimos equipo y simulacion ----
    //   rpc startSimulacion(SimulacionReq) returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.POST)
    public String startSimulacion(@RequestBody GrpcSimulacionReply simulacionNueva, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("OPERADOR")) {
                return webCoreClientGrpcSimulacion.startSimulacion(simulacionNueva);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets simulacion actual.
     *
     * @return the simulacion actual
     */
// ***---- IMPLEMENTAR ----
    //   rpc getSimulacionActual(IdElementoReq) returns (SimulacionReply){}
    //TODO: REVISAR GETSIMULACIONACTUAL
    @RequestMapping(value = "api/ejecuciones/actual/{id}", method = RequestMethod.GET)
    public String getSimulacionActual(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("OPERADOR")) {
                return webCoreClientGrpcSimulacion.getSimulacionActual();
            }
        }
        return "Usuario sin permisos";
    }

    // ---- SIMULACIONES       ------------------------------------------------------------------------------
    // ---- OPERACIONES EXTRA  ------------------------------------------------------------------------------


    /**
     * Gets lectura sensores.
     *
     * @param id the id
     * @return the lectura sensores
     */
// ***---- IMPLEMENTAR ----
    //   rpc getLecturaSensores(IdElementoReq) returns (stream LecturaSensoresReply) {}
    @RequestMapping(value = "api/ejecuciones/lecturas/{id}", method = RequestMethod.GET)
    public String getLecturaSensores(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("OPERADOR")) {
                return webCoreClientGrpcExtra.getLecturaSensores(id);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets registros.
     *
     * @param rut the rut
     * @return the registros
     */
    //   rpc getRegistros(RutEntityReq) returns (RegistrosReply){}
    @RequestMapping(value = "api/registros/{rut}", method = RequestMethod.GET)
    public String getRegistros(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
        if(!this.tokenEsValido(jwt)) { return "Error. Token invalido"; }

        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);

        if(usuario!=null) {
            if (usuario.getRol().equals("ADMINISTRADOR")) {
                return webCoreClientGrpcExtra.getRegistros(rut);
            }
        }
        return "Usuario sin permisos";
    }


    /**
     * Gets enum dict.
     *
     * @return the enum dict
     */
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
            objetoRetornar.addEnumList("ConexionComponente", Domain.Pin.ConexionPin.values());
            objetoRetornar.addEnumList("EstadoComponente", Domain.Componente.EstadoComponente.values());
            objetoRetornar.addEnumList("EstadoEquipo", Domain.EstadoEquipo.values());
            objetoRetornar.addEnumList("TipoPlaca", Domain.TipoPlaca.values());
            objetoRetornar.addEnumList("TipoArchivo", Domain.ArchivoEntity.TipoArchivo.values());
        }
        return objetoRetornar;
    }
}