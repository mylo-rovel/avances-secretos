/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpcobjects;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Grpc equipo entity.
 */
@ToString
@EqualsAndHashCode
public class GrpcEquipoEntity {

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
    private String urlRepositorio;

    @Getter
    @Setter
    private Domain.EstadoEquipo estado;

    @Getter
    @Setter
    private List<GrpcPlaca> listaPlacas;

    @Getter
    @Setter
    private List<GrpcComponenteFisico> listaComponentesFisicos;
}
