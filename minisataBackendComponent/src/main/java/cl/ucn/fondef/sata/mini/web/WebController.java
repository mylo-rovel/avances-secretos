/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpc.webcoreclient.*;
import cl.ucn.fondef.sata.mini.utilities.BytesChunker;
import cl.ucn.fondef.sata.mini.utilities.EnumValuesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * The type Web controller.
 */
@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE} )
@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE} )
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
//    public String addUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo, @RequestHeader(value="Authorization") String jwt) {
    public String addUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo){
/*        if (this.tokenEsValido(jwt)){
            return "Error. Token invalido";
        }*/
        return webCoreClientGrpcUsuario.addUsuario(usuarioNuevo);
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
//    public String getUsuario(@PathVariable String rut) {
        if (this.tokenEsValido(jwt)){
            return webCoreClientGrpcUsuario.getUsuario(rut);
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
//    public String getUsuarios(){
        if (this.tokenEsValido(jwt)){
            return webCoreClientGrpcUsuario.getUsuarios();
        }
        return "Error. Token invalido";
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
//    public String updateUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar){
        if (this.tokenEsValido(jwt)){
            return webCoreClientGrpcUsuario.updateUsuario(usuarioModificar);
        }
        return "Error. Token invalido";
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
//    public String addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo){
        if (this.tokenEsValido(jwt)){
            equipoNuevo.setRutConfigurador(this.getTokenKey(jwt));
            return webCoreClientGrpcEquipo.addEquipo(equipoNuevo);
        }
        return "Error. Token invalido";
    }

    /**
     * Update equipo string.
     *
     * @param equipoModificado the equipo modificado
     * @return the string
     */
    // ***---- ACTUALIZAR CAMPOS ----
    //   rpc updateEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.PATCH)
//    public String updateEquipo(@RequestBody GrpcEquipoEntityReq equipoModificado){
    public String updateEquipo(@RequestBody GrpcEquipoEntityReq equipoModificado, @RequestHeader(value="Authorization") String jwt){
        if (this.tokenEsValido(jwt)){
            equipoModificado.setRutConfigurador(this.getTokenKey(jwt));
            return webCoreClientGrpcEquipo.updateEquipo(equipoModificado);
        }
        return "Error. Token invalido";
    }

    /**
     * Gets equipo.
     *
     * @param id the id
     * @return the equipo
     */
    //   rpc getEquipo(IdElementoReq)  returns (EquipoEntityReply) {}
    @RequestMapping(value = "api/equipos/{id}", method = RequestMethod.GET)
    //public String getEquipo(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public String getEquipo(@PathVariable long id){
        return webCoreClientGrpcEquipo.getEquipo(id);
    }

    /**
     * Gets equipos.
     *
     * @return the equipos
     */
    //   rpc getEquipos(EmptyReq)  returns (EquiposEntityReply) {}
    @RequestMapping(value = "api/equipos", method = RequestMethod.GET)
    // public String getEquipos(@RequestHeader(value="Authorization") String jwt) {
    public String getEquipos(){
        return webCoreClientGrpcEquipo.getEquipos();
    }

// ***---- IMPLEMENTAR ----
    //   rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos/archivos", method = RequestMethod.POST)
    // public String uploadArchivo(@RequestBody GrpcArchivosEntityReq archivoNuevo, @RequestHeader(value="Authorization") String jwt) {
