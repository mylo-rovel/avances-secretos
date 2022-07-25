package cl.ucn.fondef.sata.mini.utilities;

import cl.ucn.fondef.sata.mini.grpc.coreboardclient.CoreBoardClientGrpcBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Informacion board.
 */
@ToString
@EqualsAndHashCode
public class InformacionBoard {

    @Getter
    @Setter
    private List<String> valoresGrafico;

    @Getter
    @Setter
    private boolean estaEjecutandose;

    @Getter
    @Setter
    private double aguaCaidaActual;

    @Getter
    @Setter
    private CoreBoardClientGrpcBase coreBoardClient;

    /**
     * Instantiates a new Informacion board.
     *
     * @param direccion the direccion
     */
    public InformacionBoard(String direccion) {
        this.coreBoardClient = new CoreBoardClientGrpcBase(direccion);
        this.aguaCaidaActual = 0.0;
        this.estaEjecutandose = false;
        this.valoresGrafico = new ArrayList<String>();
    }

    public void resetCoreBoardClient(String newDireccion) {
        this.coreBoardClient.shutdownCoreBoardClient();
        this.coreBoardClient = new CoreBoardClientGrpcBase(newDireccion);
    }
}
