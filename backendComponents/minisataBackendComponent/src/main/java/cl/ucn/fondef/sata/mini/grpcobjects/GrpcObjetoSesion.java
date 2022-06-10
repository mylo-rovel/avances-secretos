package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
// Campos obtenidos desde el archivo .proto
message ObjetoSesion {
    bool sesion_iniciada = 1;
    string Json_Web_Token = 2;
}
}*/
@ToString
@EqualsAndHashCode
public class GrpcObjetoSesion {

    @Getter
    @Setter
    private boolean sesionIniciada;

    @Getter
    @Setter
    private String jsonWebToken;
}
