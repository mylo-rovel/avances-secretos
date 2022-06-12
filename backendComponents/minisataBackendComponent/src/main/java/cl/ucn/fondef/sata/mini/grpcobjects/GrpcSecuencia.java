package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
message Secuencia {
  repeated Evento lista_eventos = 1;
}
*/
@ToString
@EqualsAndHashCode
public class GrpcSecuencia {
    @Getter
    @Setter
    private GrpcEvento[] listaEventos;
}
