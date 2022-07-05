package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEvento;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcSecuencia;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcSimulacionReply;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcSimulacionReq;
import io.grpc.Grpc;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core client grpc simulacion.
 */
@Service
public final class WebCoreClientGrpcSimulacion extends WebCoreClientGrpcBase {

    /**
     * Get simulacion string.
     *
     * @param idSimulacion the id simulacion
     * @return the string
     */
    public String getSimulacion(long idSimulacion){
        Domain.IdElementoReq idElementoReturn = Domain.IdElementoReq.newBuilder().setId(idSimulacion).build();
        Domain.SimulacionReply serverResponse = this.stub.getSimulacion(idElementoReturn);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Get simulaciones string.
     *
     * @return the string
     */
    public String getSimulaciones(){
        Domain.EmptyReq emptyReq = Domain.EmptyReq.newBuilder().build();
        Domain.SimulacionesReply serverResponse = this.stub.getSimulaciones(emptyReq);
        return this.gson.toJson(serverResponse);
    }

    /**
     * Start simulacion string.
     *
     * @param simulacionNueva the simulacion nueva
     * @return the string
     */
    public String startSimulacion(GrpcSimulacionReply simulacionNueva) {
        Domain.SimulacionReply.Builder simulacionReq = Domain.SimulacionReply.newBuilder()
                .setNombre(simulacionNueva.getNombre())
                .setDescripcion(simulacionNueva.getDescripcion())
                .setNombreEquipo(simulacionNueva.getNombre())
                //.setRutOperador(simulacionNueva.getRutOperador())
                .setFechaEjecucion(simulacionNueva.getFechaEjecucion());

        List<GrpcSecuencia> listaSecuencias = grpSecuenciasReq.getListaSecuencias();
        for (int i = 0; i < listaSecuencias.size(); i++) {
            GrpcSecuencia secuenciaRecibida = listaSecuencias.get(i);
            Domain.Secuencia.Builder secuenciaEnviar = Domain.Secuencia.newBuilder()
                    .setIdComponente(secuenciaRecibida.getIdComponente())
                    .setNombreComponente(secuenciaRecibida.getNombreComponente());

            List<GrpcEvento> listaEventos= secuenciaRecibida.getListaEventos();
            for (int j = 0; j < listaEventos.size(); j ++) {
                Domain.Evento eventoEnviar = Domain.Evento.newBuilder()
                        .setIntensidad(listaEventos.get(j).getIntensidad())
                        .setDuracion(listaEventos.get(j).getDuracion())
                        .setPosicion(j)
                        .build();
                secuenciaEnviar.addEvento(eventoEnviar);
            }
            secuenciasReq.addSecuencia(secuenciaEnviar);
        }
        Domain.MensajeReply serverResponse = this.stub.addSecuencias(secuenciasReq.build());
        return this.gson.toJson(serverResponse);
    }

    public String startSimulacion(GrpcSimulacionReq simulacionNueva) {

        return "";
    }

    /**
     * Gets simulacion actual.
     *
     * @return the simulacion actual
     */
    public String getSimulacionActual() {
        return "";
    }

}
