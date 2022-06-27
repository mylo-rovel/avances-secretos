package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc usuario entity req.
 */
@ToString
@EqualsAndHashCode
public class GrpcUsuarioEntityReq {

    @Getter
    @Setter
    private String rutAdministrador;

    @Getter
    @Setter
    private GrpcUsuarioEntity usuario;
}

