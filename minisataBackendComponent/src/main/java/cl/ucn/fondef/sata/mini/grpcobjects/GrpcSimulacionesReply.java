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
public class GrpcSimulacionesReply {

    @Getter
    @Setter
    private GrpcSimulacionAcotada[] listaSimulacionesAcotadas;
}
