package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.utilities.webrequests.WebReqEventoSubEntity;
import cl.ucn.fondef.sata.mini.utilities.webrequests.WebReqSecuenciaSubEntity;
import cl.ucn.fondef.sata.mini.utilities.webrequests.WebReqSimulacionReq;
import cl.ucn.fondef.sata.mini.utilities.webrequests.WebReqStartSimulacionReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core client grpc simulacion.
 */
@Slf4j
@Service
public final class WebCoreClientGrpcSimulacion extends WebCoreClientGrpcBase {

    /**
     * Get simulacion string.
     *
     * @param idSimulacion the id simulacion
     * @param rut          the rut
     * @return the string
     */
    public String getSimulacion(long idSimulacion, String rut){
        Domain.IdElementoConRutReq requestObj = Domain.IdElementoConRutReq.newBuilder()
                .setId(idSimulacion).setRut(rut).build();
        Domain.SimulacionReply serverResponse = this.stub.getSimulacion(requestObj);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Get simulaciones string.
     *
     * @param rut the rut
     * @return the string
     */
    public String getSimulaciones(String rut){
        Domain.RutEntityReq requestObj = Domain.RutEntityReq.newBuilder().setRut(rut).build();
        Domain.SimulacionesReply serverResponse = this.stub.getSimulaciones(requestObj);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Add simulacion string.
     *
     * @param simulacionReq the simulacion req
     * @return the string
     */
    public String addSimulacion(WebReqSimulacionReq simulacionReq) {
        Domain.SimulacionReq.Builder nuevaSimulacionReq = Domain.SimulacionReq.newBuilder()
                .setNombre(simulacionReq.getNombre())
                .setDescripcion(simulacionReq.getDescripcion())
                .setNombreEquipo(simulacionReq.getNombreEquipo())
                .setRutOperador(simulacionReq.getRutOperador());

        List<WebReqSecuenciaSubEntity> listaSecuencias = simulacionReq.getListaSecuencias();
        for (int i = 0; i < listaSecuencias.size(); i++) {
            WebReqSecuenciaSubEntity secuenciaRecibida = listaSecuencias.get(i);
            Domain.Secuencia.Builder secuenciaEnviar = Domain.Secuencia.newBuilder()
                    .setIdComponente(secuenciaRecibida.getIdComponente());

            List<WebReqEventoSubEntity> listaEventos= secuenciaRecibida.getListaEventos();
            for (int j = 0; j < listaEventos.size(); j ++) {
                Domain.Evento eventoEnviar = Domain.Evento.newBuilder()
                        .setIntensidad(listaEventos.get(j).getIntensidad())
                        .setDuracion(listaEventos.get(j).getDuracion())
                        .setPosicion(j)
                        .build();
                secuenciaEnviar.addEvento(eventoEnviar);
            }
            nuevaSimulacionReq.addSecuencia(secuenciaEnviar);
        }
        Domain.MensajeReply serverResponse = this.stub.addSimulacion(nuevaSimulacionReq.build());
        return this.gson.toJson(serverResponse);
    }

    /**
     * Start simulacion string.
     *
     * @param startSimulacionReq the start simulacion req
     * @return the string
     */
    public String startSimulacion(WebReqStartSimulacionReq startSimulacionReq) {
        Domain.StartSimulacionReq requestObj = Domain.StartSimulacionReq.newBuilder()
                .setIdSimulacion(startSimulacionReq.getId()).setNombreEquipo(startSimulacionReq.getNombreEquipo()).build();
        Domain.MensajeReply serverResponse = this.stub.startSimulacion(requestObj);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets equipos trabajando.
     *
     * @return the equipos trabajando
     */
    public String getEquiposTrabajando() {
        Domain.EmptyReq emptyReq = Domain.EmptyReq.newBuilder().build();
        Domain.EquiposEntityReply serverResponse = this.stub.getEquiposTrabajando(emptyReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets ejecucion.
     *
     * @param idEjecucion the id ejecucion
     * @param rut         the rut
     * @return the ejecucion
     */
    public String getEjecucion(long idEjecucion, String rut) {
        Domain.IdElementoConRutReq idElementoConRutReq = Domain.IdElementoConRutReq.newBuilder()
                .setId( idEjecucion ).setRut(rut).build();
        Domain.EjecucionReply serverResponse = this.stub.getEjecucion(idElementoConRutReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets ejecuciones.
     *
     * @param rut the rut
     * @return the ejecuciones
     */
    public String getEjecuciones(String rut) {
        Domain.RutEntityReq rutReq = Domain.RutEntityReq.newBuilder().setRut(rut).build();
        Domain.EjecucionesReply serverResponse = this.stub.getEjecuciones(rutReq);
        return this.gson.toJson(serverResponse);
    }

}
