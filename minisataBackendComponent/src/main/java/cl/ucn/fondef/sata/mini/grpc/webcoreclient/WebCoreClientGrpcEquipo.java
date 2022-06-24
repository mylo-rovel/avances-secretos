package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcArchivosEquipoEntityReq;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcComponenteFisico;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntityReq;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcPin;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core client grpc equipo.
 */
@Service
public class WebCoreClientGrpcEquipo extends WebCoreClientGrpcBase {

    // Esta funcion es ejecutada por 'this.addEquipo()' y 'this.updateEquipo' dado que
    // comparten la parte de crear un equipo a enviar
    private Domain.EquipoEntityReq getEquipoGrpcEnviar(GrpcEquipoEntityReq equipoRecibido) {
        Domain.EquipoEntity.Builder equipoGrpc = Domain.EquipoEntity.newBuilder()
                .setNombre(equipoRecibido.getEquipo().getNombre())
                .setDescripcion(equipoRecibido.getEquipo().getDescripcion())
                .setUrlRepositorio(equipoRecibido.getEquipo().getUrlRepositorio())
                .setEstado(equipoRecibido.getEquipo().getEstado());

        // ITERAMOS SOBRE EL OBJETO RECIBIDO PARA CREAR OBJETOS...
        // ... "repeated ComponenteFisico componente_fisico = 6;" A ENVIAR
        List<GrpcComponenteFisico> listaComponentesRecibidos = equipoRecibido.getEquipo().getListaComponentesFisicos();

        for (int i = 0; i < listaComponentesRecibidos.size(); i++) {
            GrpcComponenteFisico componenteRecibido = listaComponentesRecibidos.get(i);

            Domain.ComponenteFisico.Builder componenteEnviar = Domain.ComponenteFisico.newBuilder()
                    .setNombre(componenteRecibido.getNombre())
                    .setDescripcion(componenteRecibido.getDescripcion())
                    .setUrl(componenteRecibido.getUrl())
                    .setEstado(componenteRecibido.getEstado())
                    .setTipo(componenteRecibido.getTipo());

            List<GrpcPin> listaPinesComponente = componenteRecibido.getListaPines();
            for (int j = 0; j < listaPinesComponente.size(); j ++) {
                GrpcPin pinComponenteRecibido = listaPinesComponente.get(j);
                Domain.Pin pinEnviar = Domain.Pin.newBuilder()
                        .setNumero(pinComponenteRecibido.getNumero())
                        .setNombre(pinComponenteRecibido.getNombre())
                        .setDescripcion(pinComponenteRecibido.getDescripcion())
                        .setPlaca(pinComponenteRecibido.getPlaca())
                        .setConexion(pinComponenteRecibido.getConexion())
                        .build();
                componenteEnviar.addPinComponente(pinEnviar);
            }
            equipoGrpc.addComponenteFisico(componenteEnviar);
        }

        Domain.EquipoEntityReq equipoEntityReq = Domain.EquipoEntityReq.newBuilder()
                .setRutConfigurador(equipoRecibido.getRutConfigurador())
                .setEquipo(equipoGrpc.build())
                .build();
        return equipoEntityReq;
    }

    // ---- FUNCIONES AUX ----------------------------------------------------------------------------------------
    // ---- LLAMADAS RPC  ----------------------------------------------------------------------------------------

    /**
     * Add equipo string.
     *
     * @param equipoRecibido the equipo recibido
     * @return the string
     */
    public String addEquipo (GrpcEquipoEntityReq equipoRecibido){
        Domain.EquipoEntityReq equipoEntityReq = this.getEquipoGrpcEnviar(equipoRecibido);
        Domain.MensajeReply serverResponse = this.stub.addEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Update equipo string.
     *
     * @param equipoRecibido the equipo recibido
     * @return the string
     */
    public String updateEquipo(GrpcEquipoEntityReq equipoRecibido){
        Domain.EquipoEntityReq equipoEntityReq = this.getEquipoGrpcEnviar(equipoRecibido);
        Domain.MensajeReply serverResponse = this.stub.updateEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets equipo.
     *
     * @param id the id
     * @return the equipo
     */
    public String getEquipo(long id) {
        Domain.IdElementoReq idEquipo = Domain.IdElementoReq.newBuilder().setId(id).build();
        Domain.EquipoEntityReply serverResponse = this.stub.getEquipo(idEquipo);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets equipos.
     *
     * @return the equipos
     */
    public String getEquipos() {
        Domain.EmptyReq emptyReq = Domain.EmptyReq.newBuilder().build();
        Domain.EquiposEntityReply serverResponse = this.stub.getEquipos(emptyReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Upload archivo string.
     *
     * @param archivoNuevo the archivo nuevo
     * @return the string
     */
// Esto es un stream
    public String uploadArchivo(GrpcArchivosEquipoEntityReq archivoNuevo) {
        return "";
    }

    /**
     * Gets archivos.
     *
     * @param idEquipo the id equipo
     * @return the archivos
     */
// Esto es un stream
    public String getArchivos(long idEquipo) {
        return "";
    }

    /**
     * Gets valvulas equipo.
     *
     * @param idEquipo the id equipo
     * @return the valvulas equipo
     */
    public String getValvulasEquipo(long idEquipo) {
        Domain.IdElementoReq idRequest = Domain.IdElementoReq.newBuilder().setId(idEquipo).build();
/*        Domain.ComponentesEquipoReply serverResponse = this.stub.getValvulasEquipo(idRequest);
        return this.gson.toJson(serverResponse);*/
        return "";
    }
}
