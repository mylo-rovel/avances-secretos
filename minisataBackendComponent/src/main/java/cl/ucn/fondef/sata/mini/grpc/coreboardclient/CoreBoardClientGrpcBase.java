package cl.ucn.fondef.sata.mini.grpc.coreboardclient;

import cl.ucn.fondef.sata.mini.grpc.CoreBoardCommuServiceGrpc;
import cl.ucn.fondef.sata.mini.grpc.WebCoreCommuServiceGrpc;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

public class CoreBoardClientGrpcBase {
    // objeto que nos permitirá transformar protobufs a JSON
    protected final Gson gson = new Gson();

    // PROCESO DE ENVIO DE PETICION GRPC
    // 1ro: Definir la direccion del servidro RPC
    private final String channelTargetWebToCore = "localhost:50050";
    // 1.5ro: Crear el canal de comuncacion que apunte a la direccion del servidor RPC
    private final ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTargetWebToCore).usePlaintext().build();
    // 2do: Crear un stub (o cliente rpc) asociado al canal de comunicacion y al servicio gRPC

    /**
     * The Stub.
     */
    protected final CoreBoardCommuServiceGrpc.CoreBoardCommuServiceBlockingStub stub = CoreBoardCommuServiceGrpc.newBlockingStub(this.channel);
    protected final CoreBoardCommuServiceGrpc.CoreBoardCommuServiceStub asyncStub = CoreBoardCommuServiceGrpc.newStub(this.channel);;
}