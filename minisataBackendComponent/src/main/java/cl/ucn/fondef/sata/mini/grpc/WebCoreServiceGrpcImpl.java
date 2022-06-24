package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcEquipo;
import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcUsuario;
import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcSimulacion;
import cl.ucn.fondef.sata.mini.grpc.webcoreservice.WebCoreServiceGrpcExtra;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;


/**
 * The type Web core service grpc.
 */
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
    public void getEquipo(IdElementoReq idElementoReq, StreamObserver<EquipoEntityReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcEquipo.getEquipo(idElementoReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    @Override
    public void getEquipos(EmptyReq emptyReq, StreamObserver<EquiposEntityReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcEquipo.getEquipos(emptyReq, responseObserver);
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
    public void getSimulacion(IdElementoReq idElemento, StreamObserver<SimulacionReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.getSimulacion(idElemento, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }

    // TODO: OPTIMIZAR Y HACER UNA QUERY HACIENDO UN INNER JOIN PARA...
    // TODO: ... EVITAR HACER MULTIPLES QUERIES A LA BASE DE DATOS
    @Override
    public void getSimulaciones(EmptyReq emptyReq, StreamObserver<SimulacionesReply> responseObserver){
        var grpcResponse = webCoreServiceGrpcSimulacion.getSimulaciones(emptyReq, responseObserver);
        responseObserver.onNext(grpcResponse); //Enviar el objeto construido al cliente
        responseObserver.onCompleted(); //Terminar el proceso
    }
}