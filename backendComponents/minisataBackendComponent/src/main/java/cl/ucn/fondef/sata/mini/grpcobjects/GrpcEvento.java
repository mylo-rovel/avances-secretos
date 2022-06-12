package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
message Evento {
        int32 intensidad = 1;
        int32 duracion = 2;
}
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
}
