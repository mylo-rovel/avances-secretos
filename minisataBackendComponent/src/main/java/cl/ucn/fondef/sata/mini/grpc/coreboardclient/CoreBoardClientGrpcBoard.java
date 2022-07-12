package cl.ucn.fondef.sata.mini.grpc.coreboardclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import org.springframework.stereotype.Service;

@Service
public class CoreBoardClientGrpcBoard extends CoreBoardClientGrpcBase{

    public Domain.MensajeReply startSimulacion (Domain.SimulacionBoardReq simulacionBoardReq){
        return stub.startSimulacion(simulacionBoardReq);
    }
}
