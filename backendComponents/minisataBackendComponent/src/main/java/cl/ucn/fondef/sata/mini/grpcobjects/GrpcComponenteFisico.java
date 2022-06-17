/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcComponenteFisico {

    enum EstadoComponente {
        ACTIVO,
        INACTIVO,
        FALLA,
        REPARACION
    }

    enum ConexionComponente {
        INPUT_ANALOGICO,
        INPUT_DIGITAL,
        OUPUT_ANALOGICO,
        OUPUT_DIGITAL
    }

    enum TipoComponente {
        CAMARA,
        FLUJOMETRO,
        TERMOMETRO,
        VALVULA
    }


    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private int pin;

    @Getter
    @Setter
    // solo para las webcam, para otros componentes es ""
    private String url;

    @Getter
    @Setter
    private EstadoComponente estado;

    @Getter
    @Setter
    private ConexionComponente conexion;

    @Getter
    @Setter
    private TipoComponente tipo;
}
