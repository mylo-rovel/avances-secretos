package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

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
        return "";
    }
}
