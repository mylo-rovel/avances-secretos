package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Grpc archivos equipo entity reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcArchivosEntityReply {

    @Getter
    @Setter
    private List<GrpcArchivoEntity> archivo;
}
