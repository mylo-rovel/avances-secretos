package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Registro;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core service grpc extra.
 */
@Service
public class WebCoreServiceGrpcExtra {

    @Autowired
    private CoreDaoExtra coreDaoExtra;

    public Domain.MensajeReply getRegistros(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.RegistrosReply> responseObserver){
        List<Registro> listaRegistros = coreDaoExtra.getRegistros(rutEntityReq);

        Domain.MensajeReply.Builder grpcResponse = Domain.MensajeReply.newBuilder();
/*        for (int i = 0; i < listaRegistros.size(); i++) {

        }*/

        return grpcResponse.build();
    }
}
