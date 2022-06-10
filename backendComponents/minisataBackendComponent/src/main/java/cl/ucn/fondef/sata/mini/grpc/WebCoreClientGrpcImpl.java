package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpcobjects.GrpcCredenciales;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcObjetoSesion;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
// CLIENTE gRPC "Backend Application"
// Esta clase se usa para ENVIAR peticiones desde "Backend Application" hasta el "Central Core"
// Usaremos esta clase en el package "web". Específicamente en "WebController"
public class WebCoreClientGrpcImpl {
    private final String channelTargetWebToCore = "localhost:9090";

    // FUNCION COMPARTIDA POR TODAS LAS LLAMADAS DE ESTA CLASE
    // Importante dado que todas los métodos apuntan a la misma dirección de servidor
    private WebCoreCommuServiceGrpc.WebCoreCommuServiceBlockingStub getGrpcStub() {
        // 1ro: Crear el canal de comuncación que apunte a la dirección del servidor RPC
        // (en este caso, el servidor que apuntamos es el servidor rpc "Central Core")
        ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTargetWebToCore).usePlaintext().build();

        // 2do: Crear un stub (o cliente rpc) asociado al canal de comunicación y al servicio gRPC
        return WebCoreCommuServiceGrpc.newBlockingStub(channel);
    }

    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public GrpcObjetoSesion loginUsuario (GrpcCredenciales grpcCredenciales) {
        // PROCESO DE ENVÍO DE PETICIÓN GRPC
        // 1ro: Crear el canal de comuncación que apunte a la dirección del servidor RPC
        // 2do: Crear un stub (o cliente rpc) asociado al canal de comunicación y al servicio gRPC
        WebCoreCommuServiceGrpc.WebCoreCommuServiceBlockingStub stub = getGrpcStub();

        // 3ro: Crear el objeto que será enviado al server RPC "Central Core"
        // Acá le metemos los datos recibidos desde el cliente
        Credenciales requestObject = Credenciales.newBuilder()
                .setCorreo( grpcCredenciales.getCorreo() )
                .setContrasena( grpcCredenciales.getContrasena() )
                .build();

        // 4to: Enviar la petición RPC y guardar la respuesta
        ObjetoSesion serverResponse = stub.loginUsuario(requestObject);

        // 5to: Crear un objeto que finalmente se transformará en JSON para enviar al browser
        // Esto es importante dado que enviar directamente "serverResponse" arroja errores
        GrpcObjetoSesion objetoSesion = new GrpcObjetoSesion();

        objetoSesion.setSesionIniciada( serverResponse.getSesionIniciada() );
        objetoSesion.setJsonWebToken( serverResponse.getJsonWebToken() );

        return objetoSesion;
    }
}
