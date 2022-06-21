package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcArchivosEquipoEntityReq;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcComponenteFisico;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntityReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebCoreClientGrpcEquipo extends WebCoreClientGrpcBase {

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

    public String setEquipo(GrpcEquipoEntityReq equipoModificado){
        Domain.EquipoEntity.Builder equipoEnviar = Domain.EquipoEntity.newBuilder()
                .setNombre(equipoModificado.getEquipo().getNombre())
                .setUrlRepositorio(equipoModificado.getEquipo().getUrlRepositorio())
                .setDescripcion(equipoModificado.getEquipo().getDescripcion())
                .setRutConfigurador(equipoModificado.getEquipo().getRutConfigurador());

        // ITERAMOS SOBRE EL OBJETO RECIBIDO PARA CREAR OBJETOS...
        // ... "repeated ComponenteFisico componente_fisico = 5;" A ENVIAR
        List<GrpcComponenteFisico> listaComponentesNuevos = equipoModificado.getEquipo().getListaComponentesFisicos();
        for (int i = 0; i < listaComponentesNuevos.size(); i++) {
            GrpcComponenteFisico componenteFuenteInformacion = listaComponentesNuevos.get(i);
            Domain.ComponenteFisico componenteEnviar = Domain.ComponenteFisico.newBuilder()
                    .setPin(componenteFuenteInformacion.getPin())
                    .setNombre(componenteFuenteInformacion.getNombre())
                    .setDescripcion(componenteFuenteInformacion.getDescripcion())
                    .setUrl(componenteFuenteInformacion.getUrl())
                    .setEstado(componenteFuenteInformacion.getEstado())
                    .setConexion(componenteFuenteInformacion.getConexion())
                    .setTipo(componenteFuenteInformacion.getTipo())
                    .build();
            equipoEnviar.addComponenteFisico(componenteEnviar);
        }

        Domain.EquipoEntityReq equipoEntityReq = Domain.EquipoEntityReq.newBuilder()
                .setEquipo(equipoEnviar.build())
                .build();

        Domain.MensajeReply serverResponse = this.stub.addEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);
    }

    public String getEquipo(long id) {
        Domain.IdElementoReq idEquipo = Domain.IdElementoReq.newBuilder().setId(id).build();
        Domain.EquipoEntityReply serverResponse = this.stub.getEquipo(idEquipo);
        return this.gson.toJson(serverResponse);
    }

    public String getEquipos() {
        Domain.EmptyReq emptyReq = Domain.EmptyReq.newBuilder().build();
        Domain.EquiposEntityReply serverResponse = this.stub.getEquipos(emptyReq);
        return this.gson.toJson(serverResponse);
    }

    // Esto es un stream
    public String uploadArchivo(GrpcArchivosEquipoEntityReq archivoNuevo) {
        return "";
    }

    // Esto es un stream
    public String getArchivos(long idEquipo) {
        return "";
    }

    public String getValvulasEquipo(long idEquipo) {
        Domain.IdElementoReq idRequest = Domain.IdElementoReq.newBuilder().setId(idEquipo).build();
/*        Domain.ComponentesEquipoReply serverResponse = this.stub.getValvulasEquipo(idRequest);
        return this.gson.toJson(serverResponse);*/
        return "";
    }
}
