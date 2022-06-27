package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc equipo entity reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcEquipoEntityReply {

    @Getter
    @Setter
    private GrpcEquipoEntity equipo;
}
