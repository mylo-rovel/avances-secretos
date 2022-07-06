package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
public class GrpcEjecucionAcotada {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String nombreSimulacion;

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private String fechaEjecucion;

    @Getter
    @Setter
    private double aguaCaida;
}
