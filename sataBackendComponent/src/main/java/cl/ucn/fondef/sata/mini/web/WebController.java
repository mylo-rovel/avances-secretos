/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpc.webcoreclient.*;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.utilities.EnumValuesResponse;
import cl.ucn.fondef.sata.mini.web.webrequests.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * The type Web controller.
 */
@Slf4j
@RestController
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

     private boolean tokenEsInvalido(String jsonwebtoken) {
        try {
            if (jsonwebtoken == null) {return true;}
            // si al obtener la llave del token (el rut) se retorna null, entonces el token está malo
            return jwtUtil.getKey(jsonwebtoken) == null;
        }
        catch (Exception ex) {
            log.warn("Error al validar el token");
            log.warn(ex.getMessage());
            return true;
        }
     }

    /**
     * Authenticate string.
     *
     * @param credenciales the credenciales
     * @return the string
     */
// ---- USUARIOS ----------------------------------------------------------------------------------------
    // rpc authenticate(WebReqCredencialesReq) returns (SesionEntityReply){}
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String authenticate(@RequestBody WebReqCredencialesReq credenciales) {
        return webCoreClientGrpcUsuario.authenticate(credenciales);
    }


    /**
     * Add usuario string.
     *
     * @param usuarioNuevo the usuario nuevo
     * @param jwt          the jwt
     * @return the string
     */
// rpc addUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public String addUsuario(@RequestBody WebReqUsuarioReq usuarioNuevo, @RequestHeader(value="Authorization") String jwt) {
/*//        SOLO USAR CUANDO LA BASE DE DATOS ESTA VACIA
       return webCoreClientGrpcUsuario.addUsuario(usuarioNuevo);*/
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
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
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null){
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())){
                return webCoreClientGrpcUsuario.getUsuario(rut);
            }
        }
        return "Usuario sin permisos";
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
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null){
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())) {
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
    public String updateUsuario(@RequestBody WebReqUsuarioReq usuarioModificar, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
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


    /**
     * Add equipo string.
     *
     * @param equipoNuevo the equipo nuevo
     * @param jwt         the jwt
     * @return the string
     */
//   rpc addEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.POST)
     public String addEquipo(@RequestBody WebReqEquipoReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
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

    /**
     * Update equipo string.
     *
     * @param equipoModificado the equipo modificado
     * @param jwt              the jwt
     * @return the string
     */
//   rpc updateEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.PATCH)
    public String updateEquipo(@RequestBody WebReqEquipoReq equipoModificado, @RequestHeader(value="Authorization") String jwt){
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
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

    /**
     * Gets equipo.
     *
     * @param id  the id
     * @param jwt the jwt
     * @return the equipo
     */
//   rpc getEquipo(IdElementoReq)  returns (EquipoEntityReply) {}
    @RequestMapping(value = "api/equipos/{id}", method = RequestMethod.GET)
    public String getEquipo(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
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

    /**
     * Gets equipos.
     *
     * @param jwt the jwt
     * @return the equipos
     */
//   rpc getEquipos(EmptyReq)  returns (EquiposEntityReply) {}
    @RequestMapping(value = "api/equipos", method = RequestMethod.GET)
    public String getEquipos(@RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null){
            if(usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) || usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())){
                return webCoreClientGrpcEquipo.getEquipos(rutUsuario.getRut());
            }
        }
        return "Usario sin permisos";
    }

    /**
     * Upload archivo string.
     *
     * @param file the file
     * @param jwt  the jwt
     * @return the string
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
// ***---- IMPLEMENTAR ----
    //   rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos/archivos", method = RequestMethod.POST)
    public String uploadArchivo(@RequestParam("file") MultipartFile file, @RequestHeader(value="Authorization") String jwt) throws IOException, InterruptedException {
//        ESTE ENDPOINT ESTA A MEDIAS. ENVIA LOS ARCHIVOS POR CHUNKS
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                webCoreStreamClientGrpc.uploadArchivo(file);
                return file.getOriginalFilename();
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets archivos.
     *
     * @param idEquipo the id equipo
     * @param jwt      the jwt
     * @return the archivos
     */
// ***---- IMPLEMENTAR ----
    //   rpc getArchivos(IdElementoReq)  returns (ArchivosEquipoEntityReply){}
    @RequestMapping(value = "api/equipos/archivo/{idEquipo}", method = RequestMethod.GET)
    public String getArchivos(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                return "Aun no implementado";//webCoreClientGrpcEquipo.getArchivos(idEquipo);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets valvulas equipo.
     *
     * @param idEquipo the id equipo
     * @param jwt      the jwt
     * @return the valvulas equipo
     */
//   rpc getValvulasEquipo(IdElementoReq) returns (ComponentesEquipoReply) {}
    @RequestMapping(value = "api/equipos/valvulas/{idEquipo}", method = RequestMethod.GET)
    public String getValvulasEquipo(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                return webCoreClientGrpcEquipo.getValvulasEquipo(idEquipo);
            }
        }
        return "Usuario sin permisos";
    }


    /**
     * Gets secuencias componente.
     *
     * @param idComponente the id componente
     * @param jwt          the jwt
     * @return the secuencias componente
     */
// ***---- ACTUALIZAR ----
    //    rpc getSecuenciasComponente(IdElementoReq) returns (SecuenciasComponenteReply) {}
    @RequestMapping(value = "api/equipos/secuencias/{idComponente}", method = RequestMethod.GET)
    public String getSecuenciasComponente(@PathVariable long idComponente, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name()) ||  usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.CONFIGURADOR.name())) {
                return webCoreClientGrpcEquipo.getSecuenciasComponente(idComponente);
            }
        }
        return "Usuario sin permisos";
    }

    //   rpc getRegistros(RutEntityReq) returns (RegistrosReply){}
    @RequestMapping(value = "api/registros/{rut}", method = RequestMethod.GET)
    public String getRegistros(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR.name())) {
                return webCoreClientGrpcExtra.getRegistros(rut);
            }
        }
        return "Usuario sin permisos";
    }


    // ---- EQUIPOS      ------------------------------------------------------------------------------------
    // ---- SIMULACIONES ------------------------------------------------------------------------------------

    /**
     * Add simulacion string.
     *
     * @param simulacionReq the simulacion req
     * @param jwt           the jwt
     * @return the string
     */
