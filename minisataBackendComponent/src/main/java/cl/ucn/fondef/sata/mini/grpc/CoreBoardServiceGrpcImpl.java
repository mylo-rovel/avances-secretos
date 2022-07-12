package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.utilities.InformacionEjecucion;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class CoreBoardServiceGrpcImpl extends CoreBoardCommuServiceGrpc.CoreBoardCommuServiceImplBase{

    // aca recibimos peticiones de la raspi
    // AGREGAR FUNCION QUE RECIBA LA PETICION "rpc sendMensajeEncendido (SaludoBoardReq)  returns (SaludoBoardReply) {}"
    // PARA GUARDARLA EN EL HASHMAP DE LOS EQUIPOS ENCENDIDO "HashMap<String, InformacionEjecucion> ejecucionesEquipo"
    // LUEGO ENVIAR UN OBJETO VACIO TEMPORAL: Domain.SimulacionReply.newBuilder().build();

}