/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpc;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class CoreClientGrpcImpl {
    private String channelTargetWebToCore = "localhost:9090";
    private String channelTargetCoreToRaspi = "localhost:50051";

    public String sendReqCoreToRaspi (String comando) {
        // Creando el canal de comuncación
        ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTargetCoreToRaspi).usePlaintext().build();
        System.out.println("yendo a " + this.channelTargetCoreToRaspi);

        // Creando un stub (cliente) asociando el canal de comunicación
        LedManipulationServiceGrpc.LedManipulationServiceBlockingStub stub = LedManipulationServiceGrpc
                .newBlockingStub(channel);

        // Creando el objeto que será enviado al server gRPC
        TextMessage requestObject = TextMessage.newBuilder().setMessage(comando).build();

        // Enviando la petición gRPC
        TextMessage serverResponse = stub.startLedPerformance(requestObject);
        
        return serverResponse.getMessage();
    }

    // las funciones son casi iguales pero no tiene importancia para este ejercicio
    public String sendReqWebToCore (String comando) {
        // Creando el canal de comuncación
        ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTargetWebToCore).usePlaintext().build();
        System.out.println("yendo a " + this.channelTargetWebToCore);

        // Creando un stub (cliente) asociando el canal de comunicación
        LedManipulationServiceGrpc.LedManipulationServiceBlockingStub stub = LedManipulationServiceGrpc
                .newBlockingStub(channel);

        // Creando el objeto que será enviado al server gRPC
        TextMessage requestObject = TextMessage.newBuilder().setMessage(comando).build();

        // Enviando la petición gRPC
        TextMessage serverResponse = stub.startLedPerformance(requestObject);

        //channel.shutdown();
        return serverResponse.getMessage();
    }


}