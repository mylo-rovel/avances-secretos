package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Grpc simulacion reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcSimulacionReply {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private String descripcionEquipo;


    @Getter
    @Setter
    private String fechaEjecucion;

    @Getter
    @Setter
    private List<GrpcSecuencia> listaSecuencias;

    @Getter
    @Setter
    private double aguaCaida;
}
