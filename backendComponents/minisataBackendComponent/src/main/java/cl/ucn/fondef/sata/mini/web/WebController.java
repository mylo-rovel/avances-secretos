/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.grpc.WebCoreClientGrpcImpl;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcCredenciales;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcObjetoSesion;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcParametrosInicioSimulacion;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class WebController {
    // ESTE SERVIDOR ES EL PUENTE ENTRE EL WEBBROWSER Y EL "CENTRAL CORE"

    @Autowired
    private WebCoreClientGrpcImpl webCoreClientGrpc;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public GrpcObjetoSesion handleLedRequest(@RequestBody GrpcCredenciales grpcCredenciales) {
        // acá se podrían checkear los campos entregados por el navegador
        return webCoreClientGrpc.loginUsuario(grpcCredenciales);
    }

    @RequestMapping(value = "api/simulacion", method = RequestMethod.POST)
    public boolean displaySimulacionEnviada(@RequestBody GrpcParametrosInicioSimulacion parametrosInicioSimulacion) {
        System.out.println("nuevaSimulacion = " + parametrosInicioSimulacion);
        return true;
    }

}
