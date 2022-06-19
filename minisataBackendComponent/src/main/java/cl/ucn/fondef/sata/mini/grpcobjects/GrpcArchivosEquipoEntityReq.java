package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
public class GrpcArchivosEquipoEntityReq {

    @Getter
    @Setter
    private List<GrpcArchivoEquipoEntity> archivo;
}
