package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.coredao.CoreDao;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcComponenteFisico;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntity;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuarioEntity;
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
    private CoreRaspiClientGrpcImpl coreRaspiClientGrpc;

    @Autowired
    private CoreDao coreDao;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public void authenticate(CredencialesEntityReq reqCredenciales, StreamObserver<SesionEntityReply> responseObserver) {
        // Llamar a coreDao para hacer el proceso relacionado a la base de datos
        boolean credencialesCorrectas = coreDao.credencialesSonCorrectas(reqCredenciales.getEmail(), reqCredenciales.getPassword());
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

/*    public void addUsuario(UsuarioNuevo reqUsuarioNuevo, StreamObserver<MensajeResultadoOperacion> responseObserver){
        GrpcUsuarioEntity datosUsuario = new GrpcUsuarioEntity();

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

    public void addEquipo(Equipo reqEquipo, StreamObserver<MensajeResultadoOperacion> responseObserver){
        GrpcEquipoEntity equipo = new GrpcEquipoEntity();

        equipo.setNombre(reqEquipo.getNombre());
        equipo.setDescripcion(reqEquipo.getDescripcion());
        equipo.setUrlRepo(reqEquipo.getUrlRepo());

        //List<GrpcCompFisico> listaVacia = new ArrayList<GrpcCompFisico>();
        GrpcComponenteFisico[] listaVacia = new GrpcComponenteFisico[0];
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

    public void getSimulaciones(Empty empty, StreamObserver<ListaSimulacionesAcotada> responseObserver){
        List<Simulacion> lista = coreDao.obtenerSimulaciones();
        Equipo equipoActual;
        List<SimulacionAcotada> listaAcotada = new ArrayList<>();

        for (Simulacion simulacion : lista) {
            equipoActual = coreDao.obtenerEquipoEspecifico(simulacion.getIdEquipo());
            SimulacionAcotada simulacionAcotada = SimulacionAcotada.newBuilder()
                .setIdSimulacion(simulacion.getId())
                .setNombreEquipo(equipoActual.getNombre())
                .setFechaSimulacion(simulacion.getFechaCreacion())
                .setAguaCaida(simulacion.getAguaCaida()).build();
            listaAcotada.add(simulacionAcotada);
        }


        ListaSimulacionesAcotada.Builder listToReturn = ListaSimulacionesAcotada.newBuilder();

        for (SimulacionAcotada simulacion : listaAcotada) {
            listToReturn.addSimulacionAcotada(simulacion);
        }

        System.out.println("listToReturn = " + listToReturn);

        responseObserver.onNext(listToReturn.build());

        responseObserver.onCompleted();
    }

    public void getSimulacion(int idElemento, StreamObserver<SimulacionEspecifica> responseObserver){

        Simulacion simulacion = coreDao.obtenerSimulacionEspecifica(idElemento);

        SimulacionEspecifica.Builder simulacionRetornar = SimulacionEspecifica.newBuilder();

        simulacionRetornar.setIdSimulacion(simulacion.getId());
        simulacionRetornar.setFechaSimulacion(simulacion.getFechaCreacion());

        Equipo equipo = coreDao.obtenerEquipoEspecifico(simulacion.getIdEquipo());
        simulacionRetornar.setNombreEquipo(equipo.getNombre());
        simulacionRetornar.setDescripcionEquipo(equipo.getDescripcion());
        //ver donde meter la lista de secuencias
        simulacionRetornar.setAguaCaida(simulacion.getAguaCaida());

        responseObserver.onNext(simulacionRetornar.build());
        responseObserver.onCompleted();
    }
    */
}
