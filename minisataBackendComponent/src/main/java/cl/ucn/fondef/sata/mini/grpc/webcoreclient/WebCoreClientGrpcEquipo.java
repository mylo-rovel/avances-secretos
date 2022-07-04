package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core client grpc equipo.
 */
@Service
public final class WebCoreClientGrpcEquipo extends WebCoreClientGrpcBase {

    // todo: separar este bloque gigante en funciones chiquititas

    // Esta funcion es ejecutada por 'this.addEquipo()' y 'this.updateEquipo' dado que
    // comparten la parte de crear un equipo a enviar
    private Domain.EquipoEntityReq getEquipoGrpcEnviar(GrpcEquipoEntityReq equipoRecibido) {
        Domain.EquipoEntity.Builder equipoGrpc = Domain.EquipoEntity.newBuilder()
                .setNombre(equipoRecibido.getEquipo().getNombre())
                .setDescripcion(equipoRecibido.getEquipo().getDescripcion())
                .setUrlRepositorio(equipoRecibido.getEquipo().getUrlRepositorio())
                .setEstado(equipoRecibido.getEquipo().getEstado());

        // ITERAMOS SOBRE EL OBJETO RECIBIDO PARA CREAR OBJETOS...
        // ... "repeated Placa placa = 6;" A ENVIAR
        List<GrpcPlaca> listaPlacasRecibidas = equipoRecibido.getEquipo().getListaPlacas();
        for (int i = 0; i < listaPlacasRecibidas.size(); i++) {
            GrpcPlaca placaRecibida = listaPlacasRecibidas.get(i);

            Domain.Placa placaEnviar = Domain.Placa.newBuilder()
                    .setNombre(placaRecibida.getNombre())
                    .setDescripcion(placaRecibida.getDescripcion())
                    .setTipo(placaRecibida.getTipo())
                    .build();

            equipoGrpc.addPlaca(placaEnviar);
        }


        // ... "repeated Componente componente_fisico = 7;" A ENVIAR
        List<GrpcComponente> listaComponentesRecibidos = equipoRecibido.getEquipo().getListaComponentes();
        for (int i = 0; i < listaComponentesRecibidos.size(); i++) {
            GrpcComponente componenteRecibido = listaComponentesRecibidos.get(i);

            Domain.Componente.Builder componenteEnviar = Domain.Componente.newBuilder()
                    .setNombre(componenteRecibido.getNombre())
                    .setDescripcion(componenteRecibido.getDescripcion())
                    .setUrl(componenteRecibido.getUrl())
                    .setEstado(componenteRecibido.getEstado())
                    .setTipo(componenteRecibido.getTipo())
                    .setTipoPlaca(componenteRecibido.getTipoPlaca());

            List<GrpcPin> listaPinesComponente = componenteRecibido.getListaPines();
            for (int j = 0; j < listaPinesComponente.size(); j ++) {
                GrpcPin pinComponenteRecibido = listaPinesComponente.get(j);
                Domain.Pin pinEnviar = Domain.Pin.newBuilder()
                        .setNumero(pinComponenteRecibido.getNumero())
                        .setNombre(pinComponenteRecibido.getNombre())
                        .setDescripcion(pinComponenteRecibido.getDescripcion())
                        .setConexion(pinComponenteRecibido.getConexion())
                        .build();
                componenteEnviar.addPinComponente(pinEnviar);
            }
            equipoGrpc.addComponente(componenteEnviar);
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

    public String getEquipoOC(long id, String rut){
        Domain.IdElementoConRutReq idEquipoUsuario = Domain.IdElementoConRutReq.newBuilder().setId(id).setRut(rut).build();
        Domain.EquipoEntityReply serverResponse = this.stub.getEquipoOC(idEquipoUsuario);
        return this.gson.toJson(serverResponse);
    }

    public String getEquiposOC(String rut){
        Domain.RutEntityReq rutUsuario = Domain.RutEntityReq.newBuilder().setRut(rut).build();
        Domain.EquiposEntityReply serverResponse = this.stub.getEquiposOC(rutUsuario);
        return this.gson.toJson(serverResponse);
    }
    /**
     * Upload archivo string.
     *
     * @param archivoNuevo the archivo nuevo
     * @return the string
     */
// Esto es un stream
    public String uploadArchivo(GrpcArchivosEntityReq archivoNuevo) {
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
        Domain.ComponentesEquipoReply serverResponse = this.stub.getValvulasEquipo(idRequest);
        return this.gson.toJson(serverResponse);
    }

    public String getSecuenciasComponente(long idEquipo){
        Domain.IdElementoReq idElementoReq = Domain.IdElementoReq.newBuilder().setId(idEquipo).build();
        Domain.SecuenciasComponenteEquipoReply serverResponse = this.stub.getSecuenciasComponente(idElementoReq);
        return this.gson.toJson(serverResponse);
    }
}
