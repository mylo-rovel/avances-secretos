package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Web req credenciales req.
 */
@ToString
@EqualsAndHashCode
public class WebReqCredencialesReq {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;
}
