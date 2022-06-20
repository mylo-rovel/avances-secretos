package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.WebCoreCommuServiceGrpc;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.Getter;
import org.springframework.stereotype.Service;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;


//import com.google.protobuf.util.JsonFormat;

import java.util.List;

/*@Service*/
// CLIENTE gRPC "Backend Application"
// Esta "clase" se usa para ENVIAR peticiones desde "Backend Application" hasta el "Central Core"
// Usaremos esta clase en el package "web". Especificamente en "WebController"
// En realidad, esta clase es heredada por las otras de este package
public class WebCoreClientGrpcBase {
    // objeto que nos permitirá transformar protobufs a JSON
    protected final Gson gson = new Gson();
    
    // PROCESO DE ENVIO DE PETICION GRPC
    // 1ro: Definir la direccion del servidro RPC
    private final String channelTargetWebToCore = "localhost:9090";
    // 1.5ro: Crear el canal de comuncacion que apunte a la direccion del servidor RPC
    private final ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTargetWebToCore).usePlaintext().build();
    // 2do: Crear un stub (o cliente rpc) asociado al canal de comunicacion y al servicio gRPC

    protected final WebCoreCommuServiceGrpc.WebCoreCommuServiceBlockingStub stub = WebCoreCommuServiceGrpc.newBlockingStub(this.channel);

}