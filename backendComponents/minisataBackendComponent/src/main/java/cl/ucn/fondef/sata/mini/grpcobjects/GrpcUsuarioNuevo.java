package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
// Campos obtenidos desde el archivo .proto
message UsuarioNuevo {
  string rut_administrador = 1;
  Usuario usuario_nuevo = 2;
}
}*/
@ToString
@EqualsAndHashCode
public class GrpcUsuarioNuevo {

    @Getter
    @Setter
    private String rutAdministrador;

    @Getter
    @Setter
    private GrpcUsuario usuarioNuevo;
}

