/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.grpc.WebCoreClientGrpcImpl;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST} )
@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE} )
public class WebController {
    // ESTE SERVIDOR ES EL PUENTE ENTRE EL WEBBROWSER Y EL "CENTRAL CORE"
    @Autowired
    private WebCoreClientGrpcImpl webCoreClientGrpc;

    @Autowired
    private JwtUtil jwtUtil;

/*     private boolean tokenEsValido(String jsonwebtoken) {
         if (jsonwebtoken == null) {return false;}
         // si al obtener la llave del token (el correo) se retorna null, entonces el token está malo
         return jwtUtil.getKey(jsonwebtoken) != null;
     }*/

    // ---- USUARIOS ----------------------------------------------------------------------------------------
    // rpc authenticate(CredencialesEntityReq) returns (SesionEntityReply){}
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public GrpcSesionEntityReply authenticate(@RequestBody GrpcCredencialesEntityReq credenciales) {
        return webCoreClientGrpc.authenticate(credenciales);
    }

    // rpc addUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    //public GrpcMensajeResultadoOperacion addUsuario(@RequestBody GrpcUsuarioNuevo usuarioNuevo, @RequestHeader(value="Authorization") String jwt) {
    public GrpcMensajeReply addUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo){
        var a = webCoreClientGrpc.addUsuario(usuarioNuevo);
        return new GrpcMensajeReply();
    }

    // ***---- IMPLEMENTAR ----
    // rpc getUsuario(RutEntityReq)  returns (UsuarioEntityReply) {}
    @RequestMapping(value = "api/usuarios/{rut}", method = RequestMethod.GET)
    //public GrpcUsuarioEntityReply getUsuario(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
    public GrpcUsuarioEntityReply getUsuario(@PathVariable String rut){
        return new GrpcUsuarioEntityReply();
    }

    // ***---- IMPLEMENTAR ----
    // rpc getUsuarios(EmptyReq) returns (UsuariosEntityReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    // public GrpcUsuariosEntityReply getUsuarios(@RequestHeader(value="Authorization") String jwt) {
    public GrpcUsuariosEntityReply getUsuarios(){
        return new GrpcUsuariosEntityReply();
    }

    // ***---- IMPLEMENTAR ----
    // rpc setUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.PATCH)
    // public GrpcUsuariosEntityReply setUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar, @RequestHeader(value="Authorization") String jwt) {
    public GrpcMensajeReply setUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar){
        return new GrpcMensajeReply();
    }


    // ---- USUARIOS ----------------------------------------------------------------------------------------
    // ---- EQUIPOS  ----------------------------------------------------------------------------------------


    //   rpc addEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.POST)
    // public GrpcMensajeReply addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
    public GrpcMensajeReply addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo){
//        return webCoreClientGrpc.addEquipo(equipoNuevo);
        return new GrpcMensajeReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc setEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.PATCH)
    // public GrpcMensajeReply setEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
    public GrpcMensajeReply setEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo){
        return new GrpcMensajeReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getEquipo(IdElementoReq)  returns (EquipoEntityReply) {}
    @RequestMapping(value = "api/equipos/{id}", method = RequestMethod.GET)
    //public GrpcEquipoEntityReply getEquipo(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public GrpcEquipoEntityReply getEquipo(@PathVariable long id){
        return new GrpcEquipoEntityReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getEquipos(EmptyReq)  returns (EquiposEntityReply) {}
    @RequestMapping(value = "api/equipos", method = RequestMethod.GET)
    // public GrpcEquiposEntityReply getEquipos(@RequestHeader(value="Authorization") String jwt) {
    public GrpcEquiposEntityReply getEquipos(){
        return new GrpcEquiposEntityReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos/archivo", method = RequestMethod.POST)
    // public GrpcMensajeReply uploadArchivo(@RequestBody GrpcArchivosEquipoEntityReq archivoNuevo, @RequestHeader(value="Authorization") String jwt) {
    public GrpcMensajeReply uploadArchivo(@RequestBody GrpcArchivosEquipoEntityReq archivoNuevo){
        return new GrpcMensajeReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getArchivos(IdElementoReq)  returns (ArchivosEquipoEntityReply){}
    @RequestMapping(value = "api/equipos/archivo/{idEquipo}", method = RequestMethod.GET)
    // public GrpcArchivosEquipoEntityReply getArchivos(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
    public GrpcArchivosEquipoEntityReply getArchivos(@PathVariable long idEquipo){
        return new GrpcArchivosEquipoEntityReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getRegistros(RutEntityReq) returns (RegistrosReply){}
    @RequestMapping(value = "api/registros/{rut}", method = RequestMethod.GET)
    // public GrpcRegistrosReply getRegistros(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
    public GrpcRegistrosReply getRegistros(@PathVariable String rut){
        return new GrpcRegistrosReply();
    }


    // ---- EQUIPOS      ------------------------------------------------------------------------------------
    // ---- SIMULACIONES ------------------------------------------------------------------------------------


    //   rpc getSimulacion(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/simulaciones/{id}", method = RequestMethod.GET)
    // public GrpcSimulacionReply getSimulacion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt){
    public GrpcSimulacionReply getSimulacion(@PathVariable long id){
//        return webCoreClientGrpc.getSimulacion(id);
        return new GrpcSimulacionReply();
    }

    //   rpc getSimulaciones(EmptyReq) returns (SimulacionesReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.GET)
    // public GrpcSimulacionesReply getSimulaciones(@RequestHeader(value="Authorization") String jwt) {
    public GrpcSimulacionesReply getSimulaciones() {
//        return webCoreClientGrpc.getSimulaciones();
        return new GrpcSimulacionesReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc startSimulacion(SimulacionReq) returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.POST)
    // public GrpcMensajeReply startSimulacion(@RequestBody GrpcSimulacionReq simulacionNueva, @RequestHeader(value="Authorization") String jwt) {
    public GrpcMensajeReply startSimulacion(@RequestBody GrpcSimulacionReq simulacionNueva) {
        System.out.println("nuevaSimulacion = " + simulacionNueva);
        return new GrpcMensajeReply();
    }


    // ---- SIMULACIONES       ------------------------------------------------------------------------------
    // ---- OPERACIONES EXTRA  ------------------------------------------------------------------------------


    // ***---- IMPLEMENTAR ----
    //   rpc getSimulacionActual(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/ejecuciones/actual/{id}", method = RequestMethod.GET)
    // public GrpcSimulacionReply getSimulacionActual(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public GrpcSimulacionReply getSimulacionActual() {
        return new GrpcSimulacionReply();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getLecturasSensores(IdElementoReq) returns (stream LecturaSensoresReply) {}
    @RequestMapping(value = "api/ejecuciones/lecturas/{id}", method = RequestMethod.GET)
    // public GrpcLecturaSensoresReply getLecturasSensores(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public GrpcLecturasSensoresReply getLecturasSensores(@PathVariable long id) {
        return new GrpcLecturasSensoresReply();
    }

}