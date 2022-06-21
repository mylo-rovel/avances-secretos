package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEvento;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcSecuencia;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcSimulacionReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebCoreClientGrpcSimulacion extends WebCoreClientGrpcBase {

    public String getSimulacion(long idSimulacion){
        Domain.IdElementoReq idElementoReturn = Domain.IdElementoReq.newBuilder().setId(idSimulacion).build();
        Domain.SimulacionReply serverResponse = this.stub.getSimulacion(idElementoReturn);
        return this.gson.toJson(serverResponse);
    }

    public String getSimulaciones(){
        Domain.EmptyReq emptyReq = Domain.EmptyReq.newBuilder().build();
        Domain.SimulacionesReply serverResponse = this.stub.getSimulaciones(emptyReq);
        return this.gson.toJson(serverResponse);
    }
    public String startSimulacion(GrpcSimulacionReq simulacionNueva) {
        Domain.SimulacionReq.Builder simulacionReq = Domain.SimulacionReq.newBuilder()
                .setNombre(simulacionNueva.getNombre())
                .setDescripcion(simulacionNueva.getDescripcion())
                .setNombreEquipo(simulacionNueva.getNombre())
                .setRutOperador(simulacionNueva.getRutOperador())
                .setFechaEjecucion(simulacionNueva.getFechaEjecucion());

        List<GrpcSecuencia> secuenciasRecibidas = simulacionNueva.getListaSecuencias();
        for (int i = 0; i < secuenciasRecibidas.size(); i++){
            Domain.Secuencia.Builder secuenciaEnviar = Domain.Secuencia.newBuilder();
            List<GrpcEvento> eventosRecibidos = secuenciasRecibidas.get(i).getListaEventos();
            for (int j = 0; j < eventosRecibidos.size(); j ++) {
                Domain.Evento eventoEnviar = Domain.Evento.newBuilder()
                        .setIntensidad(eventosRecibidos.get(j).getIntensidad())
                        .setDuracion(eventosRecibidos.get(j).getDuracion())
                        .setPosicion(eventosRecibidos.get(j).getPosicion())
                        .build();
                secuenciaEnviar.addEvento(eventoEnviar);
            }
            simulacionReq.addSecuencia(secuenciaEnviar);
        }

        return "";
    }

    public String getSimulacionActual() {
        return "";
    }

}
