package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcComponenteFisico;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntityReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebCoreClientGrpcEquipos extends WebCoreClientGrpcBase {

    public String addEquipo (GrpcEquipoEntityReq equipoNuevo){
        Domain.EquipoEntity.Builder equipoRecibidoEnviar = Domain.EquipoEntity.newBuilder()
                .setNombre(equipoNuevo.getEquipo().getNombre())
                .setDescripcion(equipoNuevo.getEquipo().getDescripcion())
                .setUrlRepositorio(equipoNuevo.getEquipo().getUrlRepositorio())
                .setRutConfigurador(equipoNuevo.getEquipo().getRutConfigurador());

        // ITERAMOS SOBRE EL OBJETO RECIBIDO PARA CREAR OBJETOS...
        // ... "repeated ComponenteFisico componente_fisico = 5;" A ENVIAR
        List<GrpcComponenteFisico> listaComponentesNuevos = equipoNuevo.getEquipo().getListaComponentesFisicos();
        for (int i = 0; i < listaComponentesNuevos.size(); i++) {
            GrpcComponenteFisico componenteFuenteInformacion = listaComponentesNuevos.get(i);
            Domain.ComponenteFisico componenteEnviar = Domain.ComponenteFisico.newBuilder()
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

        Domain.EquipoEntityReq equipoEntityReq = Domain.EquipoEntityReq.newBuilder()
                .setEquipo(equipoRecibidoEnviar.build())
                .build();

/*        Domain.MensajeReply serverResponse = this.stub.addEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);*/
        return this.gson.toJson(equipoEntityReq);
    }

    public String setEquipo(GrpcEquipoEntityReq equipoNuevo){

    }
}
