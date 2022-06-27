package cl.ucn.fondef.sata.mini.grpcobjects;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc usuario entity.
 */
@ToString
@EqualsAndHashCode
public class GrpcUsuarioEntity {

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

    /**
     * The Rol.
     */
    @Getter
    @Setter
    public Domain.UsuarioEntity.RolUsuario rol;

    /**
     * The Estado.
     */
    @Getter
    @Setter
    public Domain.UsuarioEntity.EstadoUsuario estado;

}
