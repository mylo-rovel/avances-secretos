package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

@ToString
@EqualsAndHashCode
public class GrpcArchivosEquipoEntity {
    enum TipoArchivo {
        PNG,
        PDF,
        JPG,
        JPEG
    }

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private File archivo;

    @Getter
    @Setter
    private TipoArchivo tipo;
}
