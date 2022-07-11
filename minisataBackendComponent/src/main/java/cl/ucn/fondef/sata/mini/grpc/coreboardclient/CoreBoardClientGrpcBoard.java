package cl.ucn.fondef.sata.mini.grpc.coreboardclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntityReq;
import org.springframework.stereotype.Service;

@Service
public class CoreBoardClientGrpcBoard extends CoreBoardClientGrpcBase{

    public String startSimulacion (Domain.SimulacionBoardReq simulacionBoardReq){
        System.out.println("simulacionBoardReq.getIdSimulacion() = " + simulacionBoardReq.getIdSimulacion());
        System.out.println("simulacionBoardReq.getSecuenciaList() = " + simulacionBoardReq.getSecuenciaList());
        var temporal = Domain.SimulacionBoardReq.newBuilder()
                .setIdSimulacion(10)
                .addAllSecuencia(simulacionBoardReq.getSecuenciaList())
                .build();
        Domain.MensajeReply mensajeRaspi = stub.startSimulacion(temporal);
//        System.out.println("mensajeRaspi = " + mensajeRaspi);
        return "HOLA";
    }
}
