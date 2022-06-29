package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Pin;
import cl.ucn.fondef.sata.mini.model.Placa;
import cl.ucn.fondef.sata.mini.utilities.JwtUtil;
import cl.ucn.fondef.sata.mini.utilities.StringEnumTransformer;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        String mensajeResultado = coreDaoEquipo.addEquipo(equipoEntityReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
    }

    /**
     * Update equipo domain . mensaje reply.
     *
     * @param equipoEntityReq  the equipo entity req
     * @param responseObserver the response observer
     * @return the domain . mensaje reply
     */
    public Domain.MensajeReply updateEquipo(Domain.EquipoEntityReq equipoEntityReq, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeResultado = coreDaoEquipo.updateEquipo(equipoEntityReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
        /*return Domain.MensajeReply.newBuilder().setMensajeTexto("Falta implementar addEquipo").build();*/
    }


    private Domain.EquipoEntity.Builder getEquipoEntityBuilder (Equipo equipoGuardado){
        Domain.EquipoEntity.Builder equipoEnte = Domain.EquipoEntity.newBuilder()
                .setId(equipoGuardado.getId())
                .setNombre(equipoGuardado.getNombre())
                .setDescripcion(equipoGuardado.getDescripcion())
                .setUrlRepositorio(equipoGuardado.getUrlRepositorio())
                .setEstado(stringEnumTransformer.getEnumEstadoEquipo(equipoGuardado.getEstado()));
        return equipoEnte;
    }

    private void addPlacasToEquipo(Domain.EquipoEntity.Builder equipoEnte, Domain.IdElementoReq idEquipoReq) {
        List<Placa> placasGuardadas = coreDaoEquipo.getPlacas(idEquipoReq);
        // ITERAR PARA CREAR CADA PROTOBUF DE "Placa"
        for (int i = 0; i < placasGuardadas.size(); i++) {
            Domain.Placa placaEnviar = Domain.Placa.newBuilder()
                    .setNombre(         placasGuardadas.get(i).getNombre())
                    .setDescripcion(    placasGuardadas.get(i).getDescripcion())
                    .setTipo(           stringEnumTransformer.getEnumTipoPlaca(placasGuardadas.get(i).getTipo()))
                    .build();
            equipoEnte.addPlaca(placaEnviar);
        }
        /*return equipoEnte;*/
    }

    private Domain.EquipoEntity.Builder addComponentesYPinesToEquipo(Domain.EquipoEntity.Builder equipoEnte, Domain.IdElementoReq idEquipoReq) {
        List<ComponenteFisico> componentesGuardados = coreDaoEquipo.getComponentesFisicos(idEquipoReq);
        // ITERAR PARA CREAR CADA PROTOBUF DE "ComponenteFisico"
        for (int i = 0; i < componentesGuardados.size(); i++) {
            Domain.ComponenteFisico.Builder componenteEnviar = Domain.ComponenteFisico.newBuilder()
                    .setId(         componentesGuardados.get(i).getId())
                    .setNombre(     componentesGuardados.get(i).getNombre())
                    .setDescripcion(componentesGuardados.get(i).getDescripcion())
                    .setUrl(        componentesGuardados.get(i).getUrl())
                    .setEstado(     stringEnumTransformer.getEnumEstadoComponente(componentesGuardados.get(i).getEstado()))
                    .setTipo(       stringEnumTransformer.getEnumTipoComponente(componentesGuardados.get(i).getTipo()))
                    .setTipoPlaca(  stringEnumTransformer.getEnumTipoPlaca(componentesGuardados.get(i).getTipoPlaca()));

            // ITERAR PARA CREAR CADA PROTOBUF DE "Pin"
            long idComponente = componentesGuardados.get(i).getId();
            List<Pin> pinesGuardados = coreDaoEquipo.getPines(idComponente);
            for (int j = 0; j < pinesGuardados.size(); j++) {
                Domain.Pin pinEnviar = Domain.Pin.newBuilder()
                        .setNumero(         pinesGuardados.get(j).getNumero())
                        .setNombre(         pinesGuardados.get(j).getNombre())
                        .setDescripcion(    pinesGuardados.get(j).getDescripcion())
                        .setConexion(       stringEnumTransformer.getEnumConexionPin(pinesGuardados.get(j).getConexion()))
                        .build();
                componenteEnviar.addPinComponente(pinEnviar);
            }
            equipoEnte.addComponenteFisico(componenteEnviar);
        }
        return equipoEnte;
    }

    public Domain.EquipoEntityReply getEquipo(Domain.IdElementoReq idEquipoReq, StreamObserver<Domain.EquipoEntityReply> responseObserver){
        Equipo equipoGuardado = coreDaoEquipo.getEquipo(idEquipoReq);
        if (equipoGuardado == null) { return Domain.EquipoEntityReply.newBuilder().build(); }

        Domain.EquipoEntity.Builder equipoEnte = this.getEquipoEntityBuilder(equipoGuardado);
        this.addPlacasToEquipo(equipoEnte, idEquipoReq);
        this.addComponentesYPinesToEquipo(equipoEnte, idEquipoReq);

        Domain.EquipoEntityReply equipoRetornar = Domain.EquipoEntityReply.newBuilder()
                .setEquipo(equipoEnte.build())
                .build();

        return equipoRetornar;
    }

    /**
     * Get equipos domain . equipos entity reply.
     *
     * @param emptyReq         the empty req
     * @param responseObserver the response observer
     * @return the domain . equipos entity reply
     */
    public Domain.EquiposEntityReply getEquipos(Domain.EmptyReq emptyReq, StreamObserver<Domain.EquiposEntityReply> responseObserver){
        List<Equipo> listaEquiposGuardados = coreDaoEquipo.getEquipos();
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

        return listaRetornar.build();
    }


    private void addValvulasToReplyObject (Domain.ComponentesEquipoReply.Builder grpcResponse, List<ComponenteFisico> listaValvulasGuardadas) {
        for (int i = 0; i < listaValvulasGuardadas.size(); i++) {
            Domain.ComponenteFisico componenteFisico = Domain.ComponenteFisico.newBuilder()
                    .setId(listaValvulasGuardadas.get(i).getId())
                    .setNombre(listaValvulasGuardadas.get(i).getNombre())
                    .setEstado(stringEnumTransformer.getEnumEstadoComponente(listaValvulasGuardadas.get(i).getEstado()))
                    .build();
            grpcResponse.addComponenteFisico(componenteFisico);
        }
    }

    public Domain.ComponentesEquipoReply getValvulasEquipo(Domain.IdElementoReq idRequest, StreamObserver<Domain.ComponentesEquipoReply> responseObserver){
        List<ComponenteFisico> listaValvulasGuardadas = coreDaoEquipo.getValvulasEquipo(idRequest);
        if (listaValvulasGuardadas == null) { return Domain.ComponentesEquipoReply.newBuilder().build(); }

        Domain.ComponentesEquipoReply.Builder grpcResponse = Domain.ComponentesEquipoReply.newBuilder();
        this.addValvulasToReplyObject(grpcResponse, listaValvulasGuardadas);

        return grpcResponse.build();
    }
}
