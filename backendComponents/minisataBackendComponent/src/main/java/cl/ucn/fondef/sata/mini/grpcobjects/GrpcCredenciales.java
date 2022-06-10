package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
// Campos obtenidos desde el archivo .proto
message Credenciales {
        string correo = 1;
        string contrasena = 2;
}
*/
@ToString
@EqualsAndHashCode
public class GrpcCredenciales {

    @Getter
    @Setter
    private String correo;

    @Getter
    @Setter
    private String contrasena;
}
