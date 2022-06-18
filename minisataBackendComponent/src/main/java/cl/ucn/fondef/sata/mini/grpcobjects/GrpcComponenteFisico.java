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
public class GrpcComponenteFisico {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private int pin;

    @Getter
    @Setter
    // solo para las webcam, para otros componentes es ""
    private String url;

    @Getter
    @Setter
    private Domain.ComponenteFisico.EstadoComponente estado;

    @Getter
    @Setter
    private Domain.ComponenteFisico.ConexionComponente conexion;

    @Getter
    @Setter
    private Domain.ComponenteFisico.TipoComponente tipo;
}
