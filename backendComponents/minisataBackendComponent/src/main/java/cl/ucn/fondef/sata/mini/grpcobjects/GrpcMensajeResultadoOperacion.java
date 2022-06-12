package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
// Campos obtenidos desde el archivo .proto
message MensajeResultadoOperacion {
  string mensaje_texto = 1;
}
}*/
@ToString
@EqualsAndHashCode
public class GrpcMensajeResultadoOperacion {

    @Getter
    @Setter
    private String mensajeTexto;
}