// rpc addSimulacion(SimulacionReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones/secuencias/", method = RequestMethod.POST)
    public String addSimulacion(@RequestBody WebReqSimulacionReq simulacionReq, @RequestHeader(value="Authorization") String jwt){
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
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

    /**
     * Gets simulacion.
     *
     * @param id  the id
     * @param jwt the jwt
     * @return the simulacion
     */
//   rpc getSimulacion(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/simulaciones/{id}", method = RequestMethod.GET)
    public String getSimulacion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt){
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getSimulacion(id, this.getTokenKey(jwt));
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets simulaciones.
     *
     * @param jwt the jwt
     * @return the simulaciones
     */
//   rpc getSimulaciones(EmptyReq) returns (SimulacionesReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.GET)
    public String getSimulaciones(@RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getSimulaciones(this.getTokenKey(jwt));
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Start simulacion string.
     *
     * @param startSimulacionReq the start simulacion req
     * @param jwt                the jwt
     * @return the string
     */
// ***---- IMPLEMENTAR => TODO: ARREGLAR DOMAIN.PROTO => Sólo recibimos equipo y simulacion ----
    //   rpc startSimulacion(SimulacionReq) returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.POST)
    public String startSimulacion(@RequestBody WebReqStartSimulacionReq startSimulacionReq, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.startSimulacion(startSimulacionReq);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets equipos trabajando.
     *
     * @param jwt the jwt
     * @return the equipos trabajando
     */
// ***---- IMPLEMENTAR ----
    //   rpc getEquiposTrabajando(EmptyReq) returns (EquiposEntityReply){}
    @RequestMapping(value = "api/equipos/trabajando", method = RequestMethod.GET)
    public String getEquiposTrabajando(@RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getEquiposTrabajando();
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets ejecucion.
     *
     * @param id  the id
     * @param jwt the jwt
     * @return the ejecucion
     */
// TODO: ARREGLAR => ESTAMOS ENTREGANDO LA ID DE LA EJECUCION, NO DE LA SIMULACION
    //   rpc getEjecucion(IdElementoReq) returns (EjecucionReply){}
    @RequestMapping(value = "api/ejecuciones/{id}", method = RequestMethod.GET)
    public String getEjecucion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                return webCoreClientGrpcSimulacion.getEjecucion(id, this.getTokenKey(jwt));
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets ejecuciones.
     *
     * @param jwt the jwt
     * @return the ejecuciones
     */
//   rpc getEjecuciones(EmptyReq) returns (EjecucionesReply){}
    @RequestMapping(value = "api/ejecuciones/", method = RequestMethod.GET)
    public String getEjecuciones(@RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
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


    /**
     * Gets lectura sensores.
     *
     * @param jwt the jwt
     * @return the lectura sensores
     */
// ***---- IMPLEMENTAR ----
    //   rpc getValoresGrafico(EstadoGraficoUsuarioReq) returns (LecturaSensoresReply) {}
    @RequestMapping(value = "api/ejecuciones/valoresgrafico", method = RequestMethod.POST)
    public String getValoresGrafico(@RequestBody WebReqEstadoGraficoUsuarioReq reqObj, @RequestHeader(value="Authorization") String jwt) {
        if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(this.getTokenKey(jwt)).build();
        Usuario usuario = coreDaoUsuario.getUsuario(rutUsuario);
        if(usuario!=null) {
            if (usuario.getRol().equals(Domain.UsuarioEntity.RolUsuario.OPERADOR.name())) {
                log.info("ReqObj" + reqObj);
                if (reqObj.getIndiceInicial() > reqObj.getIndiceFinal()) {
                    return "Indices incorrectos";
                }
                return webCoreClientGrpcSimulacion.getValoresGrafico(reqObj);
            }
        }
        return "Usuario sin permisos";
    }

    /**
     * Gets registros.
     * @param jwt the jwt
     * @return the registros
     */
//    rpc getSensores (SensoresEquipoEntityReq) returns (SensoresEquipoEntityReply) {}
    @RequestMapping(value = "api/equipos/sensores/{idEquipo}", method = RequestMethod.GET)
    public String getSensores(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
        //if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        return webCoreClientGrpcEquipo.getSensores(idEquipo);
    }
//rpc getSimulacionesEjecutadas (DatosSimulacionesEquipoMesReq) returns (SimulacionesEquipoMesReply) {}
    @RequestMapping(value = "api/equipos/simulaciones/{idEquipo}/{mes}", method = RequestMethod.GET)
    public String getSimulacionesEjecutadas (@PathVariable long idEquipo, @PathVariable int mes, @RequestHeader(value="Authorization") String jwt) {
        //if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        return webCoreClientGrpcSimulacion.getSimulacionesEjecutadas(idEquipo, mes);
    }
   // rpc getDatosResumen (DatosSimulacionReq) returns (ResumenSimulacionReply) {} {}
   @RequestMapping(value = "api/equipos/simulaciones/{idSimulacion}/{caudal}/{temperatura}/{pluviometro}/{presion}/{humedad}", method = RequestMethod.GET)
   public String getDatosResumen (@PathVariable long idSimulacion, @PathVariable int caudal, @PathVariable int temperatura, @PathVariable int pluviometro, @PathVariable int presion, @PathVariable int humedad, @RequestHeader(value="Authorization") String jwt) {
       //if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
       return webCoreClientGrpcSimulacion.getDatosResumen(idSimulacion, caudal, temperatura, pluviometro, presion, humedad);
   }
    //rpc getMedidas (DatosEjecucionSensorReq) returns (MedidasEjecucionSensorReply) {}
    @RequestMapping(value = "api/ejecuciones/{idEjecucion}/{idSensor}", method = RequestMethod.GET)
    public String getMedidas (@PathVariable int idEjecucion, @PathVariable int idSensor, @RequestHeader(value="Authorization") String jwt) {
        //if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        return webCoreClientGrpcSimulacion.getMedidas(idEjecucion, idSensor);
    }

    // rpc getUltimaMedida (DatosEjecucionUltimaMedidaReq) returns (UltimaMedidaReply) {}
    @RequestMapping(value = "api/ejecuciones/ultima_medida", method = RequestMethod.POST)
    public String getUltimaMedida (@RequestBody WebReqUltimaMedida ultimaMedidaObj, @RequestHeader(value="Authorization") String jwt) {
        //if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        var idEjecucion = ultimaMedidaObj.getId_ejecucion();
        var idSensor = ultimaMedidaObj.getId_sensor();
        return webCoreClientGrpcSimulacion.getUltimaMedida(idEjecucion, idSensor);
    }

    // rpc getUltimasMedidas (UltimosDatosEjecucionReq) returns (UltimasMedidasEjecucionReply) {}
    @RequestMapping(value = "api/ejecuciones/UltimasMedidas", method = RequestMethod.POST)
    public String getUltimasMedidas (@RequestBody WebReqUltimasMedidas UltimasMedidas, @RequestHeader(value="Authorization") String jwt) {
       //if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
       var idEjecucion = UltimasMedidas.getId_ejecucion();
       var idSensor = UltimasMedidas.getId_sensor();
       var timestamp = UltimasMedidas.getTimestamp();
       var lastSecond = UltimasMedidas.getLast_second();
       var lastEntrities = UltimasMedidas.getLast_entrities();
       return webCoreClientGrpcSimulacion.getUltimasMedidas(idEjecucion, idSensor, timestamp, lastSecond, lastEntrities);
    }

    //  rpc getUmbralesPorSensor (UmbralesSensorReq) returns (UmbralesSensorReply) {}
    @RequestMapping(value = "api/equipos/sensores/umbrales/{idSensor}", method = RequestMethod.GET)
    public String getUmbralesPorSensor(@PathVariable long idSensor, @RequestHeader(value="Authorization") String jwt) {
        //if(this.tokenEsInvalido(jwt)) { return "Error. Token invalido"; }
        return webCoreClientGrpcEquipo.getUmbralesPorSensor(idSensor);
    }
   /**
     * Gets enum dict.
     *
     * @param jwt the jwt
     * @return the enum dict
     */
// ESTE ENDPOINT EXISTE PARA FACILITARLE LA VIDA AL FRONTEND EN LAS PARTES EN LA QUE
    // SE REQUIERE INPUTS DEL USUARIO => ENTREGANDO LAS LISTA DE LOS VALORES DE LAS ENUMERACIONES
    // PODEMOS CREAR LISTAS DESPLEGABLES EN EL FRONTEND QUE GARANTIZAN EL BUEN FUNCIONAMIENTO
    // DE LOS CAMPOS CON ENUMS (opciones limitadas para los usuarios => que solo elijan lo que queremos)
    @RequestMapping(value = "api/extras/enums", method = RequestMethod.GET)
    public EnumValuesResponse getEnumDict(@RequestHeader(value="Authorization") String jwt) {
        EnumValuesResponse objetoRetornar = new EnumValuesResponse();
        if(this.tokenEsInvalido(jwt)) { return null; }
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