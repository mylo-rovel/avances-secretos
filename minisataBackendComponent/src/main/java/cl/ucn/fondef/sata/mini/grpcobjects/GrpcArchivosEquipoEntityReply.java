package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcArchivosEquipoEntityReply {

    @Getter
    @Setter
    private GrpcArchivosEquipoEntity archivo;
}
