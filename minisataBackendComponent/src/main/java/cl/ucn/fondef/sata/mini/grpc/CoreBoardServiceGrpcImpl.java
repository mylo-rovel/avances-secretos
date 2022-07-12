package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcSimulacion;
import cl.ucn.fondef.sata.mini.utilities.InformacionEjecucion;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Stream;

@Slf4j
@Service
public class CoreBoardServiceGrpcImpl extends CoreBoardCommuServiceGrpc.CoreBoardCommuServiceImplBase{
    @Autowired
    private WebCoreServiceGrpcSimulacion webCoreServiceGrpcSimulacion;

    // aca recibimos peticiones de la raspi
    // AGREGAR FUNCION QUE RECIBA LA PETICION "rpc sendMensajeEncendido (SaludoBoardReq)  returns (SaludoBoardReply) {}"
    // PARA GUARDARLA EN EL HASHMAP DE LOS EQUIPOS ENCENDIDO "HashMap<String, InformacionEjecucion> ejecucionesEquipo"
    // LUEGO ENVIAR UN OBJETO VACIO TEMPORAL: Domain.SimulacionReply.newBuilder().build();

    public void sendMensajeEncendido(Domain.SaludoBoardReq saludoBoardReq, StreamObserver<Domain.SaludoBoardReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.sendMensajeEncendido(saludoBoardReq, responseObserver);
        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}
