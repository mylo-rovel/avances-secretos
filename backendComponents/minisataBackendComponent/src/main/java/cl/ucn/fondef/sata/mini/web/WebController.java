/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web;

import cl.ucn.fondef.sata.mini.coredao.CoreDao;
import cl.ucn.fondef.sata.mini.grpc.CoreClientGrpcImpl;
import cl.ucn.fondef.sata.mini.model.Login;
import cl.ucn.fondef.sata.mini.model.RegistroUsuarios;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.utilities.ObjetoSesion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The MiniRest Controller.
 *
 * @author Diego Urrutia-Astorga.
 */
@RestController
@Slf4j
public class WebController {
    // este servidor se conecta con nuxt
    @Autowired
    CoreClientGrpcImpl coreClientGrpcImpl;

    @Autowired
    CoreDao coreDao;

    @Autowired
    private JwtUtil jwtUtil;
/*
    // parte encender led por grpc
    @RequestMapping(value = "api/leddeldestino", method = RequestMethod.GET)
    public String handleLedRequest() {
        return coreClientGrpcImpl.sendReqWebToCore("encenderLed");
    }*/

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String handleLedRequest(@RequestBody Login login) {
        // si las credenciales son correctas
        if (coreDao.iniciarSesion(login)) {
            return jwtUtil.create(login.getCorreo(), "Operador");
        }
        return "credenciales incorrectas";
    }

    @RequestMapping(value = "api/usuario", method = RequestMethod.GET)
    public List<RegistroUsuarios> getAdmins() {
        return coreDao.obtenerAdmins();
    }

}
