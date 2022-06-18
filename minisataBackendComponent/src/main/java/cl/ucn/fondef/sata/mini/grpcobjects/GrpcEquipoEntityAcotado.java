package cl.ucn.fondef.sata.mini.grpcobjects;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcEquipoEntityAcotado {

    @Getter
    @Setter
    private long nombre;

    @Getter
    @Setter
    private Domain.EstadoEquipo estado;
}
