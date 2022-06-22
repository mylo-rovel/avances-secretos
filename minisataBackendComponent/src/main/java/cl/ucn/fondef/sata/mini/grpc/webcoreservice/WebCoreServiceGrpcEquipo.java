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

@Service
public class WebCoreServiceGrpcEquipo {
    // EN ESTA CLASE CONSTRUIMOS LA RESPUESTA A RETORNAR AL CLIENTE RPC EN BASE A LO QUE
    // SE OBTIENE DE LA DB.
    // EN EL ARCHIVO PRINCIPAL 'WebCoreServiceGrpcImpl' SE LLAMAN ESTOS METODOS SOLO PARA
    // OBTENER EL OBJETO QUE SE DEVOLVERA
    @Autowired
    private CoreDaoEquipo coreDaoEquipo;

    @Autowired
    StringEnumTransformer stringEnumTransformer;

    public Domain.MensajeReply addEquipo(Domain.EquipoEntityReq equipoEntityReq, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeResultado = coreDaoEquipo.addEquipo(equipoEntityReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
    }

    public Domain.MensajeReply setEquipo(Domain.EquipoEntityReq equipoEntityReq, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeResultado = coreDaoEquipo.setEquipo(equipoEntityReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
    }

    public Domain.EquipoEntityReply getEquipo(Domain.IdElementoReq idElementoReq, StreamObserver<Domain.EquipoEntityReply> responseObserver){
        Equipo equipoGuardado = coreDaoEquipo.getEquipo(idElementoReq);

        Domain.EquipoEntity.Builder equipoEnte = Domain.EquipoEntity.newBuilder()
                .setId(equipoGuardado.getId())
                .setNombre(equipoGuardado.getNombre())
                .setDescripcion(equipoGuardado.getDescripcion())
                .setUrlRepositorio(equipoGuardado.getUrlRepositorio())
                .setEstado(stringEnumTransformer.getEnumEstadoEquipo(equipoGuardado.getEstado()))
                .setRutConfigurador(equipoGuardado.getRutConfigurador());

        List<ComponenteFisico> listaComponenteFisico = coreDaoEquipo.getComponentesFisicosEquipo(idElementoReq);
        for(ComponenteFisico componenteFisico: listaComponenteFisico){
            Domain.ComponenteFisico componenteDominio = Domain.ComponenteFisico.newBuilder()
                    .setEstado(stringEnumTransformer.getEnumEstadoComponente(componenteFisico.getEstado()))
                    .setConexion(stringEnumTransformer.getEnumConexionComponente(componenteFisico.getConexion()))
                    .setTipo(stringEnumTransformer.getEnumTipoComponente(componenteFisico.getTipo()))
                    .setNombre(componenteFisico.getNombre())
                    .setDescripcion(componenteFisico.getDescripcion())
                    .setPin(componenteFisico.getPin())
                    .setUrl(componenteFisico.getUrl())
                    .build();
            equipoEnte.addComponenteFisico(componenteDominio);
        }

        Domain.EquipoEntityReply equipoRetornar = Domain.EquipoEntityReply.newBuilder()
                .setEquipo(equipoEnte.build())
                .build();

        return equipoRetornar;
    }

    public Domain.EquiposEntityReply getEquipos(Domain.EmptyReq emptyReq, StreamObserver<Domain.EquiposEntityReply> responseObserver){
        List<Equipo> listaEquiposGuardados = coreDaoEquipo.getEquipos();
        Domain.EquiposEntityReply.Builder listaRetornar = Domain.EquiposEntityReply.newBuilder();

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
}
