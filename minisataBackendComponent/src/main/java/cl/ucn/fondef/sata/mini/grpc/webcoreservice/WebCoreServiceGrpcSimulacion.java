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

/**
 * The type Web core service grpc simulacion.
 */
@Service
public class WebCoreServiceGrpcSimulacion {

    @Autowired
    private CoreDaoSimulacion coreDaoSimulacion;

    @Autowired
    private CoreDaoEquipo coreDaoEquipo;

    /**
     * Get simulacion domain . simulacion reply.
     *
     * @param idElemento       the id elemento
     * @param responseObserver the response observer
     * @return the domain . simulacion reply
     */
    public Domain.SimulacionReply getSimulacion(Domain.IdElementoReq idElemento, StreamObserver<Domain.SimulacionReply> responseObserver){
        // Obtener la simulacion desde la base de datos
        Simulacion simulacionGuardada = coreDaoSimulacion.getSimulacion(idElemento);
        if (simulacionGuardada == null) {
            return Domain.SimulacionReply.newBuilder().build();
        }

        Domain.IdElementoReq idEquipo = Domain.IdElementoReq.newBuilder().setId(simulacionGuardada.getIdEquipo()).build();
        Equipo equipoAsociado = coreDaoEquipo.getEquipo(idEquipo);
        Domain.SimulacionReply simulacionRetornar = Domain.SimulacionReply.newBuilder()
                .setId(simulacionGuardada.getId())
                .setNombre(simulacionGuardada.getNombre())
                .setDescripcion(simulacionGuardada.getDescripcion())
                .setNombreEquipo(equipoAsociado.getNombre())
                .setDescripcionEquipo(equipoAsociado.getDescripcion())
                .setFechaEjecucion(simulacionGuardada.getFechaCreacion())
                .build();

        return simulacionRetornar;
    }

    /**
     * Get simulaciones domain . simulaciones reply.
     *
     * @param emptyReq         the empty req
     * @param responseObserver the response observer
     * @return the domain . simulaciones reply
     */
    public Domain.SimulacionesReply getSimulaciones(Domain.EmptyReq emptyReq, StreamObserver<Domain.SimulacionesReply> responseObserver){
        List<Simulacion> listaSimuGuardadas = coreDaoSimulacion.getSimulaciones();
        Domain.SimulacionesReply.Builder listaRetornar = Domain.SimulacionesReply.newBuilder();

        if (listaSimuGuardadas == null) {
            return listaRetornar.build();
        }

        for (Simulacion simulacion : listaSimuGuardadas) {
            Domain.IdElementoReq idEquipo = Domain.IdElementoReq.newBuilder().setId(simulacion.getIdEquipo()).build();
            Equipo equipoAsociado = coreDaoEquipo.getEquipo(idEquipo);

            Domain.SimulacionAcotada simuRetornar = Domain.SimulacionAcotada.newBuilder()
                    .setId(simulacion.getId())
                    .setNombre(simulacion.getNombre())
                    .setNombreEquipo(equipoAsociado.getNombre())
                    .setFechaEjecucion(equipoAsociado.getDescripcion())
                    .build();

            listaRetornar.addSimulacionAcotada(simuRetornar);
        }
        return listaRetornar.build();
    }

    // rpc addSecuencias(SecuenciasReq)  returns (MensajeReply){}
    public Domain.MensajeReply addSecuencias(Domain.SecuenciasReq secuenciasReq, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeResultado = coreDaoSimulacion.addSecuencias(secuenciasReq);

        Domain.MensajeReply grpcResponse = Domain.MensajeReply.newBuilder()
                .setMensajeTexto(mensajeResultado)
                .build();

        return grpcResponse;
    }
}
