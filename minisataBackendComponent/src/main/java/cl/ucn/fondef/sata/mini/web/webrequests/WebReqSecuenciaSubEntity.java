package cl.ucn.fondef.sata.mini.web.webrequests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Web req secuencia sub entity.
 */
@ToString
@EqualsAndHashCode
public class WebReqSecuenciaSubEntity {

    @Getter
    @Setter
    private long idComponente;

    @Getter
    @Setter
    private String nombreComponente;

    @Getter
    @Setter
    private List<WebReqEventoSubEntity> listaEventos;
}
