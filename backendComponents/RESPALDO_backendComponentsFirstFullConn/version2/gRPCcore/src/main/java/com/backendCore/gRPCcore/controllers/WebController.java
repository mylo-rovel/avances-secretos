package com.backendCore.gRPCcore.controllers;

import com.backendCore.gRPCcore.grpcCoreService.CoreClientGrpcImpl;
import com.grpcLEDservice.grpc.LedManipulationServiceGrpc;
import com.grpcLEDservice.grpc.TextMessage;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class WebController {

    @Autowired
    CoreClientGrpcImpl coreClientGrpcImpl;

    @RequestMapping(value = "api/leddeldestino", method = RequestMethod.GET)
    public String handleLedRequest() {
        System.out.println("Peticion del navegador recibida. Enviando orden al CORE");
        try {
            String serverResponse = coreClientGrpcImpl.sendReqWebToCore("encenderLed");
            System.out.println("respuesta server: " + serverResponse);
            return serverResponse;
        }
        catch (Exception ex) {
            System.out.println(ex);
            return "ERROR";
        }
    }
/*    @GrpcClient("gRPCcore")
    private LedManipulationServiceGrpc.LedManipulationServiceBlockingStub ledManipulationServiceGrpc;

    @RequestMapping(value = "api/leddeldestino", method = RequestMethod.GET)
    public String handleLedRequest() {
        // The Request. tM stands for textMessage
        TextMessage tM = TextMessage.newBuilder()
                .setMessage("encenderLed")
                .build();

        return this.ledManipulationServiceGrpc.startLedPerformance(tM).getMessage();
    }*/

}
