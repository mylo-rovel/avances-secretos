/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Grpc simulacion req.
 */
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
    private String nombreEquipo;

    @Getter
    @Setter
    private String rutOperador;

    @Getter
    @Setter
    private List<GrpcSecuencia> listaSecuencias;
}
