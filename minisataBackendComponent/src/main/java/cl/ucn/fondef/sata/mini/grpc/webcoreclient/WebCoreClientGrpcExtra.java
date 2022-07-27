package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import org.springframework.stereotype.Service;

/**
 * The type Web core client grpc extra.
 */
@Service
public final class WebCoreClientGrpcExtra extends WebCoreClientGrpcBase{
    /**
     * Gets registros.
     *
     * @param rutUsuario the rut usuario
     * @return the registros
     */
    public String getRegistros (String rutUsuario) {
        Domain.RutEntityReq rutEntityReq = Domain.RutEntityReq.newBuilder().setRut(rutUsuario).build();
        Domain.RegistrosReply serverResponse = this.stub.getRegistros(rutEntityReq);
        return this.gson.toJson(serverResponse);
    }
}
