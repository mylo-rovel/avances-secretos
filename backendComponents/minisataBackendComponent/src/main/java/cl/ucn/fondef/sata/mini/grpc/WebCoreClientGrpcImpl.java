package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpcobjects.*;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
// CLIENTE gRPC "Backend Application"
// Esta clase se usa para ENVIAR peticiones desde "Backend Application" hasta el "Central Core"
// Usaremos esta clase en el package "web". Específicamente en "WebController"
public class WebCoreClientGrpcImpl {
    private final String channelTargetWebToCore = "localhost:9090";
    // PROCESO DE ENVÍO DE PETICIÓN GRPC
    // 1ro: Crear el canal de comuncación que apunte a la dirección del servidor RPC
    // (en este caso, el servidor que apuntamos es el servidor rpc "Central Core")
    private final ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTargetWebToCore).usePlaintext().build();
    // 2do: Crear un stub (o cliente rpc) asociado al canal de comunicación y al servicio gRPC
    private final WebCoreCommuServiceGrpc.WebCoreCommuServiceBlockingStub stub = WebCoreCommuServiceGrpc.newBlockingStub(this.channel);


    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public GrpcObjetoSesion loginUsuario (GrpcCredenciales grpcCredenciales) {
        // 3ro: Crear el objeto que será enviado al server RPC "Central Core"
        // Acá le metemos los datos recibidos desde el cliente
        Credenciales requestObject = Credenciales.newBuilder()
                .setCorreo( grpcCredenciales.getCorreo() )
                .setContrasena( grpcCredenciales.getContrasena() )
                .build();

        // 4to: Enviar la petición RPC y guardar la respuesta
        ObjetoSesion serverResponse = this.stub.loginUsuario(requestObject);

        // 5to: Crear un objeto que finalmente se transformará en JSON para enviar al browser
        // Esto es importante dado que enviar directamente "serverResponse" arroja errores
        GrpcObjetoSesion objetoSesion = new GrpcObjetoSesion();

        objetoSesion.setSesionIniciada( serverResponse.getSesionIniciada() );
        objetoSesion.setJsonWebToken( serverResponse.getJsonWebToken() );

        return objetoSesion;
    }

    public GrpcMensajeResultadoOperacion agregarUsuario (GrpcUsuarioNuevo grpcUsuarioNuevo){
        Usuario requestObject = Usuario.newBuilder()
                .setRut(grpcUsuarioNuevo.getUsuarioNuevo().getRut())
                .setNombre(grpcUsuarioNuevo.getUsuarioNuevo().getNombre())
                .setApellido(grpcUsuarioNuevo.getUsuarioNuevo().getApellido())
                .setCorreo(grpcUsuarioNuevo.getUsuarioNuevo().getCorreo())
                .setContrasena(grpcUsuarioNuevo.getUsuarioNuevo().getContrasena())
                .setRol(grpcUsuarioNuevo.getUsuarioNuevo().getRol())
                .setEstado(grpcUsuarioNuevo.getUsuarioNuevo().isEstado())
                .build();
        UsuarioNuevo requestObject2 = UsuarioNuevo.newBuilder()
                .setRutAdministrador(grpcUsuarioNuevo.getRutAdministrador())
                .setUsuarioNuevo(requestObject)
                .build();

        MensajeResultadoOperacion serverResponse = this.stub.agregarUsuario(requestObject2);
        GrpcMensajeResultadoOperacion objetoResultadoOperacion = new GrpcMensajeResultadoOperacion();
        objetoResultadoOperacion.setMensajeTexto("Usuario Agregado Exitosamente");
        return objetoResultadoOperacion;
    }

    public GrpcMensajeResultadoOperacion crearSimulador (GrpcSimulador grpcSimulador){
        GrpcCompFisico[] listaVacia = new GrpcCompFisico[0];
        Simulador requestObject = Simulador.newBuilder()
                .setNombre(grpcSimulador.getNombre())
                .setDescripcion(grpcSimulador.getDescripcion())
                .setEnlaceRepo(grpcSimulador.getEnlace_repo())
                .setEstado(grpcSimulador.isEstado())
                //.setListaValvulas(listaVacia)
                //.setListaSensores(listaVacia)
                //.setListaCamaras(listaVacia)
                .setRutConfigurador(grpcSimulador.getRutConfigurador())
                .build();

        MensajeResultadoOperacion serverResponse = this.stub.crearSimulador(requestObject);
        GrpcMensajeResultadoOperacion objetoResultadoOperacion = new GrpcMensajeResultadoOperacion();
        objetoResultadoOperacion.setMensajeTexto("Simulador creado exitosamente");
        return objetoResultadoOperacion;
    }
}
