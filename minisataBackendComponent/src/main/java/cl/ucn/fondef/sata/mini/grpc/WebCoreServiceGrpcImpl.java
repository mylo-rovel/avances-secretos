package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.coredao.CoreDao;
import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.utilities.StringEnumTransformer;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;


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

    @Autowired
    StringEnumTransformer stringEnumTransformer;

    @Override
    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public void authenticate(CredencialesEntityReq reqCredenciales, StreamObserver<SesionEntityReply> responseObserver) {
        // Llamar a coreDao para hacer el proceso relacionado a la base de datos
        boolean credencialesCorrectas = coreDao.credencialesSonCorrectas(reqCredenciales);
        Usuario usuarioLogeado = coreDao.getUsuarioPorCorreo(reqCredenciales.getEmail());

        // Por si se registró en la tabla 'login' pero no en 'usuario'
        String jwtUsuario = (credencialesCorrectas && (usuarioLogeado != null )) ?
                jwtUtil.create(usuarioLogeado.getRut(), usuarioLogeado.getRol())
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
    public void getUsuario(RutEntityReq rutEntityReq, StreamObserver<UsuarioEntityReply> responseObserver){
        Usuario usuarioGuardado = coreDao.getUsuario(rutEntityReq);

        UsuarioEntityReply.Builder usuarioRetornar = UsuarioEntityReply.newBuilder();
        if (usuarioGuardado != null) {
            UsuarioEntity.Builder usuarioEnte = UsuarioEntity.newBuilder()
                    .setRut(usuarioGuardado.getRut())
                    .setNombre(usuarioGuardado.getNombre())
                    .setApellido(usuarioGuardado.getApellido())
                    .setEmail(usuarioGuardado.getEmail())
                    .setPassword(usuarioGuardado.getPassword())
                    .setRol(stringEnumTransformer.getEnumRolUsuario(usuarioGuardado.getRol()))
                    .setEstado(stringEnumTransformer.getEnumEstadoUsuario(usuarioGuardado.getEstado()));

            usuarioRetornar.setId(usuarioGuardado.getId());
            usuarioRetornar.setUsuario(usuarioEnte.build());
        }
        responseObserver.onNext(usuarioRetornar.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUsuarios(EmptyReq emptyReq, StreamObserver<UsuariosEntityReply> responseObserver){
        List<Usuario> listaUsuariosGuardados = coreDao.getUsuarios();
        UsuariosEntityReply.Builder listaRetornar = UsuariosEntityReply.newBuilder();

        if (listaUsuariosGuardados != null ){
            for(Usuario usuario: listaUsuariosGuardados){
                UsuarioEntity usuarioEnte = UsuarioEntity.newBuilder()
                        .setRut(usuario.getRut())
                        .setNombre(usuario.getNombre())
                        .setApellido(usuario.getApellido())
                        .setEmail(usuario.getEmail())
                        .setPassword(usuario.getPassword())
                        .setRol(stringEnumTransformer.getEnumRolUsuario(usuario.getRol()))
                        .setEstado(stringEnumTransformer.getEnumEstadoUsuario(usuario.getEstado()))
                        .build();
                listaRetornar.addUsuario(usuarioEnte);
            }
        }

        responseObserver.onNext(listaRetornar.build());
        responseObserver.onCompleted();
    }

    @Override
    public void setUsuario(UsuarioEntityReq usuarioEntityReq, StreamObserver<MensajeReply> responseObserver){
        String mensajeResultado = coreDao.setUsuario(usuarioEntityReq);

        MensajeReply grpcResponse = MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void addEquipo(EquipoEntityReq equipoEntityReq, StreamObserver<MensajeReply> responseObserver){
        String mensajeResultado = coreDao.addEquipo(equipoEntityReq);

        MensajeReply grpcResponse = MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void setEquipo(EquipoEntityReq equipoEntityReq, StreamObserver<MensajeReply> responseObserver){
        String mensajeResultado = coreDao.setEquipo(equipoEntityReq);

        MensajeReply grpcResponse = MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getEquipo(IdElementoReq idElementoReq, StreamObserver<EquipoEntityReply> responseObserver){
        Equipo equipoGuardado = coreDao.getEquipo(idElementoReq);

        EquipoEntity.Builder equipoEnte = EquipoEntity.newBuilder()
                .setId(equipoGuardado.getId())
                .setNombre(equipoGuardado.getNombre())
                .setDescripcion(equipoGuardado.getDescripcion())
                .setUrlRepositorio(equipoGuardado.getUrlRepositorio())
                .setEstado(stringEnumTransformer.getEnumEstadoEquipo(equipoGuardado.getEstado()))
                .setRutConfigurador(equipoGuardado.getRutConfigurador());

        List<ComponenteFisico> listaComponenteFisico = coreDao.getComponentesFisicosEquipo(idElementoReq);
        for(ComponenteFisico componenteFisico: listaComponenteFisico){
            Domain.ComponenteFisico componenteDominio = Domain.ComponenteFisico.newBuilder()
                    .setEstado(stringEnumTransformer.getEnumEstadoComponente(componenteFisico.getEstado()))
                    .setConexion(stringEnumTransformer.getEnumConexionComponente(componenteFisico.getConexion()))
                    .setTipo(stringEnumTransformer.getEnumTipoComponente(componenteFisico.getTipo()))
                    .setNombre(componenteFisico.getNombre())
                    .setDescripcion(componenteFisico.getDescripcion())
                    .setPin(componenteFisico.getPin())
                    .setUrl(componenteFisico.getUrl())
                    .build();
            equipoEnte.addComponenteFisico(componenteDominio);
        }

        EquipoEntityReply equipoRetornar = EquipoEntityReply.newBuilder()
                .setEquipo(equipoEnte.build())
                .build();

        responseObserver.onNext(equipoRetornar);
        responseObserver.onCompleted();
    }

    //TODO: TAMBIEN REVISAR AQUI SI EL VALUEOF NO HACE EXPLOTAR EL PROGRAMA
    @Override
    public void getEquipos(EmptyReq emptyReq, StreamObserver<EquiposEntityReply> responseObserver){
        List<Equipo> listaEquiposGuardados = coreDao.getEquipos();
        EquiposEntityReply.Builder listaRetornar = EquiposEntityReply.newBuilder();

        for(Equipo equipo : listaEquiposGuardados){
            EquipoEntityAcotado equipoRetornar = EquipoEntityAcotado.newBuilder()
                    .setId(equipo.getId())
                    .setNombre(equipo.getNombre())
                    .setEstado(stringEnumTransformer.getEnumEstadoEquipo(equipo.getEstado()))
                    .build();

            listaRetornar.addEquipoAcotado(equipoRetornar);
        }

        responseObserver.onNext(listaRetornar.build());
        responseObserver.onCompleted();
    }

    // TODO: HACER EL INNER JOIN PARA OBTENER LAS "Secuencia" o eventos DE LA SIMULACION
    @Override
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

    // TODO: OPTIMIZAR Y HACER UNA QUERY HACIENDO UN INNER JOIN PARA...
    // TODO: ... EVITAR HACER MULTIPLES QUERIES A LA BASE DE DATOS
    @Override
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