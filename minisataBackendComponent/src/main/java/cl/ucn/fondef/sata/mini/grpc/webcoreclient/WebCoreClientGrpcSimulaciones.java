package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import org.springframework.stereotype.Service;

@Service
public class WebCoreClientGrpcSimulaciones extends WebCoreClientGrpcBase {

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
}
