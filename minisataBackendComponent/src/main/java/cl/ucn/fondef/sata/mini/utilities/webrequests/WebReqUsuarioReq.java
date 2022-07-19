package cl.ucn.fondef.sata.mini.utilities.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Web req usuario req.
 */
@ToString
@EqualsAndHashCode
public class WebReqUsuarioReq {

    @Getter
    @Setter
    private String rutAdministrador;

    @Getter
    @Setter
    private WebReqUsuarioEntity usuario;
}

