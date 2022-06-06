package com.backendCore.gRPCcore.grpcCoreService;

import com.backendCore.gRPCcore.models.grpcTextMessage;
import com.grpcLEDservice.grpc.LedManipulationServiceGrpc;
import com.grpcLEDservice.grpc.TextMessage;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class grpcCoreServiceServerImpl extends LedManipulationServiceGrpc.LedManipulationServiceImplBase {

    @Autowired
    grpcCoreRaspiClient grpcCoreRaspiClientObject;

    @Override
    public void startLedPerformance(TextMessage request, StreamObserver<TextMessage> responseObserver) {
        // RECEPCION DE PETICIONES => ROL ACTUAL: SERVER
        grpcTextMessage mensajeRespuesta = new grpcTextMessage();

        System.out.println("Peticion del 1er componente backend recibida: "+ request.getMessage());
        if (request.getMessage().equals("encenderLed")) {
            // ENVIO DE PETICIONES => ROL ACTUAL: CLIENTE
            String respuestaPython = grpcCoreRaspiClientObject.sendComandoLedToRaspi("encenderLed");
            mensajeRespuesta.setMessage(respuestaPython);
        }else{
            mensajeRespuesta.setMessage("COMANDO INVALIDO");
        }

        System.out.println(mensajeRespuesta.getMessage());
        TextMessage grpcObjetoRetorno = TextMessage.newBuilder().setMessage(mensajeRespuesta.getMessage()).build();
        responseObserver.onNext(grpcObjetoRetorno);
        responseObserver.onCompleted();
    }
}

