package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class WebReqEstadoGraficoUsuarioReq {
    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private int indiceInicial;

    @Getter
    @Setter
    private int indiceFinal;
}
