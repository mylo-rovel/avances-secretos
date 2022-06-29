package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import org.springframework.stereotype.Service;

/**
 * The type Web core client grpc extra.
 */
@Service
public class WebCoreClientGrpcExtra extends WebCoreClientGrpcBase{
    /**
     * Gets lectura sensores.
     *
     * @param id the id
     * @return the lectura sensores
     */
    public String getLecturaSensores(long id) {
        return "";
    }

    /**
     * Gets registros.
     *
     * @param rut the rut
     * @return the registros
     */
    public String getRegistros(String rut) {
        Domain.RutEntityReq rutEntityReq = Domain.RutEntityReq.newBuilder().setRut(rut).build();
        Domain.RegistrosReply serverResponse = this.stub.getRegistros(rutEntityReq);
        return this.gson.toJson(serverResponse);
    }
}
