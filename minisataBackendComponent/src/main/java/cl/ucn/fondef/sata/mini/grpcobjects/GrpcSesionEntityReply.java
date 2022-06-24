package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc sesion entity reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcSesionEntityReply {

    @Getter
    @Setter
    private boolean sesionIniciada;

    @Getter
    @Setter
    private String jsonWebToken;
}
