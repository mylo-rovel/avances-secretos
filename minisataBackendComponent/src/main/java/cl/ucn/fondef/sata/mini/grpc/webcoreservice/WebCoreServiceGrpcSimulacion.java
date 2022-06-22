package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebCoreServiceGrpcSimulacion {

    @Autowired
    private CoreDaoSimulacion coreDaoSimulacion;

    @Autowired
    private CoreDaoEquipo coreDaoEquipo;

    public Domain.SimulacionReply getSimulacion(Domain.IdElementoReq idElemento, StreamObserver<Domain.SimulacionReply> responseObserver){
        // Obtener la simulacion desde la base de datos
        Simulacion simulacionGuardada = coreDaoSimulacion.getSimulacion(idElemento);

        Domain.IdElementoReq idEquipo = Domain.IdElementoReq.newBuilder().setId(simulacionGuardada.getId()).build();
        Equipo equipoAsociado = coreDaoEquipo.getEquipo(idEquipo);

        Domain.SimulacionReply simulacionRetornar = Domain.SimulacionReply.newBuilder()
                .setId(simulacionGuardada.getId())
                .setNombre(simulacionGuardada.getNombre())
                .setDescripcion(simulacionGuardada.getDescripcion())
                .setNombreEquipo(equipoAsociado.getNombre())
                .setDescripcionEquipo(equipoAsociado.getDescripcion())
                .setFechaEjecucion(simulacionGuardada.getFechaEjecucion())
//                .setSecuencia()
                .setAguaCaida(simulacionGuardada.getAguaCaida())
                .build();

        return simulacionRetornar;
    }

    public Domain.SimulacionesReply getSimulaciones(Domain.EmptyReq emptyReq, StreamObserver<Domain.SimulacionesReply> responseObserver){
        List<Simulacion> listaSimuGuardadas = coreDaoSimulacion.getSimulaciones();
        Domain.SimulacionesReply.Builder listaRetornar = Domain.SimulacionesReply.newBuilder();

        for (Simulacion simulacion : listaSimuGuardadas) {
            Domain.IdElementoReq idEquipo = Domain.IdElementoReq.newBuilder().setId(simulacion.getIdEquipo()).build();
            Equipo equipoAsociado = coreDaoEquipo.getEquipo(idEquipo);

            Domain.SimulacionAcotada simuRetornar = Domain.SimulacionAcotada.newBuilder()
                    .setNombre(simulacion.getNombre())
                    .setNombreEquipo(equipoAsociado.getNombre())
                    .setFechaEjecucion(equipoAsociado.getDescripcion())
                    .setAguaCaida(simulacion.getAguaCaida())
                    .build();

            listaRetornar.addSimulacionAcotada(simuRetornar);
        }
        return listaRetornar.build();
    }
}
