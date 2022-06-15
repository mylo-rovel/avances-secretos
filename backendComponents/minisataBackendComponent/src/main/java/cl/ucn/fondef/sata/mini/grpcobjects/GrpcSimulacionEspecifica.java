/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.Getter;
import lombok.Setter;


public class GrpcSimulacionEspecifica {

    @Getter
    @Setter
    private Long idSimulacion;

    @Getter
    @Setter
    private String fechaSimulacion;

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private String descrEquipo;

    @Getter
    @Setter
    private GrpcSecuencia[] listaSecuencias;

    @Getter
    @Setter
    private Double aguaCaida;
}
