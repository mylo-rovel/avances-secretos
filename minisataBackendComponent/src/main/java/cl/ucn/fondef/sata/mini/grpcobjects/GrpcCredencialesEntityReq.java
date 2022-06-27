package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc credenciales entity req.
 */
@ToString
@EqualsAndHashCode
public class GrpcCredencialesEntityReq {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;
}
