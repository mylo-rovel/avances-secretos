package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.Getter;
import lombok.Setter;

public class WebReqUltimasMedidas {
    @Getter
    @Setter
    private int id_ejecucion;
    @Getter
    @Setter
    private int id_sensor;
    @Getter
    @Setter
    private String timestamp;
    @Getter
    @Setter
    private int last_second;
    @Getter
    @Setter
    private int last_entrities;


}
