/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpc.webcoreclient.WebCoreClientGrpcEquipo;
import cl.ucn.fondef.sata.mini.grpc.webcoreclient.WebCoreClientGrpcExtra;
import cl.ucn.fondef.sata.mini.grpc.webcoreclient.WebCoreClientGrpcSimulacion;
import cl.ucn.fondef.sata.mini.grpc.webcoreclient.WebCoreClientGrpcUsuario;
import cl.ucn.fondef.sata.mini.utilities.EnumValuesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import org.springframework.web.bind.annotation.*;


/**
 * The type Web controller.
 */
@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST} )
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
    private JwtUtil jwtUtil;

/*     private boolean tokenEsValido(String jsonwebtoken) {
         if (jsonwebtoken == null) {return false;}
         // si al obtener la llave del token (el correo) se retorna null, entonces el token está malo
         return jwtUtil.getKey(jsonwebtoken) != null;
     }*/

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

    /**
     * Add usuario string.
     *
     * @param usuarioNuevo the usuario nuevo
     * @return the string
     */
// rpc addUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    //public String addUsuario(@RequestBody GrpcUsuarioNuevo usuarioNuevo, @RequestHeader(value="Authorization") String jwt) {
    public String addUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo){
        return webCoreClientGrpcUsuario.addUsuario(usuarioNuevo);
    }

    /**
     * Gets usuario.
     *
     * @param rut the rut
     * @return the usuario
     */
// rpc getUsuario(RutEntityReq)  returns (UsuarioEntityReply) {}
    @RequestMapping(value = "api/usuarios/{rut}", method = RequestMethod.GET)
    //public String getUsuario(@PathVariable String rut, @RequestHeader(value="Authorization") String jwt) {
    public String getUsuario(@PathVariable String rut) {
        return webCoreClientGrpcUsuario.getUsuario(rut);
    }

    /**
     * Gets usuarios.
     *
     * @return the usuarios
     */
// rpc getUsuarios(EmptyReq) returns (UsuariosEntityReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    // public String getUsuarios(@RequestHeader(value="Authorization") String jwt) {
    public String getUsuarios(){
        return webCoreClientGrpcUsuario.getUsuarios();
    }

    /**
     * Sets usuario.
     *
     * @param usuarioModificar the usuario modificar
     * @return the usuario
     */
    // rpc updateUsuario(UsuarioEntityReq)  returns (MensajeReply) {}
    @RequestMapping(value = "api/usuarios", method = RequestMethod.PATCH)
    // public String updateUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar, @RequestHeader(value="Authorization") String jwt) {
    public String updateUsuario(@RequestBody GrpcUsuarioEntityReq usuarioModificar){
        return webCoreClientGrpcUsuario.updateUsuario(usuarioModificar);
    }


    // ---- USUARIOS ----------------------------------------------------------------------------------------
    // ---- EQUIPOS  ----------------------------------------------------------------------------------------


    /**
     * Add equipo string.
     *
     * @param equipoNuevo the equipo nuevo
     * @return the string
     */
//   rpc addEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.POST)
    // public String addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
    public String addEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo){
        return webCoreClientGrpcEquipo.addEquipo(equipoNuevo);
    }

    /**
     * Sets equipo.
     *
     * @param equipoModificado the equipo modificado
     * @return the equipo
     */
// ***---- IMPLEMENTAR ----
    //   rpc updateEquipo(EquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos", method = RequestMethod.PATCH)
    // public String updateEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo, @RequestHeader(value="Authorization") String jwt){
    public String updateEquipo(@RequestBody GrpcEquipoEntityReq equipoModificado){
        return webCoreClientGrpcEquipo.updateEquipo(equipoModificado);
    }

    /**
     * Gets equipo.
     *
     * @param id the id
     * @return the equipo
     */
// ***---- IMPLEMENTAR ----
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
// ***---- IMPLEMENTAR ----
    //   rpc getEquipos(EmptyReq)  returns (EquiposEntityReply) {}
    @RequestMapping(value = "api/equipos", method = RequestMethod.GET)
    // public String getEquipos(@RequestHeader(value="Authorization") String jwt) {
    public String getEquipos(){
        return webCoreClientGrpcEquipo.getEquipos();
    }

    /**
     * Upload archivo string.
     *
     * @param archivoNuevo the archivo nuevo
     * @return the string
     */
// ***---- IMPLEMENTAR ----
    //   rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
    @RequestMapping(value = "api/equipos/archivo", method = RequestMethod.POST)
    // public String uploadArchivo(@RequestBody GrpcArchivosEquipoEntityReq archivoNuevo, @RequestHeader(value="Authorization") String jwt) {
    public String uploadArchivo(@RequestBody GrpcArchivosEquipoEntityReq archivoNuevo){
        return webCoreClientGrpcEquipo.uploadArchivo(archivoNuevo);
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
// ***---- IMPLEMENTAR ----
    //   rpc getValvulasEquipo(IdElementoReq) returns (ComponentesEquipoReply) {}
    @RequestMapping(value = "api/equipos/valvulas/{idEquipo}", method = RequestMethod.GET)
    // public String getValvulasEquipo(@PathVariable long idEquipo, @RequestHeader(value="Authorization") String jwt) {
    public String getValvulasEquipo(@PathVariable long idEquipo){
        return webCoreClientGrpcEquipo.getValvulasEquipo(idEquipo);
    }

    // ---- EQUIPOS      ------------------------------------------------------------------------------------
    // ---- SIMULACIONES ------------------------------------------------------------------------------------


    /**
     * Gets simulacion.
     *
     * @param id the id
     * @return the simulacion
     */
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
// ***---- IMPLEMENTAR ----
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
// ***---- IMPLEMENTAR ----
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
        objetoRetornar.addEnumList("TipoComponente", Domain.ComponenteFisico.TipoComponente.values());
        objetoRetornar.addEnumList("ConexionComponente", Domain.Pin.ConexionPin.values());
        objetoRetornar.addEnumList("EstadoComponente", Domain.ComponenteFisico.EstadoComponente.values());
        objetoRetornar.addEnumList("EstadoEquipo", Domain.EstadoEquipo.values());
        objetoRetornar.addEnumList("TipoArchivo", Domain.ArchivoEquipoEntityReq.TipoArchivo.values());

        return objetoRetornar;
    }
}