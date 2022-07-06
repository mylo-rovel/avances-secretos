package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.utilities.StringEnumTransformer;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core service grpc usuario.
 */
@Service
public class WebCoreServiceGrpcUsuario {
    // EN ESTA CLASE CONSTRUIMOS LA RESPUESTA A RETORNAR AL CLIENTE RPC EN BASE A LO QUE
    // SE OBTIENE DE LA DB.
    // EN EL ARCHIVO PRINCIPAL 'WebCoreServiceGrpcImpl' SE LLAMAN ESTOS METODOS SOLO PARA
    // OBTENER EL OBJETO QUE SE DEVOLVERA
    @Autowired
    private CoreDaoUsuario coreDaoUsuario;

    @Autowired
    private CoreDaoExtra coreDaoExtra;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * The String enum transformer.
     */
    @Autowired
    StringEnumTransformer stringEnumTransformer;

    /**
     * Authenticate domain . sesion entity reply.
     *
     * @param reqCredenciales  the req credenciales
     * @param responseObserver the response observer
     * @return the domain . sesion entity reply
     */
    public Domain.SesionEntityReply authenticate(Domain.CredencialesEntityReq reqCredenciales, StreamObserver<Domain.SesionEntityReply> responseObserver) {
        // Llamar a coreDaoUsuario para hacer el proceso relacionado a la base de datos
        boolean credencialesCorrectas = coreDaoUsuario.credencialesSonCorrectas(reqCredenciales);
        Usuario usuarioLogeado = coreDaoUsuario.getUsuarioPorCorreo(reqCredenciales.getEmail());

        String jwtUsuario = (credencialesCorrectas && (usuarioLogeado != null )) ?
                jwtUtil.create(usuarioLogeado.getRut(), usuarioLogeado.getRol())
                : "";

        Domain.SesionEntityReply grpcResponse = Domain.SesionEntityReply.newBuilder()
                .setSesionIniciada(credencialesCorrectas)
                .setJsonWebToken(jwtUsuario)
                .build();

        coreDaoExtra.addRegistroLoginUsuario(usuarioLogeado);

        return grpcResponse;
    }

    /**
     * Add usuario domain . mensaje reply.
     *
     * @param reqUsuarioNuevo  the req usuario nuevo
     * @param responseObserver the response observer
     * @return the domain . mensaje reply
     */
    public Domain.MensajeReply addUsuario(Domain.UsuarioEntityReq reqUsuarioNuevo, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeRespuesta = coreDaoUsuario.addUsuario(reqUsuarioNuevo);

        // PROCESO DE ENVÍO DE RESPUESTA GRPC
        // 1ro: Construir el objeto que almacenará la información a devolver al cliente
        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeRespuesta)
                .build();

        return grpcResponse;
    }
    private void attachUsuarioEnteToResponse(Domain.UsuarioEntityReply.Builder usuarioRetornar, Usuario usuarioGuardado) {
        Domain.UsuarioEntity.Builder usuarioEnte = Domain.UsuarioEntity.newBuilder()
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

    public Domain.UsuarioEntityReply getUsuario(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.UsuarioEntityReply> responseObserver) {
        Usuario usuarioGuardado = coreDaoUsuario.getUsuario(rutEntityReq);

        Domain.UsuarioEntityReply.Builder usuarioRetornar = Domain.UsuarioEntityReply.newBuilder();
        if (usuarioGuardado != null) {
            attachUsuarioEnteToResponse(usuarioRetornar, usuarioGuardado);
        }
        return usuarioRetornar.build();
    }

    /**
     * Gets usuarios.
     *
     * @param emptyReq         the empty req
     * @param responseObserver the response observer
     * @return the usuarios
     */
    public Domain.UsuariosEntityReply getUsuarios(Domain.EmptyReq emptyReq, StreamObserver<Domain.UsuariosEntityReply> responseObserver) {
        List<Usuario> listaUsuariosGuardados = coreDaoUsuario.getUsuarios();
        Domain.UsuariosEntityReply.Builder listaRetornar = Domain.UsuariosEntityReply.newBuilder();

        if (listaUsuariosGuardados != null) {
            for (Usuario usuario : listaUsuariosGuardados) {
                Domain.UsuarioEntity usuarioEnte = Domain.UsuarioEntity.newBuilder()
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
        return listaRetornar.build();
    }

    /**
     * Update usuario domain . mensaje reply.
     *
     * @param usuarioEntityReq the usuario entity req
     * @param responseObserver the response observer
     * @return the domain . mensaje reply
     */
    public Domain.MensajeReply updateUsuario(Domain.UsuarioEntityReq usuarioEntityReq, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeResultado = coreDaoUsuario.updateUsuario(usuarioEntityReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
    }
}
