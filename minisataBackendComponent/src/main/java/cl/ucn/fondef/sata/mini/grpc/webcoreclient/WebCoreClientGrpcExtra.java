package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import org.springframework.stereotype.Service;

@Service
public class WebCoreClientGrpcExtra extends WebCoreClientGrpcBase{
    public String getLecturaSensores(long id) {
        return "";
    }

    public String getRegistros(String rut) {
        return "";
    }
}
