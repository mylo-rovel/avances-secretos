package cl.ucn.fondef.sata.mini.web.webrequests;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Web req pin sub entity.
 */
@ToString
@EqualsAndHashCode
public class WebReqPinSubEntity {

    @Getter
    @Setter
    private int numero;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private Domain.Pin.ConexionPin conexion;
}
