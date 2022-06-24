package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc mensaje reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcMensajeReply {

    @Getter
    @Setter
    private String mensajeTexto;
}
