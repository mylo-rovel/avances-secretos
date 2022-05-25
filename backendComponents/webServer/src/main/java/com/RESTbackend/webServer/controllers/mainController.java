package com.RESTbackend.webServer.controllers;

import com.RESTbackend.webServer.grpchandlers.grpcledcaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class mainController {

    @Autowired
    grpcledcaller grpcledcallerObject;

    @RequestMapping(value = "api/leddeldestino", method = RequestMethod.GET)
    public String handleLedRequest() {
        System.out.println("Peticion recibida. Enviando orden");
        try {
            String serverResponse = grpcledcallerObject.sendComandoLed("encenderLed");
            System.out.println("respuesta server: " + serverResponse);
            return serverResponse;
        }
        catch (Exception ex) {
            System.out.println(ex);
            return "ERROR";
        }

        //return "ENVIANDO ORDEN AL BACKEND CORE";
    }
}