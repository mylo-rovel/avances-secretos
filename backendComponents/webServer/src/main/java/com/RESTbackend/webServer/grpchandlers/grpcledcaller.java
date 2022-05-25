package com.RESTbackend.webServer.grpchandlers;

import com.grpcLEDservice.grpc.LedManipulationServiceGrpc;
import com.grpcLEDservice.grpc.TextMessage;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class grpcledcaller {
    private String grpcServerAddress = "localhost";
    private String channelTarget = String.format("dns:///%s:9090",grpcServerAddress);
    // crear el canal de comuncación
    private ManagedChannel channel = NettyChannelBuilder.forTarget(channelTarget).usePlaintext().build();

    public String sendComandoLed (String comando) {
        // crear un stub (cliente) asociando el canal de comunicación
        LedManipulationServiceGrpc.LedManipulationServiceBlockingStub stub = LedManipulationServiceGrpc
                .newBlockingStub(channel);
        // crear el objeto que será enviado al server gRPC
        TextMessage requestObject = TextMessage.newBuilder().setMessage(comando).build();
        // enviar la petición gRPC
        TextMessage serverResponse = stub.startLedPerformance(requestObject);

        channel.shutdown();
        return serverResponse.getMessage();
    }
}
