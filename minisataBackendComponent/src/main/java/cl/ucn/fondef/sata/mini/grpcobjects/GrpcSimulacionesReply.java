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
 * The type Grpc simulaciones reply.
 */
@ToString
@EqualsAndHashCode
public class GrpcSimulacionesReply {

    @Getter
    @Setter
    private List<GrpcSimulacionAcotada> listaSimulacionesAcotadas;
}
