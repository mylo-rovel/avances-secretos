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
public class GrpcEquipo {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String urlRepo;

    @Getter
    @Setter
    private boolean estado;

    @Getter
    @Setter
    private GrpcCompFisico[] listaValvulas;

    @Getter
    @Setter
    private GrpcCompFisico[] listaSensores;

    @Getter
    @Setter
    private GrpcCompFisico[] listaCamaras;

    @Getter
    @Setter
    private String rutConfigurador;
}
