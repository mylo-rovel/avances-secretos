/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpc;


import cl.ucn.fondef.sata.mini.model.GrpcTextMessage;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class CoreServiceGrpcImpl extends LedManipulationServiceGrpc.LedManipulationServiceImplBase {

    @Autowired
    CoreClientGrpcImpl coreClientGrpcImpl;

    @Override
    public void startLedPerformance(TextMessage request, StreamObserver<TextMessage> responseObserver) {
        // RECEPCION DE PETICIONES => ROL ACTUAL: SERVER
        // Creando objeto para guardar el mensaje de respuesta del server raspi
        GrpcTextMessage mensajeRespuesta = new GrpcTextMessage();
        System.out.println("Peticion del 1er componente backend recibida: "+ request.getMessage());

        // ENVIO DE PETICIONES => ROL ACTUAL: CLIENTE
        if (request.getMessage().equals("encenderLed")) {
            String respuestaPython = coreClientGrpcImpl.sendReqCoreToRaspi("encenderLed");
            mensajeRespuesta.setMessage(respuestaPython);
        }else{
            mensajeRespuesta.setMessage("COMANDO INVALIDO");
        }
        System.out.println(mensajeRespuesta.getMessage());

        // Creando el objeto con la respuesta del servidor para retornar al cliente
        TextMessage grpcObjetoRetorno = TextMessage.newBuilder()
                .setMessage( mensajeRespuesta.getMessage() )
                .build();

        // Enviando el objeto al cliente
        responseObserver.onNext(grpcObjetoRetorno);

        // Terminando el proceso
        responseObserver.onCompleted();
    }
}

