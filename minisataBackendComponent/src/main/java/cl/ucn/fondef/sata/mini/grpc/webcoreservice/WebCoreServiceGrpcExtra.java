package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Registro;
import cl.ucn.fondef.sata.mini.model.Usuario;
import cl.ucn.fondef.sata.mini.utilities.StringEnumTransformer;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core service grpc extra.
 */
@Service
public class WebCoreServiceGrpcExtra {

    @Autowired
    private CoreDaoExtra coreDaoExtra;

    @Autowired
    private CoreDaoUsuario coreDaoUsuario;

    /**
     * The String enum transformer.
     */
    @Autowired
    StringEnumTransformer stringEnumTransformer;

    private Domain.UsuarioEntity getProtobufUsuarioRegistro(Usuario usuarioRegistro) {
        return Domain.UsuarioEntity.newBuilder()
                .setNombre(usuarioRegistro.getNombre())
                .setApellido(usuarioRegistro.getApellido())
                .setEstado(stringEnumTransformer.getEnumEstadoUsuario(usuarioRegistro.getEstado()))
                .setRol(stringEnumTransformer.getEnumRolUsuario(usuarioRegistro.getRol()))
                .build();
    }

    private void attachRegistroToResponse(Domain.RegistrosReply.Builder grpcResponse, List<Registro> listaRegistros, Domain.UsuarioEntity usuarioRegistro) {
        for (int i = 0; i < listaRegistros.size(); i++) {
            Domain.Registro registroEnviar = Domain.Registro.newBuilder()
                    .setId(             listaRegistros.get(i).getId())
                    .setIdEntidad(      listaRegistros.get(i).getIdEntidad())
                    .setTimestamp(      listaRegistros.get(i).getFecha())
                    .setDescripcion(    listaRegistros.get(i).getDescripcion())
                    .setTipo(stringEnumTransformer.getEnumTipoRegistro(listaRegistros.get(i).getTipoRegistro()))
                    .setUsuario(usuarioRegistro)
                    .build();
            grpcResponse.addRegistro(registroEnviar);
        }
    }

    /**
     * Get registros domain . registros reply.
     *
     * @param rutEntityReq     the rut entity req
     * @param responseObserver the response observer
     * @return the domain . registros reply
     */
    public Domain.RegistrosReply getRegistros(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.RegistrosReply> responseObserver){
        List<Registro> listaRegistros = coreDaoExtra.getRegistros(rutEntityReq);
        Domain.RegistrosReply.Builder grpcResponse = Domain.RegistrosReply.newBuilder();
        if (listaRegistros == null) {
            return grpcResponse.build();
        }
        Domain.UsuarioEntity usuarioRegistro = this.getProtobufUsuarioRegistro(coreDaoUsuario.getUsuario(rutEntityReq));
        this.attachRegistroToResponse(grpcResponse, listaRegistros, usuarioRegistro);
        return grpcResponse.build();
    }
}
