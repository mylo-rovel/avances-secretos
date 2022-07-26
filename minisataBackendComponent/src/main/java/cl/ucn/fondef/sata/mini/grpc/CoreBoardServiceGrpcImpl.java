package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcSimulacion;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The type Core board service grpc.
 */
@Slf4j
@GrpcService
public class CoreBoardServiceGrpcImpl extends CoreBoardCommuServiceGrpc.CoreBoardCommuServiceImplBase{
    @Autowired
    private WebCoreServiceGrpcSimulacion webCoreServiceGrpcSimulacion;

    // aca recibimos peticiones de la raspi
    // AGREGAR FUNCION QUE RECIBA LA PETICION "rpc sendMensajeEncendido (SaludoBoardReq)  returns (SaludoBoardReply) {}"
    // PARA GUARDARLA EN EL HASHMAP DE LOS EQUIPOS ENCENDIDO "HashMap<String, InformacionBoard> ejecucionesEquipo"
    // LUEGO ENVIAR UN OBJETO VACIO TEMPORAL: Domain.SimulacionReply.newBuilder().build();

    @Override
    public void sendLecturasSensores(Domain.LecturaSensoresReq lecturaSensoresReq, StreamObserver<Domain.EmptyReq> responseObserver){
        log.info("\n\nCaudal recibido desde el equipo: " + lecturaSensoresReq.getNombreEquipo());
        Domain.EmptyReq grpcResponse = webCoreServiceGrpcSimulacion.sendLecturasSensores(lecturaSensoresReq, responseObserver);
        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void sendMensajeEncendido(Domain.SaludoBoardReq saludoBoardReq, StreamObserver<Domain.SaludoBoardReply> responseObserver){
        log.info("\n\nSaludo recibido desde el equipo: " + saludoBoardReq.getNombreEquipo());
        Domain.SaludoBoardReply grpcResponse = webCoreServiceGrpcSimulacion.sendMensajeEncendido(saludoBoardReq, responseObserver);
        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void sendMensajeTerminoEjecucion(Domain.AvisoTerminoEjecucionReq avisoTerminoEjecucionReq,
                                            StreamObserver<Domain.EmptyReq> responseObserver){
        log.info("\n\nTermino de ejecución por parte del equipo: " + avisoTerminoEjecucionReq.getNombreEquipo());
        Domain.EmptyReq grpcResponse = webCoreServiceGrpcSimulacion.sendMensajeTerminoEjecucion(avisoTerminoEjecucionReq, responseObserver);
        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}
