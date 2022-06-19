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
    private List<GrpcComponenteFisico> listaComponentesFisicos;

    @Getter
    @Setter
    private Domain.EstadoEquipo estado;

    @Getter
    @Setter
    private String rutConfigurador;
}
