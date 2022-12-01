package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class MedidaSensorReq {
    @Getter
    @Setter
    private Long idEjecucion;

    @Getter
    @Setter
    private String tipoSensor;
}
