package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
message ParametrosInicioSimulacion {
    int32 id_simulador = 1;
    string rut_operador = 2;
    string nombre_simulacion = 3;
    string descripcion_simulacion = 4;
    repeated Secuencia lista_secuencias = 5;
}
*/
@ToString
@EqualsAndHashCode
public class GrpcParametrosInicioSimulacion {
    @Getter
    @Setter
    private int idSimulador;

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
