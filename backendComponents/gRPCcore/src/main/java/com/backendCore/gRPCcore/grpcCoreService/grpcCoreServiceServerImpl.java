package com.backendCore.gRPCcore.grpcCoreService;

import com.backendCore.gRPCcore.models.grpcTextMessage;
import com.grpcLEDservice.grpc.LedManipulationServiceGrpc;
import com.grpcLEDservice.grpc.TextMessage;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService

public class grpcCoreServiceServerImpl extends LedManipulationServiceGrpc.LedManipulationServiceImplBase {
    @Override
    public void startLedPerformance(TextMessage request, StreamObserver<TextMessage> responseObserver) {
        grpcTextMessage mensajeRespuesta = new grpcTextMessage();

        if (request.getMessage().equals("encenderLed")) {
            mensajeRespuesta.setMessage("EXITO. ENVIANDO ORDEN AL RASPI");
        }else{
            mensajeRespuesta.setMessage("COMANDO INVALIDO");
        }

        TextMessage grpcObjetoRetorno = TextMessage.newBuilder()
                .setMessage(mensajeRespuesta.getMessage())
                .build();
        System.out.println(mensajeRespuesta.getMessage());
        responseObserver.onNext(grpcObjetoRetorno);
        responseObserver.onCompleted();
    }
}

