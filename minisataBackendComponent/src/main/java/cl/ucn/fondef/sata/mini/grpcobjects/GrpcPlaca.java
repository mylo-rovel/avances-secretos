package cl.ucn.fondef.sata.mini.grpcobjects;


import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcPlaca {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private Domain.TipoPlaca tipo;
}
