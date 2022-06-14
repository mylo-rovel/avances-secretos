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
public class GrpcSimulacion {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String rutOperador;

    @Getter
    @Setter
    private Long idEquipo;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String fechaCreacion;

    @Getter
    @Setter
    private String fechaEjecucion;

    @Getter
    @Setter
    private Double aguaCaida;
}
