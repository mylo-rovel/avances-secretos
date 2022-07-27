package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.web.webrequests.WebReqComponenteSubEntity;
import cl.ucn.fondef.sata.mini.web.webrequests.WebReqEquipoReq;
import cl.ucn.fondef.sata.mini.web.webrequests.WebReqPinSubEntity;
import cl.ucn.fondef.sata.mini.web.webrequests.WebReqPlacaSubEntity;
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
    private Domain.EquipoEntityReq getEquipoGrpcEnviar(WebReqEquipoReq equipoRecibido) {
        Domain.EquipoEntity.Builder equipoGrpc = Domain.EquipoEntity.newBuilder()
                .setNombre(equipoRecibido.getEquipo().getNombre())
                .setDescripcion(equipoRecibido.getEquipo().getDescripcion())
                .setUrlRepositorio(equipoRecibido.getEquipo().getUrlRepositorio())
                .setEstado(equipoRecibido.getEquipo().getEstado());

        // ITERAMOS SOBRE EL OBJETO RECIBIDO PARA CREAR OBJETOS...
        // ... "repeated Placa placa = 6;" A ENVIAR
        List<WebReqPlacaSubEntity> listaPlacasRecibidas = equipoRecibido.getEquipo().getListaPlacas();
        for (int i = 0; i < listaPlacasRecibidas.size(); i++) {
            WebReqPlacaSubEntity placaRecibida = listaPlacasRecibidas.get(i);

            Domain.Placa placaEnviar = Domain.Placa.newBuilder()
                    .setNombre(placaRecibida.getNombre())
                    .setDescripcion(placaRecibida.getDescripcion())
                    .setTipo(placaRecibida.getTipo())
                    .build();

            equipoGrpc.addPlaca(placaEnviar);
        }


        // ... "repeated Componente componente_fisico = 7;" A ENVIAR
        List<WebReqComponenteSubEntity> listaComponentesRecibidos = equipoRecibido.getEquipo().getListaComponentes();
        for (int i = 0; i < listaComponentesRecibidos.size(); i++) {
            WebReqComponenteSubEntity componenteRecibido = listaComponentesRecibidos.get(i);

            Domain.Componente.Builder componenteEnviar = Domain.Componente.newBuilder()
                    .setNombre(componenteRecibido.getNombre())
                    .setDescripcion(componenteRecibido.getDescripcion())
                    .setUrl(componenteRecibido.getUrl())
                    .setEstado(componenteRecibido.getEstado())
                    .setTipo(componenteRecibido.getTipo())
                    .setTipoPlaca(componenteRecibido.getTipoPlaca());

            List<WebReqPinSubEntity> listaPinesComponente = componenteRecibido.getListaPines();
            for (int j = 0; j < listaPinesComponente.size(); j ++) {
                WebReqPinSubEntity pinComponenteRecibido = listaPinesComponente.get(j);
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
    public String addEquipo (WebReqEquipoReq equipoRecibido){
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
    public String updateEquipo(WebReqEquipoReq equipoRecibido){
        Domain.EquipoEntityReq equipoEntityReq = this.getEquipoGrpcEnviar(equipoRecibido);
        Domain.MensajeReply serverResponse = this.stub.updateEquipo(equipoEntityReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Get equipo string.
     *
     * @param id  the id
     * @param rut the rut
     * @return the string
     */
    public String getEquipo(long id, String rut){
        Domain.IdElementoConRutReq idEquipoUsuarioReq = Domain.IdElementoConRutReq.newBuilder().setId(id).setRut(rut).build();
        Domain.EquipoEntityReply serverResponse = this.stub.getEquipo(idEquipoUsuarioReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Get equipos string.
     *
     * @param rut the rut
     * @return the string
     */
    public String getEquipos(String rut){
        Domain.RutEntityReq rutUsuarioReq = Domain.RutEntityReq.newBuilder().setRut(rut).build();
        Domain.EquiposEntityReply serverResponse = this.stub.getEquipos(rutUsuarioReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Get valvulas equipo string.
     *
     * @param idEquipo the id equipo
     * @return the string
     */
    public String getValvulasEquipo(long idEquipo){
        Domain.IdElementoReq requestObj = Domain.IdElementoReq.newBuilder().setId(idEquipo).build();
        Domain.ComponentesEquipoReply serverResponse = this.stub.getValvulasEquipo(requestObj);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Get secuencias componente string.
     *
     * @param idEquipo the id equipo
     * @return the string
     */
    public String getSecuenciasComponente(long idEquipo){
        Domain.IdElementoReq idElementoReq = Domain.IdElementoReq.newBuilder().setId(idEquipo).build();
        Domain.SecuenciasComponenteEquipoReply serverResponse = this.stub.getSecuenciasComponente(idElementoReq);
        return this.gson.toJson(serverResponse);
    }
}
