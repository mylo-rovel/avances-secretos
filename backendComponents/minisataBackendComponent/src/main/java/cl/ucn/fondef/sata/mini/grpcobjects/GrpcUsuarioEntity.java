package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcUsuarioEntity {
    enum EstadoUsuario {
        ACTIVO,
        INACTIVO
    }

    enum RolUsuario {
        ADMINISTRADOR,
        CONFIGURADOR,
        OPERADOR
    }

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
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private RolUsuario rol;

    @Getter
    @Setter
    private EstadoUsuario estado;

}
