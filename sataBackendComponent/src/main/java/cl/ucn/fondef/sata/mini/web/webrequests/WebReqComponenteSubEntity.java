/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web.webrequests;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Web req componente sub entity.
 */
@ToString
@EqualsAndHashCode
public class WebReqComponenteSubEntity {

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
    private Domain.Componente.EstadoComponente estado;

    @Getter
    @Setter
    private Domain.Componente.TipoComponente tipo;

    @Getter
    @Setter
    private Domain.TipoPlaca tipoPlaca;

    @Getter
    @Setter
    private List<WebReqPinSubEntity> listaPines;
}
