package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpc.CoreBoardCommuServiceGrpc;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;

/**
 * The type Core board client grpc base.
 */
@Slf4j
public class CoreBoardClientGrpcBase {
    // ESTA CLASE ES INSTANCIADA POR CADA EQUIPO QUE SE REPORTE COMO DISPONIBLE
    // ESOS OBJETOS SON GUARDADOS DENTRO DE LA CLASE "InformacionBoard" Y ESOS EN UN
    // HASHMAP DENTRO DE LA CLASE "WebCoreServiceGrpcSimulacion"

    /**
     * The Gson.
     */
// objeto que nos permitirá transformar protobufs a JSON
    protected final Gson gson = new Gson();

    protected ManagedChannel channel;

    /**
     * The Stub.
     */
    protected CoreBoardCommuServiceGrpc.CoreBoardCommuServiceBlockingStub stub;
    /**
     * The Async stub.
     */
    protected CoreBoardCommuServiceGrpc.CoreBoardCommuServiceStub asyncStub;

    /**
     * Instantiates a new Core board client grpc base.
     *
     * @param direccion the direccion
     */
    public CoreBoardClientGrpcBase (String direccion) {
        // PROCESO DE ENVIO DE PETICION GRPC
        // 1ro: Definir la direccion del servidro RPC
        this.channel = NettyChannelBuilder.forTarget(direccion).usePlaintext().build();
        this.stub = CoreBoardCommuServiceGrpc.newBlockingStub(channel);
        this.asyncStub = CoreBoardCommuServiceGrpc.newStub(channel);
    }

    public void shutdownCoreBoardClient() {
        ManagedChannel channelDown = this.channel.shutdownNow();
        System.out.println("CoreBoardClientGrpcBase channelDown = " + channelDown);
    }

    /**
     * Start simulacion domain . mensaje reply.
     *
     * @param simulacionBoardReq the simulacion board req
     * @return the domain . mensaje reply
     */
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