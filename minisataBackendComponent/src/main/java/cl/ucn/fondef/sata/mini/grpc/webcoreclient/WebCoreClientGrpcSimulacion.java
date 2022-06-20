package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcSimulacionReq;
import org.springframework.stereotype.Service;

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
        Domain.SimulacionReq simulacionReq = Domain.SimulacionReq.newBuilder()
                .set
        return "";
    }

    public String getSimulacionActual() {
        return "";
    }

}
