package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.utilities.StringEnumTransformer;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core service grpc equipo.
 */
@Service
public class WebCoreServiceGrpcEquipo {
    // EN ESTA CLASE CONSTRUIMOS LA RESPUESTA A RETORNAR AL CLIENTE RPC EN BASE A LO QUE
    // SE OBTIENE DE LA DB.
    // EN EL ARCHIVO PRINCIPAL 'WebCoreServiceGrpcImpl' SE LLAMAN ESTOS METODOS SOLO PARA
    // OBTENER EL OBJETO QUE SE DEVOLVERA
    @Autowired
    private CoreDaoEquipo coreDaoEquipo;

    /**
     * The String enum transformer.
     */
    @Autowired
    StringEnumTransformer stringEnumTransformer;

    /**
     * Add equipo domain . mensaje reply.
     *
     * @param equipoEntityReq  the equipo entity req
     * @param responseObserver the response observer
     * @return the domain . mensaje reply
     */
    public Domain.MensajeReply addEquipo(Domain.EquipoEntityReq equipoEntityReq, StreamObserver<Domain.MensajeReply> responseObserver){
        /*String mensajeResultado = coreDaoEquipo.addEquipo(equipoEntityReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;*/
        return Domain.MensajeReply.newBuilder().setMensajeTexto("Falta implementar addEquipo").build();
    }

    /**
     * Update equipo domain . mensaje reply.
     *
     * @param equipoEntityReq  the equipo entity req
     * @param responseObserver the response observer
     * @return the domain . mensaje reply
     */
    public Domain.MensajeReply updateEquipo(Domain.EquipoEntityReq equipoEntityReq, StreamObserver<Domain.MensajeReply> responseObserver){
        /*String mensajeResultado = coreDaoEquipo.updateEquipo(equipoEntityReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;*/
        return Domain.MensajeReply.newBuilder().setMensajeTexto("Falta implementar addEquipo").build();
    }

    /**
     * Get equipo domain . equipo entity reply.
     *
     * @param idElementoReq    the id elemento req
     * @param responseObserver the response observer
     * @return the domain . equipo entity reply
     */
    public Domain.EquipoEntityReply getEquipo(Domain.IdElementoReq idElementoReq, StreamObserver<Domain.EquipoEntityReply> responseObserver){
        /*Equipo equipoGuardado = coreDaoEquipo.getEquipo(idElementoReq);

        if (equipoGuardado == null) {
            return Domain.EquipoEntityReply.newBuilder().build();
        }

        Domain.EquipoEntity.Builder equipoEnte = Domain.EquipoEntity.newBuilder()
                .setId(equipoGuardado.getId())
                .setNombre(equipoGuardado.getNombre())
                .setDescripcion(equipoGuardado.getDescripcion())
                .setUrlRepositorio(equipoGuardado.getUrlRepositorio())
                .setEstado(stringEnumTransformer.getEnumEstadoEquipo(equipoGuardado.getEstado()));
                *//*.setRutConfigurador(equipoGuardado.getRutConfigurador());*//*

        List<ComponenteFisico> listaComponentesFisico = coreDaoEquipo.getComponentesFisicosEquipo(idElementoReq);
        if (listaComponentesFisico != null) {
            for(ComponenteFisico componenteFisico: listaComponentesFisico){
                Domain.ComponenteFisico componenteDominio = Domain.ComponenteFisico.newBuilder()
                        .setEstado(stringEnumTransformer.getEnumEstadoComponente(componenteFisico.getEstado()))
//                    .setConexion(stringEnumTransformer.getEnumConexionComponente(componenteFisico.getConexion()))
                        .setTipo(stringEnumTransformer.getEnumTipoComponente(componenteFisico.getTipo()))
                        .setNombre(componenteFisico.getNombre())
                        .setDescripcion(componenteFisico.getDescripcion())
//                    .setPin(componenteFisico.getPin())
                        .setUrl(componenteFisico.getUrl())
                        .build();
                equipoEnte.addComponenteFisico(componenteDominio);
            }
        }

        Domain.EquipoEntityReply equipoRetornar = Domain.EquipoEntityReply.newBuilder()
                .setEquipo(equipoEnte.build())
                .build();

        return equipoRetornar;*/
        return Domain.EquipoEntityReply.newBuilder().build();
    }

    /**
     * Get equipos domain . equipos entity reply.
     *
     * @param emptyReq         the empty req
     * @param responseObserver the response observer
     * @return the domain . equipos entity reply
     */
    public Domain.EquiposEntityReply getEquipos(Domain.EmptyReq emptyReq, StreamObserver<Domain.EquiposEntityReply> responseObserver){
        /*List<Equipo> listaEquiposGuardados = coreDaoEquipo.getEquipos();
        Domain.EquiposEntityReply.Builder listaRetornar = Domain.EquiposEntityReply.newBuilder();

        if (listaRetornar == null) {
            return listaRetornar.build();
        }

        for(Equipo equipo : listaEquiposGuardados){
            Domain.EquipoEntityAcotado equipoRetornar = Domain.EquipoEntityAcotado.newBuilder()
                    .setId(equipo.getId())
                    .setNombre(equipo.getNombre())
                    .setEstado(stringEnumTransformer.getEnumEstadoEquipo(equipo.getEstado()))
                    .build();

            listaRetornar.addEquipoAcotado(equipoRetornar);
        }

        return listaRetornar.build();*/
        return Domain.EquiposEntityReply.newBuilder().build();
    }

    /**
     * Get valvulas equipo domain . componentes equipo reply.
     *
     * @param idRequest        the id request
     * @param responseObserver the response observer
     * @return the domain . componentes equipo reply
     */
    public Domain.ComponentesEquipoReply getValvulasEquipo(Domain.IdElementoReq idRequest, StreamObserver<Domain.ComponentesEquipoReply> responseObserver){
        /*List<ComponenteFisico> listaValvulasGuardadas = coreDaoEquipo.getValvulasEquipo(idRequest);
        if (listaValvulasGuardadas == null) {
            return Domain.ComponentesEquipoReply.newBuilder().build();
        }
        Domain.ComponentesEquipoReply grpcResponse = Domain.ComponentesEquipoReply.newBuilder()
                .build();

        return grpcResponse;*/
        return  Domain.ComponentesEquipoReply.newBuilder().build();
    }
}
