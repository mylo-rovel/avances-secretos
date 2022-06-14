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
public class GrpcSimulacionAcotada {

    @Getter
    @Setter
    private int idSimulacion;

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private String fechaSimulacion;

    @Getter
    @Setter
    private Double aguaCaida;
}
