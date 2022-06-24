package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc lectura sensores reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcLecturaSensoresReply {

    @Getter
    @Setter
    private String nombreSimulacion;

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private double flujoMedido;

    @Getter
    @Setter
    private double tiempoTranscurrido;
}

