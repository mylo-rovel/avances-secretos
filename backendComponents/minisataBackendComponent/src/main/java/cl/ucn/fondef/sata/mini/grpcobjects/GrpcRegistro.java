package cl.ucn.fondef.sata.mini.grpcobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GrpcRegistro {
    enum TipoRegistro {
        CREACION_USUARIO,
        LOGIN_USUARIO,
        CREACION_SIMULACION,
        INICIO_SIMULACION,
        UPLOAD_ARCHIVO,
    }

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long idEntidad;

    @Getter
    @Setter
    private String timestamp;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private TipoRegistro tipo;

    @Getter
    @Setter
    private GrpcUsuarioEntity usuario;
}
