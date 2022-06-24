package cl.ucn.fondef.sata.mini.grpcobjects;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Grpc registro.
 */
@ToString
@EqualsAndHashCode
public class GrpcRegistro {

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
    private Domain.Registro.TipoRegistro tipo;

    @Getter
    @Setter
    private GrpcUsuarioEntity usuario;
}
