/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Web req simulacion req.
 */
@ToString
@EqualsAndHashCode
public class WebReqSimulacionReq {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private String rutOperador;

    @Getter
    @Setter
    private List<WebReqSecuenciaSubEntity> listaSecuencias;


}
