package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcEquipo;
import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcUsuario;
import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcSimulacion;
import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcExtra;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;


/**
 * The type Web core service grpc.
 */
@Slf4j
@GrpcService
// SERVIDOR gRPC "Central Core"
// Esta clase se usa para RECIBIR y RESPONDER peticiones desde el "Backend Appplication"
// Asimismo, acá también se usa "CoreRaspiClientGrpcImpl" para hablar con el RASPBERRY PI
public class WebCoreServiceGrpcImpl extends WebCoreCommuServiceGrpc.WebCoreCommuServiceImplBase {

    @Autowired
    private WebCoreServiceGrpcUsuario webCoreServiceGrpcUsuario;

    @Autowired
    private WebCoreServiceGrpcEquipo webCoreServiceGrpcEquipo;

    @Autowired
    private WebCoreServiceGrpcSimulacion webCoreServiceGrpcSimulacion;

    @Autowired
    private WebCoreServiceGrpcExtra webCoreServiceGrpcExtra;

    @Override
    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public void authenticate(CredencialesEntityReq reqCredenciales, StreamObserver<SesionEntityReply> responseObserver) {
        var grpcResponse = webCoreServiceGrpcUsuario.authenticate(reqCredenciales, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void addUsuario(UsuarioEntityReq reqUsuarioNuevo, StreamObserver<MensajeReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcUsuario.addUsuario(reqUsuarioNuevo, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void updateUsuario(UsuarioEntityReq usuarioEntityReq, StreamObserver<MensajeReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcUsuario.updateUsuario(usuarioEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getUsuario(RutEntityReq rutEntityReq, StreamObserver<UsuarioEntityReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcUsuario.getUsuario(rutEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getUsuarios(EmptyReq emptyReq, StreamObserver<UsuariosEntityReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcUsuario.getUsuarios(emptyReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void addEquipo(EquipoEntityReq equipoEntityReq, StreamObserver<MensajeReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcEquipo.addEquipo(equipoEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void updateEquipo(EquipoEntityReq equipoEntityReq, StreamObserver<MensajeReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcEquipo.updateEquipo(equipoEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getEquipo(IdElementoConRutReq idElementoConRutReq, StreamObserver<EquipoEntityReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcEquipo.getEquipo(idElementoConRutReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getEquipos(RutEntityReq rutEntityReq, StreamObserver<EquiposEntityReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcEquipo.getEquipos(rutEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getValvulasEquipo(IdElementoReq idElemento, StreamObserver<ComponentesEquipoReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcEquipo.getValvulasEquipo(idElemento, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    // TODO: HACER EL INNER JOIN PARA OBTENER LAS "Secuencia" o eventos DE LA SIMULACION
    @Override
    public void getSimulacion(IdElementoConRutReq idElementoConRutReq, StreamObserver<SimulacionReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.getSimulacion(idElementoConRutReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    // TODO: OPTIMIZAR Y HACER UNA QUERY HACIENDO UN INNER JOIN PARA...
    // TODO: ... EVITAR HACER MULTIPLES QUERIES A LA BASE DE DATOS
    @Override
    public void getSimulaciones(RutEntityReq rutEntityReq, StreamObserver<SimulacionesReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.getSimulaciones(rutEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void addSimulacion(SimulacionReq simulacionReq, StreamObserver<MensajeReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.addSimulacion(simulacionReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void startSimulacion(StartSimulacionReq startSimulacionReq, StreamObserver<MensajeReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.startSimulacion(startSimulacionReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getEjecucion(IdElementoConRutReq idElementoConRutReq, StreamObserver<EjecucionReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.getEjecucion(idElementoConRutReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getEjecuciones(RutEntityReq rutEntityReq, StreamObserver<EjecucionesReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.getEjecuciones(rutEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getRegistros(RutEntityReq rutEntityReq, StreamObserver<RegistrosReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcExtra.getRegistros(rutEntityReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    // rpc uploadArchivo(stream ArchivoEntityReq)  returns (MensajeReply){}
    public StreamObserver<ArchivoEntityReq> uploadArchivo(final StreamObserver<MensajeReply> responseObserver) {
        return new StreamObserver<ArchivoEntityReq>() {

            @Override
            // here recibimos los archivos. los guardamos en algun
            public void onNext(ArchivoEntityReq archivoValue) {
                log.info("req recibida");
            }

            @Override
            public void onError(Throwable t) {
                log.warn("Stream cancelado por un error");
            }

            @Override
            public void onCompleted() {
                MensajeReply grpcResponse = MensajeReply.newBuilder()
                        .setMensajeTexto("Stream completado")
                        .build();
                responseObserver.onNext(grpcResponse);
                responseObserver.onCompleted();
            }
        };
    }
}