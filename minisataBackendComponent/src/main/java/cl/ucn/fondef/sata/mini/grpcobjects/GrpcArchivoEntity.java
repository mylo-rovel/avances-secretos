package cl.ucn.fondef.sata.mini.grpcobjects;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

/**
 * The type Grpc archivo equipo entity.
 */
@ToString
@EqualsAndHashCode
public class GrpcArchivoEntity {

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private File archivo;

    @Getter
    @Setter
    private Domain.ArchivoEntity.TipoArchivo tipo;
}
