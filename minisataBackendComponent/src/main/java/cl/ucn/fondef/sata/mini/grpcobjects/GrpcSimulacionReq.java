/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
public class GrpcSimulacionReq {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private Long nombreEquipo;

    @Getter
    @Setter
    private String rutOperador;

    @Getter
    @Setter
    private String fechaEjecucion;

    @Getter
    @Setter
    private List<GrpcSecuencia> listaSecuencias;
}
