/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.utilities.webrequests;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Web req equipo entity.
 */
@ToString
@EqualsAndHashCode
public class WebReqEquipoEntity {

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
    private List<WebReqPlacaSubEntity> listaPlacas;

    @Getter
    @Setter
    private List<WebReqComponenteSubEntity> listaComponentes;
}
