package cl.ucn.fondef.sata.mini.utilities.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Web req equipo req.
 */
@ToString
@EqualsAndHashCode
public class WebReqEquipoReq {

    @Getter
    @Setter
    private String rutConfigurador;

    @Getter
    @Setter
    private WebReqEquipoEntity equipo;
}
