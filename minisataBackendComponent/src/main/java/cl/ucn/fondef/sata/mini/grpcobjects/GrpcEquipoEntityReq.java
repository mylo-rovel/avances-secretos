package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc equipo entity req.
 */
@ToString
@EqualsAndHashCode
public class GrpcEquipoEntityReq {

    @Getter
    @Setter
    private String rutConfigurador;

    @Getter
    @Setter
    private GrpcEquipoEntity equipo;
}
