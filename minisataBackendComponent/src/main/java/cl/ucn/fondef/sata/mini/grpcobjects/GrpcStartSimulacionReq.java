package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcStartSimulacionReq {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String nombreEquipo;
}
