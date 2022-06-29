package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcCredencialesEntityReq;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuarioEntityReq;
import org.springframework.stereotype.Service;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class WebCoreClientGrpcUsuario extends WebCoreClientGrpcBase {

    private Domain.UsuarioEntityReq getUsuarioGrpcReqObject (GrpcUsuarioEntityReq grpcUsuarioEntityReq, String password) {
        // CREAR EL OBJETO RPC QUE LLEVA LOS DATOS A ENVIAR
        Domain.UsuarioEntity datosUsuario = Domain.UsuarioEntity.newBuilder()
                .setNombre(grpcUsuarioEntityReq.getUsuario().getNombre())
                .setApellido(grpcUsuarioEntityReq.getUsuario().getApellido())
                .setRut(grpcUsuarioEntityReq.getUsuario().getRut())
                .setEmail(grpcUsuarioEntityReq.getUsuario().getEmail())
                .setPassword(password)
                .setRol(grpcUsuarioEntityReq.getUsuario().getRol())
                .setEstado(grpcUsuarioEntityReq.getUsuario().getEstado())
                .build();

        Domain.UsuarioEntityReq requestObject = Domain.UsuarioEntityReq.newBuilder()
                .setUsuario(datosUsuario)
                .setRutAdministrador(grpcUsuarioEntityReq.getRutAdministrador())
                .build();
        return requestObject;
    }

    // ---- FUNCIONES AUX ----------------------------------------------------------------------------------------
    // ---- LLAMADAS RPC  ----------------------------------------------------------------------------------------

    // USAR EL MISMO NOMBRE DE LA FUNCION A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public String authenticate (GrpcCredencialesEntityReq grpcCredencialesEntityReq) {
        // 3ro: Crear el objeto que sera enviado al server RPC "Central Core"
        // Aca le metemos los datos recibidos desde el cliente
        Domain.CredencialesEntityReq requestObject = Domain.CredencialesEntityReq.newBuilder()
                .setEmail( grpcCredencialesEntityReq.getEmail() )
                .setPassword( grpcCredencialesEntityReq.getPassword() )
                .build();

        // 4to: Enviar la peticion RPC y guardar la respuesta
        Domain.SesionEntityReply serverResponse = this.stub.authenticate(requestObject);
        return this.gson.toJson(serverResponse);
    }

    public String addUsuario (GrpcUsuarioEntityReq grpcUsuarioEntityReq){
        // HASHING LA NUEVA CONTRASEÑA
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashPassword= argon2.hash(1, 1024, 1, grpcUsuarioEntityReq.getUsuario().getPassword());

        Domain.UsuarioEntityReq datosUsuario = this.getUsuarioGrpcReqObject(grpcUsuarioEntityReq, hashPassword);
        Domain.MensajeReply serverResponse = this.stub.addUsuario(datosUsuario);
        return this.gson.toJson(serverResponse);
    }

    public String updateUsuario(GrpcUsuarioEntityReq grpcUsuarioEntityReq){
        // HASHING LA NUEVA CONTRASEÑA
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashPassword= argon2.hash(1, 1024, 1, grpcUsuarioEntityReq.getUsuario().getPassword());

        Domain.UsuarioEntityReq datosUsuario = this.getUsuarioGrpcReqObject(grpcUsuarioEntityReq, hashPassword);
        Domain.MensajeReply serverResponse = this.stub.updateUsuario(datosUsuario);
        return this.gson.toJson(serverResponse);
    }

    public String getUsuario (String rutUsuario) {
        Domain.RutEntityReq rutEntityReq = Domain.RutEntityReq.newBuilder().setRut(rutUsuario).build();
        Domain.UsuarioEntityReply serverResponse = this.stub.getUsuario(rutEntityReq);
        return this.gson.toJson(serverResponse);
    }

    public String getUsuarios () {
        Domain.EmptyReq emptyReq = Domain.EmptyReq.newBuilder().build();
        Domain.UsuariosEntityReply serverResponse = this.stub.getUsuarios(emptyReq);
        return this.gson.toJson(serverResponse);
    }
}
