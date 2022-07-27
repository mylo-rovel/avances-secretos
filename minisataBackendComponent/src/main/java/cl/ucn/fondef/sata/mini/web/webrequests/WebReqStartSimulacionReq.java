package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Web req start simulacion req.
 */
@ToString
@EqualsAndHashCode
public class WebReqStartSimulacionReq {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String nombreEquipo;
}
