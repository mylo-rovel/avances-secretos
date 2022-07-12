package cl.ucn.fondef.sata.mini.grpc.coreboardclient;

import cl.ucn.fondef.sata.mini.grpc.CoreBoardCommuServiceGrpc;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

public class CoreBoardClientGrpcBase {
    // objeto que nos permitirá transformar protobufs a JSON
    protected final Gson gson = new Gson();

    protected CoreBoardCommuServiceGrpc.CoreBoardCommuServiceBlockingStub stub;
    protected CoreBoardCommuServiceGrpc.CoreBoardCommuServiceStub asyncStub;

    public CoreBoardClientGrpcBase (String direccion) {
        // PROCESO DE ENVIO DE PETICION GRPC
        // 1ro: Definir la direccion del servidro RPC
        ManagedChannel channel = NettyChannelBuilder.forTarget(direccion).usePlaintext().build();
        this.stub = CoreBoardCommuServiceGrpc.newBlockingStub(channel);
        this.asyncStub = CoreBoardCommuServiceGrpc.newStub(channel);;
    }

    public Domain.MensajeReply startSimulacion (Domain.SimulacionBoardReq simulacionBoardReq){
        return stub.startSimulacion(simulacionBoardReq);
    }
    
    public Domain.SaludoBoardReply sendMensajeEncendido(Domain.SaludoBoardReq saludoBoardReq){
        return stub.sendMensajeEncendido(saludoBoardReq);
    }
}