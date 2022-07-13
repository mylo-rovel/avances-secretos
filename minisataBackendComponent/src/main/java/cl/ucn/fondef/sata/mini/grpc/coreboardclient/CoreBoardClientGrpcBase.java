package cl.ucn.fondef.sata.mini.grpc.coreboardclient;

import cl.ucn.fondef.sata.mini.grpc.CoreBoardCommuServiceGrpc;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;

@Slf4j
public class CoreBoardClientGrpcBase {
    // ESTA CLASE ES INSTANCIADA POR CADA EQUIPO QUE SE REPORTE COMO DISPONIBLE
    // ESOS OBJETOS SON GUARDADOS DENTRO DE LA CLASE "InformacionBoard" Y ESOS EN UN
    // HASHMAP DENTRO DE LA CLASE "WebCoreServiceGrpcSimulacion"

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
        try {
            return stub.startSimulacion(simulacionBoardReq);
        }
        catch (Exception exception) {
            log.warn("Error en la conexion con la placa");
            return Domain.MensajeReply.newBuilder().setMensajeTexto("Error al intentar conectar con la placa").build();
        }
    }
}