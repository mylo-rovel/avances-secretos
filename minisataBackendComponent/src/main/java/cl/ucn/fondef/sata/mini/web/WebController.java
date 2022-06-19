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
    public String authenticate(@RequestBody GrpcCredencialesEntityReq credenciales) {
        return webCoreClientGrpc.authenticate(credenciales);
    }

    // rpc addUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    //public String addUsuario(@RequestBody GrpcUsuarioNuevo usuarioNuevo, @RequestHeader(value="Authorization") String jwt) {
    public String addUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo){
        return webCoreClientGrpc.addUsuario(usuarioNuevo);
    }

    // ***---- IMPLEMENTAR ----
    // rpc getUsuario(RutEntityReq)  returns (UsuarioEntityReply) {}
    @RequestMapping(value = "api/usuarios/{rut}", method = RequestMethod.GET)
    //public String getUsuario(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
    public String getUsuario(@PathVariable String rut) {
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    // rpc getUsuarios(EmptyReq) returns (UsuariosEntityReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    // public String getUsuarios(@RequestHeader(value="Authorization") String jwt) {
    public String getUsuarios(){
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    // rpc setUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.PATCH)
    // public String setUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar, @RequestHeader(value="Authorization") String jwt) {
    public String setUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar){
        return new String();
    }


    // ---- USUARIOS ----------------------------------------------------------------------------------------
    // ---- EQUIPOS  ----------------------------------------------------------------------------------------


    //   rpc addEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.POST)
    // public String addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
    public String addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo){
        return webCoreClientGrpc.addEquipo(equipoNuevo);
//        TODO (emilio): REHACER CONSIDERANDO NUEVOS COMPONENTES (clase Pin)
//        return new String();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc setEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.PATCH)
    // public String setEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
    public String setEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo){
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getEquipo(IdElementoReq)  returns (EquipoEntityReply) {}
    @RequestMapping(value = "api/equipos/{id}", method = RequestMethod.GET)
    //public String getEquipo(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public String getEquipo(@PathVariable long id){
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getEquipos(EmptyReq)  returns (EquiposEntityReply) {}
    @RequestMapping(value = "api/equipos", method = RequestMethod.GET)
    // public String getEquipos(@RequestHeader(value="Authorization") String jwt) {
    public String getEquipos(){
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos/archivo", method = RequestMethod.POST)
    // public String uploadArchivo(@RequestBody GrpcArchivosEquipoEntityReq archivoNuevo, @RequestHeader(value="Authorization") String jwt) {
    public String uploadArchivo(@RequestBody GrpcArchivosEquipoEntityReq archivoNuevo){
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getArchivos(IdElementoReq)  returns (ArchivosEquipoEntityReply){}
    @RequestMapping(value = "api/equipos/archivo/{idEquipo}", method = RequestMethod.GET)
    // public String getArchivos(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
    public String getArchivos(@PathVariable long idEquipo){
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getRegistros(RutEntityReq) returns (RegistrosReply){}
    @RequestMapping(value = "api/registros/{rut}", method = RequestMethod.GET)
    // public String getRegistros(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
    public String getRegistros(@PathVariable String rut){
        return new String();
    }


    // ---- EQUIPOS      ------------------------------------------------------------------------------------
    // ---- SIMULACIONES ------------------------------------------------------------------------------------


    //   rpc getSimulacion(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/simulaciones/{id}", method = RequestMethod.GET)
    // public String getSimulacion(@PathVariable long id, @RequestHeader(value="Authorization") String jwt){
    public String getSimulacion(@PathVariable long id){
        return webCoreClientGrpc.getSimulacion(id);
    }

    //   rpc getSimulaciones(EmptyReq) returns (SimulacionesReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.GET)
    // public String getSimulaciones(@RequestHeader(value="Authorization") String jwt) {
    public String getSimulaciones() {
        return webCoreClientGrpc.getSimulaciones();

    }

    // ***---- IMPLEMENTAR ----
    //   rpc startSimulacion(SimulacionReq) returns (MensajeReply){}
    @RequestMapping(value = "api/simulaciones", method = RequestMethod.POST)
    // public String startSimulacion(@RequestBody GrpcSimulacionReq simulacionNueva, @RequestHeader(value="Authorization") String jwt) {
    public String startSimulacion(@RequestBody GrpcSimulacionReq simulacionNueva) {
        System.out.println("nuevaSimulacion = " + simulacionNueva);
        return new String();
    }


    // ---- SIMULACIONES       ------------------------------------------------------------------------------
    // ---- OPERACIONES EXTRA  ------------------------------------------------------------------------------


    // ***---- IMPLEMENTAR ----
    //   rpc getSimulacionActual(IdElementoReq) returns (SimulacionReply){}
    @RequestMapping(value = "api/ejecuciones/actual/{id}", method = RequestMethod.GET)
    // public String getSimulacionActual(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public String getSimulacionActual() {
        return new String();
    }

    // ***---- IMPLEMENTAR ----
    //   rpc getLecturaSensores(IdElementoReq) returns (stream LecturaSensoresReply) {}
    @RequestMapping(value = "api/ejecuciones/lecturas/{id}", method = RequestMethod.GET)
    // public String getLecturaSensores(@PathVariable long id, @RequestHeader(value="Authorization") String jwt) {
    public String getLecturaSensores(@PathVariable long id) {
        return new String();
    }

}