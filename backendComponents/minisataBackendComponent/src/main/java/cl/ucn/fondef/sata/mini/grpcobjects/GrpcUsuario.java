package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
// Campos obtenidos desde el archivo .proto
message Usuario {
  string nombre = 1;
  string apellido = 2;
  string rut = 3;
  string correo = 4;
  string contrasena = 5;
  string rol = 6;
  bool estado = 7;
}
}*/
@ToString
@EqualsAndHashCode
public class GrpcUsuario {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String apellido;

    @Getter
    @Setter
    private String rut;

    @Getter
    @Setter
    private String correo;

    @Getter
    @Setter
    private String contrasena;

    @Getter
    @Setter
    private String rol;

    @Getter
    @Setter
    private boolean estado;

}
