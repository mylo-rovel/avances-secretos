package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpcobjects.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;


//import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private final Gson gson = new Gson();

    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public String authenticate (GrpcCredencialesEntityReq grpcCredencialesEntityReq) {
        // 3ro: Crear el objeto que será enviado al server RPC "Central Core"
        // Acá le metemos los datos recibidos desde el cliente
        CredencialesEntityReq requestObject = CredencialesEntityReq.newBuilder()
                .setEmail( grpcCredencialesEntityReq.getEmail() )
                .setPassword( grpcCredencialesEntityReq.getPassword() )
                .build();

        // 4to: Enviar la petición RPC y guardar la respuesta
        SesionEntityReply serverResponse = this.stub.authenticate(requestObject);
        return this.gson.toJson(serverResponse);


        // 5to: Crear un objeto que finalmente se transformará en JSON para enviar al browser
        // Esto es importante dado que enviar directamente "serverResponse" arroja errores
/*        GrpcSesionEntityReply sesion = new GrpcSesionEntityReply();
        sesion.setSesionIniciada( serverResponse.getSesionIniciada() );
        sesion.setJsonWebToken( serverResponse.getJsonWebToken() );*/
    }

    public String addUsuario (GrpcUsuarioEntityReq grpcUsuarioEntityReq){

        // CREAR EL OBJETO RPC A ENVIAR
        UsuarioEntity usuarioNuevo = UsuarioEntity.newBuilder()
                .setNombre(grpcUsuarioEntityReq.getUsuario().getNombre())
                .setApellido(grpcUsuarioEntityReq.getUsuario().getApellido())
                .setRut(grpcUsuarioEntityReq.getUsuario().getRut())
                .setEmail(grpcUsuarioEntityReq.getUsuario().getEmail())
                .setPassword(grpcUsuarioEntityReq.getUsuario().getPassword())
                .setRol(grpcUsuarioEntityReq.getUsuario().getRol())
                .setEstado(grpcUsuarioEntityReq.getUsuario().getEstado())
                .build();

        UsuarioEntityReq requestObject = UsuarioEntityReq.newBuilder()
                .setUsuario(usuarioNuevo)
                .setRutAdministrador(grpcUsuarioEntityReq.getRutAdministrador())
                .build();

        // ENVIAR EL OBJETO RPC Y GUARDAR LA RESPUESTA
        MensajeReply serverResponse = this.stub.addUsuario(requestObject);

        return this.gson.toJson(serverResponse);
    }

    public String getUsuario (String rutUsuario){
        RutEntityReq rutUsuarioReturn = RutEntityReq.newBuilder().setRut(rutUsuario).build();
        UsuarioEntityReply serverResponse = this.stub.getUsuario(rutUsuarioReturn);
        return this.gson.toJson(serverResponse);
    }

    public String getUsuarios(){
        EmptyReq emptyReq = EmptyReq.newBuilder().build();
        UsuariosEntityReply serverResponse = this.stub.getUsuarios(emptyReq);
        return this.gson.toJson(serverResponse);
    }

    public String setUsuario(GrpcUsuarioEntityReq grpcUsuarioEntityReq){
        UsuarioEntity usuarioEntity = UsuarioEntity.newBuilder()
                .setRut(grpcUsuarioEntityReq.getUsuario().getRut())
                .setNombre(grpcUsuarioEntityReq.getUsuario().getNombre())
                .setApellido(grpcUsuarioEntityReq.getUsuario().getApellido())
                .setEmail(grpcUsuarioEntityReq.getUsuario().getEmail())
                .setPassword(grpcUsuarioEntityReq.getUsuario().getPassword())
                .setEstado(grpcUsuarioEntityReq.getUsuario().getEstado())
                .setRol(grpcUsuarioEntityReq.getUsuario().getRol())
                .build();

        UsuarioEntityReq usuarioEditar = UsuarioEntityReq.newBuilder()
                .setUsuario(usuarioEntity)
                .setRutAdministrador(grpcUsuarioEntityReq.getRutAdministrador())
                .build();

        MensajeReply serverResponse = this.stub.setUsuario(usuarioEditar);
        return this.gson.toJson(serverResponse);
    }

    public String addEquipo (GrpcEquipoEntityReq equipoNuevo){

        EquipoEntity.Builder equipoRecibidoEnviar = EquipoEntity.newBuilder()
                .setNombre(equipoNuevo.getEquipo().getNombre())
                .setUrlRepositorio(equipoNuevo.getEquipo().getUrlRepositorio())
                .setDescripcion(equipoNuevo.getEquipo().getDescripcion())
                .setRutConfigurador(equipoNuevo.getEquipo().getRutConfigurador());

        // ITERAMOS SOBRE EL OBJETO RECIBIDO PARA CREAR OBJETOS...
        // ... "repeated ComponenteFisico componente_fisico = 5;" A ENVIAR
        List<GrpcComponenteFisico> listaComponentesNuevos = equipoNuevo.getEquipo().getListaComponentesFisicos();
        for (GrpcComponenteFisico componenteFuenteInformacion : listaComponentesNuevos) {
            ComponenteFisico componenteEnviar = ComponenteFisico.newBuilder()
                    .setNombre(componenteFuenteInformacion.getNombre())
                    .setDescripcion(componenteFuenteInformacion.getDescripcion())
                    .setPin(componenteFuenteInformacion.getPin())
                    .setUrl(componenteFuenteInformacion.getUrl())
                    .setEstado(componenteFuenteInformacion.getEstado())
                    .setConexion(componenteFuenteInformacion.getConexion())
                    .setTipo(componenteFuenteInformacion.getTipo())
                    .build();
            equipoRecibidoEnviar.addComponenteFisico(componenteEnviar);
        }

        EquipoEntityReq equipoEntityReq = EquipoEntityReq.newBuilder()
                .setEquipo(equipoRecibidoEnviar.build())
                .build();

        /*MensajeReply serverResponse = this.stub.addEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);
        return this.gson.toJson(equipoEntityReq);*/
        MensajeReply serverResponse = this.stub.addEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);
    }

    public String setEquipo(GrpcEquipoEntityReq equipoEditar){
        EquipoEntity.Builder equipoRecibidoEnviar = EquipoEntity.newBuilder()
                .setNombre(equipoEditar.getEquipo().getNombre())
                .setDescripcion(equipoEditar.getEquipo().getDescripcion())
                .setUrlRepositorio(equipoEditar.getEquipo().getUrlRepositorio())
                .setRutConfigurador(equipoEditar.getEquipo().getRutConfigurador());

        // ITERAMOS SOBRE EL OBJETO RECIBIDO PARA CREAR OBJETOS...
        // ... "repeated ComponenteFisico componente_fisico = 5;" A ENVIAR
        List<GrpcComponenteFisico> listaComponentesNuevos = equipoEditar.getEquipo().getListaComponentesFisicos();
        for (GrpcComponenteFisico componenteFuenteInformacion : listaComponentesNuevos) {
            ComponenteFisico componenteEnviar = ComponenteFisico.newBuilder()
                    .setNombre(componenteFuenteInformacion.getNombre())
                    .setDescripcion(componenteFuenteInformacion.getDescripcion())
                    .setPin(componenteFuenteInformacion.getPin())
                    .setUrl(componenteFuenteInformacion.getUrl())
                    .setEstado(componenteFuenteInformacion.getEstado())
                    .setConexion(componenteFuenteInformacion.getConexion())
                    .setTipo(componenteFuenteInformacion.getTipo())
                    .build();
            equipoRecibidoEnviar.addComponenteFisico(componenteEnviar);
        }

        EquipoEntityReq equipoEntityReq = EquipoEntityReq.newBuilder()
                .setEquipo(equipoRecibidoEnviar.build())
                .build();

        /*MensajeReply serverResponse = this.stub.addEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);*/

        MensajeReply serverResponse = this.stub.setEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);
    }

    public String getEquipo(long idEquipo){
        IdElementoReq idElementoReturn = IdElementoReq.newBuilder().setId(idEquipo).build();
        EquipoEntityReply serverResponse = this.stub.getEquipo(idElementoReturn);
        return this.gson.toJson(serverResponse);
    }

    public String getEquipos(){
        EmptyReq emptyReq = EmptyReq.newBuilder().build();
        EquiposEntityReply serverResponse = this.stub.getEquipos(emptyReq);
        return this.gson.toJson(serverResponse);
    }

    public String getSimulacion(long idSimulacion){
        IdElementoReq idElementoReturn = IdElementoReq.newBuilder().setId(idSimulacion).build();
        SimulacionReply serverResponse = this.stub.getSimulacion(idElementoReturn);
        return this.gson.toJson(serverResponse);
    }


    public String getSimulaciones(){
        EmptyReq emptyReq = EmptyReq.newBuilder().build();
        SimulacionesReply serverResponse = this.stub.getSimulaciones(emptyReq);
        return this.gson.toJson(serverResponse);
    }
}
