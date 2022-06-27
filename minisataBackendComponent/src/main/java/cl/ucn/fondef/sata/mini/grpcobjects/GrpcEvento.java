package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc evento.
 */
@ToString
@EqualsAndHashCode
public class GrpcEvento {
    @Getter
    @Setter
    private int intensidad;

    @Getter
    @Setter
    private int duracion;

    @Getter
    @Setter
    private int posicion;
}
