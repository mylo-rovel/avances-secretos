/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import cl.ucn.fondef.sata.mini.grpcobjects.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.grpc.WebCoreClientGrpcImpl;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
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
    public GrpcObjetoSesion loginUsuario(@RequestBody GrpcCredenciales grpcCredenciales) {
        System.out.println("AAAAgrpcCredenciales = " + grpcCredenciales);
        return webCoreClientGrpc.loginUsuario(grpcCredenciales);
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    //public GrpcMensajeResultadoOperacion registrarUsuario(@RequestHeader(value="Authorization") String jwt, @RequestBody GrpcUsuarioNuevo usuarioNuevo) {
      public GrpcMensajeResultadoOperacion registrarUsuario(@RequestBody GrpcUsuarioNuevo usuarioNuevo){
        //if (!tokenEsValido(jwt)){
        //    return "No hay permisos";
        //}
        return webCoreClientGrpc.agregarUsuario(usuarioNuevo);
    }

    @RequestMapping(value = "api/simulaciones", method = RequestMethod.POST)
    public boolean displaySimulacionEnviada(@RequestHeader(value="Authorization") String jwt,
                                            @RequestBody GrpcParametrosInicioSimulacion parametrosInicioSimulacion) {
        System.out.println("nuevaSimulacion = " + parametrosInicioSimulacion);
        return tokenEsValido(jwt);
    }

    @RequestMapping(value = "api/simulador", method = RequestMethod.POST)
      public GrpcMensajeResultadoOperacion registrarSimulador(@RequestBody GrpcSimulador simulador){
      return webCoreClientGrpc.crearSimulador(simulador);
    }

}
