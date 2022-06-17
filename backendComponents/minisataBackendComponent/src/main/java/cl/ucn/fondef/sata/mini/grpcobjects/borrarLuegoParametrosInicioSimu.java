package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class borrarLuegoParametrosInicioSimu {
    @Getter
    @Setter
    private long idEquipo;

    @Getter
    @Setter
    private String rutOperador;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private GrpcSecuencia[] listaSecuencias;
}
