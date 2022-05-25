package com.backendCore.gRPCcore.grpcCoreService;

import com.grpcLEDservice.grpc.LedManipulationServiceGrpc;
import com.grpcLEDservice.grpc.TextMessage;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class grpcCoreRaspiClient {

    public String sendComandoLedToRaspi (String comando) {
        String grpcServerAddress = "localhost";
        String channelTarget = String.format("dns:///%s:50051",grpcServerAddress);
        // crear el canal de comuncación
        ManagedChannel channel = NettyChannelBuilder.forTarget(channelTarget).usePlaintext().build();

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
