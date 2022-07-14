package cl.ucn.fondef.sata.mini.utilities.webrequests;


import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Web req placa sub entity.
 */
@ToString
@EqualsAndHashCode
public class WebReqPlacaSubEntity {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private Domain.TipoPlaca tipo;
}
