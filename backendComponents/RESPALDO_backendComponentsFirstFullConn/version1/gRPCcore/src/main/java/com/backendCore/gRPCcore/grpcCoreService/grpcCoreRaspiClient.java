package com.backendCore.gRPCcore.grpcCoreService;

import com.grpcLEDservice.grpc.LedManipulationServiceGrpc;
import com.grpcLEDservice.grpc.TextMessage;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class grpcCoreRaspiClient {
    private String channelTarget = "localhost:50051";

    public String sendComandoLedToRaspi (String comando) {
        // crear el canal de comuncación
        // "192.168.1.86:50051" => argumento de .forTarget()
        ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTarget).usePlaintext().build();
        System.out.println("yendo a " + this.channelTarget);
        // crear un stub (cliente) asociando el canal de comunicación
        LedManipulationServiceGrpc.LedManipulationServiceBlockingStub stub = LedManipulationServiceGrpc
                .newBlockingStub(channel);
        // crear el objeto que será enviado al server gRPC
        TextMessage requestObject = TextMessage.newBuilder().setMessage(comando).build();
        // enviar la petición gRPC
        TextMessage serverResponse = stub.startLedPerformance(requestObject);

        //channel.shutdown();
        return serverResponse.getMessage();
    }

}
