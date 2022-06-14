package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;

/*
message Evento {
        int32 intensidad = 1;
        int32 duracion = 2;
}
*/
@ToString
@EqualsAndHashCode
public class GrpcImagenesEquipo {
    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private BufferedImage imagen;
}
