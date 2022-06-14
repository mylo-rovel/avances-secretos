/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GrpcListaSimulacionesAcotada {

    @Getter
    @Setter
    private List<GrpcSimulacionAcotada> listaSimulacionAcotada;
}
