package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.coredao.CoreDao;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;


import java.util.ArrayList;
import java.util.List;

@GrpcService
// SERVIDOR gRPC "Central Core"
// Esta clase se usa para RECIBIR y RESPONDER peticiones desde el "Backend Appplication"
// Asimismo, acá también se usa "CoreRaspiClientGrpcImpl" para hablar con el RASPBERRY PI
public class WebCoreServiceGrpcImpl extends WebCoreCommuServiceGrpc.WebCoreCommuServiceImplBase {

    @Autowired
    private CoreDao coreDao;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public void authenticate(CredencialesEntityReq reqCredenciales, StreamObserver<SesionEntityReply> responseObserver) {
        // Llamar a coreDao para hacer el proceso relacionado a la base de datos
        boolean credencialesCorrectas = coreDao.credencialesSonCorrectas(reqCredenciales);
        Usuario usuarioLogeado = coreDao.getUsuarioPorCorreo(reqCredenciales.getEmail());

        // Por si se registró en la tabla 'login' pero no en 'usuario'
        String jwtUsuario = (credencialesCorrectas && (usuarioLogeado != null )) ?
                jwtUtil.create(usuarioLogeado.getRut(), usuarioLogeado.getRol().name())
                : "";

        // PROCESO DE ENVÍO DE RESPUESTA GRPC
        // 1ro: Construir el objeto que almacenará la información a devolver al cliente
        SesionEntityReply grpcResponse = SesionEntityReply.newBuilder()
                .setSesionIniciada(credencialesCorrectas)
                .setJsonWebToken(jwtUsuario)
                .build();

        // 2do: Enviar el objeto construido al cliente
        responseObserver.onNext(grpcResponse);

        // 3ro: Terminar el proceso
        responseObserver.onCompleted();
    }
    @Override
    public void addUsuario(UsuarioEntityReq reqUsuarioNuevo, StreamObserver<MensajeReply> responseObserver){

        String mensajeRespuesta = coreDao.addUsuario(reqUsuarioNuevo);

        // PROCESO DE ENVÍO DE RESPUESTA GRPC
        // 1ro: Construir el objeto que almacenará la información a devolver al cliente
        MensajeReply grpcResponse = MensajeReply.newBuilder()
                .setMensajeTexto(mensajeRespuesta)
                .build();

        // 2do: Enviar el objeto construido al cliente
        responseObserver.onNext(grpcResponse);

        // 3ro: Terminar el proceso
        responseObserver.onCompleted();
    }
    @Override
    public void addEquipo(EquipoEntityReq equipoEntityReq, StreamObserver<MensajeReply> responseObserver){
        // GUARDAR EN LA DB
        String mensajeResultado = coreDao.addEquipo(equipoEntityReq);

        // GRPC parte
        MensajeReply grpcResponse = MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    // TODO: HACER EL INNER JOIN PARA OBTENER LAS "Secuencia" o eventos DE LA SIMULACION
    public void getSimulacion(IdElementoReq idElemento, StreamObserver<SimulacionReply> responseObserver){
        // Obtener la simulacion desde la base de datos
        Simulacion simulacionGuardada = coreDao.getSimulacion(idElemento);

        IdElementoReq idEquipo = IdElementoReq.newBuilder().setId(simulacionGuardada.getId()).build();
        Equipo equipoAsociado = coreDao.getEquipo(idEquipo);

        SimulacionReply simulacionRetornar = SimulacionReply.newBuilder()
                .setId(simulacionGuardada.getId())
                .setNombre(simulacionGuardada.getNombre())
                .setDescripcion(simulacionGuardada.getDescripcion())
                .setNombreEquipo(equipoAsociado.getNombre())
                .setDescripcionEquipo(equipoAsociado.getDescripcion())
                .setFechaEjecucion(simulacionGuardada.getFechaEjecucion())
//                .setSecuencia()
                .setAguaCaida(simulacionGuardada.getAguaCaida())
                .build();

        responseObserver.onNext(simulacionRetornar);
        responseObserver.onCompleted();
    }

    @Override
    // TODO: OPTIMIZAR Y HACER UNA QUERY HACIENDO UN INNER JOIN PARA...
    // TODO: ... EVITAR HACER MULTIPLES QUERIES A LA BASE DE DATOS
    public void getSimulaciones(EmptyReq emptyReq, StreamObserver<SimulacionesReply> responseObserver){
        List<Simulacion> listaSimuGuardadas = coreDao.getSimulaciones();
        SimulacionesReply.Builder listaRetornar = SimulacionesReply.newBuilder();

        for (Simulacion simulacion : listaSimuGuardadas) {
            IdElementoReq idEquipo = IdElementoReq.newBuilder().setId(simulacion.getIdEquipo()).build();
            Equipo equipoAsociado = coreDao.getEquipo(idEquipo);

            SimulacionAcotada simuRetornar = SimulacionAcotada.newBuilder()
                    .setNombre(simulacion.getNombre())
                    .setNombreEquipo(equipoAsociado.getNombre())
                    .setFechaEjecucion(equipoAsociado.getDescripcion())
                    .setAguaCaida(simulacion.getAguaCaida())
                    .build();

            listaRetornar.addSimulacionAcotada(simuRetornar);
        }
        responseObserver.onNext(listaRetornar.build());
        responseObserver.onCompleted();
    }
}