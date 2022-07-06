package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Web core service grpc simulacion.
 */
@Slf4j
@Service
public class WebCoreServiceGrpcSimulacion {

    @Autowired
    private CoreDaoSimulacion coreDaoSimulacion;

    @Autowired
    private CoreDaoEquipo coreDaoEquipo;


    public Domain.SimulacionReply getSimulacion(Domain.IdElementoConRutReq idElementoConRutReq, StreamObserver<Domain.SimulacionReply> responseObserver){
        // idElementoConRutReq => id (long) de la simulacion y rut del operador
        Simulacion simulacionGuardada = coreDaoSimulacion.getSimulacion(idElementoConRutReq.getId());
        if (simulacionGuardada == null) {
            return Domain.SimulacionReply.newBuilder().build();
        }

        Domain.IdElementoConRutReq idEquipoConRut = Domain.IdElementoConRutReq.newBuilder()
                .setRut(idElementoConRutReq.getRut()).setId(simulacionGuardada.getIdEquipo()).build();

        Equipo equipoAsociado = coreDaoEquipo.getEquipo(idEquipoConRut);
        Domain.SimulacionReply simulacionRetornar = Domain.SimulacionReply.newBuilder()
                .setId(simulacionGuardada.getId())
                .setNombre(simulacionGuardada.getNombre())
                .setDescripcion(simulacionGuardada.getDescripcion())
                .setNombreEquipo(equipoAsociado.getNombre())
                .setDescripcionEquipo(equipoAsociado.getDescripcion())
                .build();

        return simulacionRetornar;
    }

    public Domain.SimulacionesReply getSimulaciones(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.SimulacionesReply> responseObserver){
        List<Simulacion> listaSimuGuardadas = coreDaoSimulacion.getSimulaciones();
        Domain.SimulacionesReply.Builder listaRetornar = Domain.SimulacionesReply.newBuilder();

        if (listaSimuGuardadas == null) {
            return listaRetornar.build();
        }

        for (Simulacion simulacion : listaSimuGuardadas) {
            Domain.IdElementoConRutReq idEquipoConRut = Domain.IdElementoConRutReq.newBuilder().setRut(rutEntityReq.getRut()).setId(simulacion.getIdEquipo()).build();
            Equipo equipoAsociado = coreDaoEquipo.getEquipo(idEquipoConRut);

            Domain.SimulacionAcotada simuRetornar = Domain.SimulacionAcotada.newBuilder()
                    .setId(simulacion.getId())
                    .setNombre(simulacion.getNombre())
                    .setNombreEquipo(equipoAsociado.getNombre())
                    .build();

            listaRetornar.addSimulacionAcotada(simuRetornar);
        }
        return listaRetornar.build();
    }

    // rpc addSecuencias(SecuenciasReq)  returns (MensajeReply){}
    public Domain.MensajeReply addSimulacion(Domain.SimulacionReq simulacionReq, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeResultado = coreDaoSimulacion.addSimulacion(simulacionReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
    }

    public Domain.MensajeReply startSimulacion(Domain.StartSimulacionReq startSimulacionReq, StreamObserver<Domain.MensajeReply> responseObserver){
        log.info("startSimulacionReq = " + startSimulacionReq);
        String mensajeResultado = coreDaoSimulacion.startSimulacion(startSimulacionReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
    }
}