//    public String uploadArchivo(@RequestBody GrpcArchivosEntityReq archivoNuevo){
    public String uploadArchivo(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
//        return webCoreClientGrpcEquipo.uploadArchivo(archivoNuevo);
        var baitsArr =file.getBytes();
        System.out.println("baitsArr length = " + baitsArr.length);
        var contentType = file.getContentType();
        System.out.println("lastElement = " + contentType);
        var fileSize = file.getSize();
        System.out.println("fileSize = " + fileSize);

        webCoreStreamClientGrpc.uploadArchivo(file);
        return file.getOriginalFilename();
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
    // public String getArchivos(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
    public String getArchivos(@PathVariable long idEquipo){
        return webCoreClientGrpcEquipo.getArchivos(idEquipo);
    }

    /**
     * Gets valvulas equipo.
     *
     * @param idEquipo the id equipo
     * @return the valvulas equipo
     */
    //   rpc getValvulasEquipo(IdElementoReq) returns (ComponentesEquipoReply) {}
    @RequestMapping(value = "api/equipos/valvulas/{idEquipo}", method = RequestMethod.GET)
    // public String getValvulasEquipo(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
    public String getValvulasEquipo(@PathVariable long idEquipo){
        return webCoreClientGrpcEquipo.getValvulasEquipo(idEquipo);
    }


    // ***---- TESTEAR ----
    //    rpc getSecuenciasComponente(IdElementoReq) returns (SecuenciasComponenteReply) {}
    @RequestMapping(value = "api/equipos/secuencias/{idComponente}", method = RequestMethod.GET)
    // public String getSecuenciasComponente(@PathVariable long idComponente, @RequestHeader(value="Authorization") String jwt) {
    public String getSecuenciasComponente(@PathVariable long idComponente){
        return webCoreClientGrpcEquipo.getSecuenciasComponente(idComponente);
    }


    // ---- EQUIPOS      ------------------------------------------------------------------------------------
    // ---- SIMULACIONES ------------------------------------------------------------------------------------


    /**
     * Gets simulacion.
     *
     * @param id the id
     * @return the simulacion
     */
    // ***---- ACTUALIZAR: AGREGAR AGUA CAIDA Y SECUENCIAS ----
//   rpc getSimulacion(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/simulaciones/{id}", method = RequestMethod.GET)
    // public String getSimulacion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt){
    public String getSimulacion(@PathVariable long id){
        return webCoreClientGrpcSimulacion.getSimulacion(id);
    }

    /**
     * Gets simulaciones.
     *
     * @return the simulaciones
     */
    // ***---- ACTUALIZAR: ARREGLAR DATO AGUA CAIDA ----
//   rpc getSimulaciones(EmptyReq) returns (SimulacionesReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.GET)
    // public String getSimulaciones(@RequestHeader(value="Authorization") String jwt) {
    public String getSimulaciones() {
        return webCoreClientGrpcSimulacion.getSimulaciones();

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
    // public String startSimulacion(@RequestBody GrpcSimulacionReq simulacionNueva, @RequestHeader(value="Authorization") String jwt) {
    public String startSimulacion(@RequestBody GrpcSimulacionReq simulacionNueva) {
        log.info("nuevaSimulacion = " + simulacionNueva);
        return webCoreClientGrpcSimulacion.startSimulacion(simulacionNueva);
    }

    /**
     * Gets simulacion actual.
     *
     * @return the simulacion actual
     */
// ***---- IMPLEMENTAR ----
    //   rpc getSimulacionActual(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/ejecuciones/actual/{id}", method = RequestMethod.GET)
    // public String getSimulacionActual(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public String getSimulacionActual() {
        return webCoreClientGrpcSimulacion.getSimulacionActual();
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
    // public String getLecturaSensores(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public String getLecturaSensores(@PathVariable long id) {
        return webCoreClientGrpcExtra.getLecturaSensores(id);
    }

    /**
     * Gets registros.
     *
     * @param rut the rut
     * @return the registros
     */
    //   rpc getRegistros(RutEntityReq) returns (RegistrosReply){}
    @RequestMapping(value = "api/registros/{rut}", method = RequestMethod.GET)
    // public String getRegistros(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
    public String getRegistros(@PathVariable String rut){
        return webCoreClientGrpcExtra.getRegistros(rut);
    }


    /**
     * Gets enum dict.
     *
     * @return the enum dict
     */
    @RequestMapping(value = "api/extras/enums", method = RequestMethod.GET)
    // public EnumValuesResponse getEnumDict(@RequestHeader(value="Authorization") String jwt) {
    public EnumValuesResponse getEnumDict() {
        EnumValuesResponse objetoRetornar = new EnumValuesResponse();
        objetoRetornar.addEnumList("RolUsuario", Domain.UsuarioEntity.RolUsuario.values());
        objetoRetornar.addEnumList("EstadoUsuario", Domain.UsuarioEntity.EstadoUsuario.values());
        objetoRetornar.addEnumList("TipoRegistro", Domain.Registro.TipoRegistro.values());
        objetoRetornar.addEnumList("TipoComponente", Domain.Componente.TipoComponente.values());
        objetoRetornar.addEnumList("ConexionComponente", Domain.Pin.ConexionPin.values());
        objetoRetornar.addEnumList("EstadoComponente", Domain.Componente.EstadoComponente.values());
        objetoRetornar.addEnumList("EstadoEquipo", Domain.EstadoEquipo.values());
        objetoRetornar.addEnumList("TipoPlaca", Domain.TipoPlaca.values());
        objetoRetornar.addEnumList("TipoArchivo", Domain.ArchivoEntity.TipoArchivo.values());

        return objetoRetornar;
    }
}