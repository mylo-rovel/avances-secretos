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
@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST} )
public class WebController {
    // ESTE SERVIDOR ES EL PUENTE ENTRE EL WEBBROWSER Y EL "CENTRAL CORE"
    @Autowired
    private WebCoreClientGrpcImpl webCoreClientGrpc;

    @Autowired
    private JwtUtil jwtUtil;

    private boolean tokenEsValido(String jsonwebtoken) {
        if (jsonwebtoken == null) {return false;}
        // si al obtener la llave del token (el correo) se retorna null, entonces el token está malo
        return jwtUtil.getKey(jsonwebtoken) != null;
    }

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public GrpcSesionEntityReply loginUsuario(@RequestBody GrpcCredencialesEntityReq credenciales) {
        return webCoreClientGrpc.authenticate(credenciales);
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    //public GrpcMensajeResultadoOperacion registrarUsuario(@RequestHeader(value="Authorization") String jwt, @RequestBody GrpcUsuarioNuevo usuarioNuevo) {
      public GrpcMensajeReply registrarUsuario(@RequestBody GrpcUsuarioEntityReq usuarioNuevo){
        var a = webCoreClientGrpc.addUsuario(usuarioNuevo);
        return new GrpcMensajeReply();
    }
/*
    @RequestMapping(value = "api/equipos", method = RequestMethod.POST)
    //recordar añadir lo de jwt: @RequestHeader(value="Authorization") String jwt,
    public GrpcMensajeReply registrarEquipo(@RequestBody GrpcEquipoEntityReq equipoNuevo){
        return webCoreClientGrpc.addEquipo(equipoNuevo);
    }

    @RequestMapping(value = "api/simulaciones", method = RequestMethod.POST)
    public GrpcSimulacionReq displaySimulacionEnviada(@RequestBody GrpcSimulacionReq simulacionNueva) {
        System.out.println("nuevaSimulacion = " + simulacionNueva);
        return simulacionNueva;
    }

    @RequestMapping(value = "api/simulaciones", method = RequestMethod.GET)
    public GrpcSimulacionesReply mostrarSimulaciones() {
        return webCoreClientGrpc.getSimulaciones();
    }

    @RequestMapping(value = "api/simulaciones/{id}", method = RequestMethod.GET)
    public borrarLuegoSimuEspecifica mostrarSimulacionEspecifica(@PathVariable long id){
        return webCoreClientGrpc.getSimulacion(id);
    }*/

}
