package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
public class GrpcSecuenciasReq {

    @Getter
    @Setter
    private String nombreEquipo;

    @Getter
    @Setter
    private String rutOperador;

    @Getter
    @Setter
    private List<GrpcSecuencia> listaSecuencias;
}
