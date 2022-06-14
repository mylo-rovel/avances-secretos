package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.coredao.CoreDao;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcCompFisico;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipo;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuario;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
// SERVIDOR gRPC "Central Core"
// Esta clase se usa para RECIBIR y RESPONDER peticiones desde el "Backend Appplication"
// Asimismo, acá también se usa "CoreRaspiClientGrpcImpl" para hablar con el RASPBERRY PI
public class WebCoreServiceGrpcImpl extends WebCoreCommuServiceGrpc.WebCoreCommuServiceImplBase {
    @Autowired
    private CoreRaspiClientGrpcImpl coreRaspiClientGrpc;

    @Autowired
    private CoreDao coreDao;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public void loginUsuario(Credenciales reqCredenciales, StreamObserver<ObjetoSesion> responseObserver) {
        // Llamar a coreDao para hacer el proceso relacionado a la base de datos
        boolean credencialesCorrectas = coreDao.credencialesSonCorrectas(reqCredenciales.getCorreo(), reqCredenciales.getContrasena());
        Usuario usuarioLogeado = coreDao.obtenerUsuarioPorCorreo(reqCredenciales.getCorreo());

        // Por si se registró en la tabla 'login' pero no en 'usuario'
        String jwtUsuario = (credencialesCorrectas && (usuarioLogeado != null )) ?
                jwtUtil.create(usuarioLogeado.getRut(), usuarioLogeado.getRol())
                : "";

        // PROCESO DE ENVÍO DE RESPUESTA GRPC
        // 1ro: Construir el objeto que almacenará la información a devolver al cliente
        ObjetoSesion grpcResponse = ObjetoSesion.newBuilder()
                .setSesionIniciada(credencialesCorrectas)
                .setJsonWebToken(jwtUsuario)
                .build();

        // 2do: Enviar el objeto construido al cliente
        responseObserver.onNext(grpcResponse);

        // 3ro: Terminar el proceso
        responseObserver.onCompleted();
    }

    public void agregarUsuario(UsuarioNuevo reqUsuarioNuevo, StreamObserver<MensajeResultadoOperacion> responseObserver){
        GrpcUsuario datosUsuario = new GrpcUsuario();

        // Creando un objeto que puede ser facilmente manipulado al DAO
        datosUsuario.setNombre(reqUsuarioNuevo.getUsuarioNuevo().getNombre());
        datosUsuario.setApellido(reqUsuarioNuevo.getUsuarioNuevo().getApellido());
        datosUsuario.setRut(reqUsuarioNuevo.getUsuarioNuevo().getRut());
        datosUsuario.setCorreo(reqUsuarioNuevo.getUsuarioNuevo().getCorreo());
        datosUsuario.setContrasena(reqUsuarioNuevo.getUsuarioNuevo().getContrasena());
        datosUsuario.setRol(reqUsuarioNuevo.getUsuarioNuevo().getRol());
        datosUsuario.setEstado(reqUsuarioNuevo.getUsuarioNuevo().getEstado());

        String mensaje = coreDao.anadirUsuario(reqUsuarioNuevo.getRutAdministrador(), datosUsuario);

        // PROCESO DE ENVÍO DE RESPUESTA GRPC
        // 1ro: Construir el objeto que almacenará la información a devolver al cliente
        MensajeResultadoOperacion grpcResponse = MensajeResultadoOperacion.newBuilder()
                .setMensajeTexto(mensaje)
                .build();

        // 2do: Enviar el objeto construido al cliente
        responseObserver.onNext(grpcResponse);

        // 3ro: Terminar el proceso
        responseObserver.onCompleted();
    }

    public void crearEquipo(Equipo reqEquipo, StreamObserver<MensajeResultadoOperacion> responseObserver){
        GrpcEquipo equipo = new GrpcEquipo();

        equipo.setNombre(reqEquipo.getNombre());
        equipo.setDescripcion(reqEquipo.getDescripcion());
        equipo.setEnlaceRepo(reqEquipo.getEnlaceRepo());

        //List<GrpcCompFisico> listaVacia = new ArrayList<GrpcCompFisico>();
        GrpcCompFisico[] listaVacia = new GrpcCompFisico[0];
        equipo.setListaValvulas(listaVacia);
        equipo.setListaSensores(listaVacia);
        equipo.setListaCamaras(listaVacia);
        equipo.setRutConfigurador(reqEquipo.getRutConfigurador());

        String mensaje = coreDao.anadirEquipo(reqEquipo.getRutConfigurador(),equipo);

        // GRPC parte
        MensajeResultadoOperacion grpcResponse = MensajeResultadoOperacion.newBuilder()
                .setMensajeTexto(mensaje)
                .build();

        responseObserver.onNext(grpcResponse);

        responseObserver.onCompleted();
    }
}
