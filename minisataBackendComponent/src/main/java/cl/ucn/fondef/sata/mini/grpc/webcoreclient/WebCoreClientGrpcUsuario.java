package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.utilities.webrequests.WebReqCredencialesReq;
import cl.ucn.fondef.sata.mini.utilities.webrequests.WebReqUsuarioReq;
import org.springframework.stereotype.Service;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * The type Web core client grpc usuario.
 */
@Service
public final class WebCoreClientGrpcUsuario extends WebCoreClientGrpcBase {

    private Domain.UsuarioEntityReq getUsuarioGrpcReqObject (WebReqUsuarioReq webReqUsuarioReq, String password) {
        // CREAR EL OBJETO RPC QUE LLEVA LOS DATOS A ENVIAR
        Domain.UsuarioEntity datosUsuario = Domain.UsuarioEntity.newBuilder()
                .setNombre(webReqUsuarioReq.getUsuario().getNombre())
                .setApellido(webReqUsuarioReq.getUsuario().getApellido())
                .setRut(webReqUsuarioReq.getUsuario().getRut())
                .setEmail(webReqUsuarioReq.getUsuario().getEmail())
                .setPassword(password)
                .setRol(webReqUsuarioReq.getUsuario().getRol())
                .setEstado(webReqUsuarioReq.getUsuario().getEstado())
                .build();

        Domain.UsuarioEntityReq requestObject = Domain.UsuarioEntityReq.newBuilder()
                .setUsuario(datosUsuario)
                .setRutAdministrador(webReqUsuarioReq.getRutAdministrador())
                .build();
        return requestObject;
    }

    // ---- FUNCIONES AUX ----------------------------------------------------------------------------------------
    // ---- LLAMADAS RPC  ----------------------------------------------------------------------------------------

    /**
     * Authenticate string.
     *
     * @param webReqCredencialesReq the web req credenciales req
     * @return the string
     */
// USAR EL MISMO NOMBRE DE LA FUNCION A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public String authenticate (WebReqCredencialesReq webReqCredencialesReq) {
        // 3ro: Crear el objeto que sera enviado al server RPC "Central Core"
        // Aca le metemos los datos recibidos desde el cliente
        Domain.CredencialesEntityReq requestObject = Domain.CredencialesEntityReq.newBuilder()
                .setEmail( webReqCredencialesReq.getEmail() )
                .setPassword( webReqCredencialesReq.getPassword() )
                .build();

        // 4to: Enviar la peticion RPC y guardar la respuesta
        Domain.SesionEntityReply serverResponse = this.stub.authenticate(requestObject);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Add usuario string.
     *
     * @param webReqUsuarioReq the web req usuario req
     * @return the string
     */
    public String addUsuario (WebReqUsuarioReq webReqUsuarioReq){
        // HASHING LA NUEVA CONTRASEÑA
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashPassword= argon2.hash(1, 1024, 1, webReqUsuarioReq.getUsuario().getPassword());

        Domain.UsuarioEntityReq datosUsuario = this.getUsuarioGrpcReqObject(webReqUsuarioReq, hashPassword);
        Domain.MensajeReply serverResponse = this.stub.addUsuario(datosUsuario);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Update usuario string.
     *
     * @param webReqUsuarioReq the web req usuario req
     * @return the string
     */
    public String updateUsuario(WebReqUsuarioReq webReqUsuarioReq){
        // HASHING LA NUEVA CONTRASEÑA
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashPassword= argon2.hash(1, 1024, 1, webReqUsuarioReq.getUsuario().getPassword());

        Domain.UsuarioEntityReq datosUsuario = this.getUsuarioGrpcReqObject(webReqUsuarioReq, hashPassword);
        Domain.MensajeReply serverResponse = this.stub.updateUsuario(datosUsuario);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets usuario.
     *
     * @param rutUsuario the rut usuario
     * @return the usuario
     */
    public String getUsuario (String rutUsuario) {
        Domain.RutEntityReq rutEntityReq = Domain.RutEntityReq.newBuilder().setRut(rutUsuario).build();
        Domain.UsuarioEntityReply serverResponse = this.stub.getUsuario(rutEntityReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets usuarios.
     *
     * @return the usuarios
     */
    public String getUsuarios () {
        Domain.EmptyReq emptyReq = Domain.EmptyReq.newBuilder().build();
        Domain.UsuariosEntityReply serverResponse = this.stub.getUsuarios(emptyReq);
        return this.gson.toJson(serverResponse);
    }
}
