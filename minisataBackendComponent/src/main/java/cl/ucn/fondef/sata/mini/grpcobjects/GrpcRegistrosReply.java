package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Grpc registros reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcRegistrosReply {

    @Getter
    @Setter
    private List<GrpcRegistro> registro;
}