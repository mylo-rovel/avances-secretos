package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.*;
import io.grpc.Grpc;
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
     * @return the string
     */
    public String getSimulaciones(String rut){
        Domain.RutEntityReq requestObj = Domain.RutEntityReq.newBuilder().setRut(rut).build();
        Domain.SimulacionesReply serverResponse = this.stub.getSimulaciones(requestObj);
        return this.gson.toJson(serverResponse);
    }

    public String addSimulacion(GrpcSimulacionReq simulacionReq) {
        Domain.SimulacionReq.Builder nuevaSimulacionReq = Domain.SimulacionReq.newBuilder()
                .setNombre(simulacionReq.getNombre())
                .setDescripcion(simulacionReq.getDescripcion())
                .setNombreEquipo(simulacionReq.getNombreEquipo())
                .setRutOperador(simulacionReq.getRutOperador());

        List<GrpcSecuencia> listaSecuencias = simulacionReq.getListaSecuencias();
        for (int i = 0; i < listaSecuencias.size(); i++) {
            GrpcSecuencia secuenciaRecibida = listaSecuencias.get(i);
            Domain.Secuencia.Builder secuenciaEnviar = Domain.Secuencia.newBuilder()
                    .setIdComponente(secuenciaRecibida.getIdComponente());

            List<GrpcEvento> listaEventos= secuenciaRecibida.getListaEventos();
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

    public String startSimulacion(GrpcStartSimulacionReq startSimulacionReq) {
        Domain.StartSimulacionReq requestObj = Domain.StartSimulacionReq.newBuilder()
                .setIdSimulacion(startSimulacionReq.getId()).setNombreEquipo(startSimulacionReq.getNombreEquipo()).build();
        Domain.MensajeReply serverResponse = this.stub.startSimulacion(requestObj);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Gets simulacion actual.
     *
     * @return the simulacion actual
     */
    public String getSimulacionActual() {
        return "";
    }

    public String getEjecucion(long idEjecucion, String rut) {
        Domain.IdElementoConRutReq idElementoConRutReq = Domain.IdElementoConRutReq.newBuilder()
                .setId( idEjecucion ).setRut(rut).build();
        Domain.EjecucionReply serverResponse = this.stub.getEjecucion(idElementoConRutReq);
        return this.gson.toJson(serverResponse);
    }

    public String getEjecuciones(String rut) {
        Domain.RutEntityReq rutReq = Domain.RutEntityReq.newBuilder().setRut(rut).build();
        Domain.EjecucionesReply serverResponse = this.stub.getEjecuciones(rutReq);
        return this.gson.toJson(serverResponse);
    }

}
