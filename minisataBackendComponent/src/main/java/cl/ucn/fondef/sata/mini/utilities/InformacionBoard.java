package cl.ucn.fondef.sata.mini.utilities;

import cl.ucn.fondef.sata.mini.grpc.coreboardclient.CoreBoardClientGrpcBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class InformacionBoard {

    @Getter
    @Setter
    private List<Double> valoresGrafico;

    @Getter
    @Setter
    private boolean estaEjecutandose;

    @Getter
    @Setter
    private double aguaCaidaActual;

    @Getter
    @Setter
    private CoreBoardClientGrpcBase coreBoardClient;

    public InformacionBoard(String direccion) {
        this.coreBoardClient = new CoreBoardClientGrpcBase(direccion);
        this.aguaCaidaActual = 0.0;
        this.estaEjecutandose = false;
        this.valoresGrafico = new ArrayList<Double>();
    }
}
