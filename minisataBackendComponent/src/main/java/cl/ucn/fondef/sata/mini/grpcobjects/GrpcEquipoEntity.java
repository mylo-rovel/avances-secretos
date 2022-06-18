/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpcobjects;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcEquipoEntity {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String urlRepositorio;

    @Getter
    @Setter
    private GrpcComponenteFisico[] listaValvulas;

    @Getter
    @Setter
    private GrpcComponenteFisico[] listaSensores;

    @Getter
    @Setter
    private GrpcComponenteFisico[] listaCamaras;

    @Getter
    @Setter
    private Domain.EstadoEquipo estado;

    @Getter
    @Setter
    private String rutConfigurador;
}
