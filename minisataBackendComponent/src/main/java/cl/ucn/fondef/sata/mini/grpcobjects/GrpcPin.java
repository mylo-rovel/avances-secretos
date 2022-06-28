package cl.ucn.fondef.sata.mini.grpcobjects;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc pin.
 */
@ToString
@EqualsAndHashCode
public class GrpcPin {

    @Getter
    @Setter
    private int numero;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private Domain.Pin.ConexionPin conexion;
}