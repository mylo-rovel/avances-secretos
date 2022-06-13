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
public class GrpcCompFisico {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String descripcion;
    @Getter
    @Setter
    private int pin;
    @Getter
    @Setter
    private boolean estado;
}
