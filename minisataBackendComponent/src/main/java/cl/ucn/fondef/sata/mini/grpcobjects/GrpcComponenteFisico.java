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
public class GrpcComponenteFisico {

    @Getter
    @Setter
    private int numero;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    // solo para las webcam, para otros componentes es ""
    private String url;

    @Getter
    @Setter
    private Domain.ComponenteFisico.EstadoComponente estado;

    @Getter
    @Setter
    private Domain.ComponenteFisico.TipoComponente tipo;

    @Getter
    @Setter
    private List<GrpcPin> listaPines;
}
