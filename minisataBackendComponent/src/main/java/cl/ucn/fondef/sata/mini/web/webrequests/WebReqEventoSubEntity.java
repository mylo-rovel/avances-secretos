package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Web req evento sub entity.
 */
@ToString
@EqualsAndHashCode
public class WebReqEventoSubEntity {
    @Getter
    @Setter
    private int intensidad;

    @Getter
    @Setter
    private int duracion;
}
