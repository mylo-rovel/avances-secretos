package com.RESTbackend.webServer.grpchandlers;

import com.grpcLEDservice.grpc.LedManipulationServiceGrpc;
import com.grpcLEDservice.grpc.TextMessage;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class grpcledcaller {
    private String grpcServerAddress = "localhost";
    private String channelTarget = String.format("dns:///%s:9090",this.grpcServerAddress);

    public String sendComandoLed (String comando) {
        // crear el canal de comuncación
        ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTarget).usePlaintext().build();

        // crear un stub (cliente) asociando el canal de comunicación
        LedManipulationServiceGrpc.LedManipulationServiceBlockingStub stub = LedManipulationServiceGrpc
                .newBlockingStub(channel);
        // crear el objeto que será enviado al server gRPC
        System.out.println("paso 4");
        var requestObject = TextMessage.newBuilder().setMessage("encenderLed").build();
        // enviar la petición gRPC
        TextMessage serverResponse = stub.startLedPerformance(requestObject);

        channel.shutdown();
        return serverResponse.getMessage();
    }
}