package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Grpc archivos equipo entity req.
 */
@ToString
@EqualsAndHashCode
public class GrpcArchivosEntityReq {

    @Getter
    @Setter
    private List<GrpcArchivoEntity> archivo;
}
