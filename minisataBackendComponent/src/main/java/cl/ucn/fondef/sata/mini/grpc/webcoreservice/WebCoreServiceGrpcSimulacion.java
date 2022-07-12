package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Ejecucion;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.utilities.InformacionEjecucion;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * The type Web core service grpc simulacion.
 */
@Slf4j
@Service
public class WebCoreServiceGrpcSimulacion {

    private final HashMap<String, InformacionEjecucion> ejecucionesEquipo = new HashMap<>();

    @Autowired
    private CoreDaoSimulacion coreDaoSimulacion;

    @Autowired
    private CoreDaoEquipo coreDaoEquipo;

    private Equipo getEquipoAsociadoASimulacion(Domain.IdElementoConRutReq idElementoConRutReq, Simulacion simulacionGuardada) {
        Domain.IdElementoConRutReq idEquipoConRut = Domain.IdElementoConRutReq.newBuilder()
                .setRut(idElementoConRutReq.getRut()).setId(simulacionGuardada.getIdEquipo()).build();
        return coreDaoEquipo.getEquipo(idEquipoConRut);
    }
    private void attachDataToSimulacionReply (Domain.SimulacionReply.Builder simulacionRetornar,
                                              Simulacion simulacionGuardada,
                                              Equipo equipoAsociado,
                                              List<Domain.Secuencia> secuenciasGrpcEnviar) {
        simulacionRetornar.setId(simulacionGuardada.getId())
                .setNombre(simulacionGuardada.getNombre())
                .setDescripcion(simulacionGuardada.getDescripcion())
                .setNombreEquipo(equipoAsociado.getNombre())
                .setDescripcionEquipo(equipoAsociado.getDescripcion())
                .addAllSecuencia(secuenciasGrpcEnviar);
    }
    public Domain.SimulacionReply getSimulacion(Domain.IdElementoConRutReq idElementoConRutReq, StreamObserver<Domain.SimulacionReply> responseObserver){
        // idElementoConRutReq => id (long) de la simulacion y rut del operador
        Domain.IdElementoReq idSimulacionReq = Domain.IdElementoReq.newBuilder().setId(idElementoConRutReq.getId()).build();
        Simulacion simulacionGuardada = coreDaoSimulacion.getSimulacionDB(idSimulacionReq);
        if (simulacionGuardada == null) { return Domain.SimulacionReply.newBuilder().build(); }

        List<Domain.Secuencia> secuenciasGrpcEnviar = coreDaoSimulacion.getGrpcSecuenciasSimulacion(idSimulacionReq);
        Equipo equipoAsociado = this.getEquipoAsociadoASimulacion(idElementoConRutReq, simulacionGuardada);

        if (secuenciasGrpcEnviar == null || equipoAsociado == null) { return Domain.SimulacionReply.newBuilder().build(); }

        Domain.SimulacionReply.Builder simulacionRetornar = Domain.SimulacionReply.newBuilder();
        this.attachDataToSimulacionReply(simulacionRetornar, simulacionGuardada, equipoAsociado, secuenciasGrpcEnviar);

        return simulacionRetornar.build();
    }




    public Domain.SimulacionesReply getSimulaciones(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.SimulacionesReply> responseObserver){
        List<Simulacion> listaSimuGuardadas = coreDaoSimulacion.getSimulaciones();
        Domain.SimulacionesReply.Builder listaRetornar = Domain.SimulacionesReply.newBuilder();
        if (listaSimuGuardadas == null) { return listaRetornar.build(); }

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
        return Domain.MensajeReply.newBuilder().setMensajeTexto(mensajeResultado).build();
    }



//    rpc getEjecucion(IdElementoReq) returns (EjecucionReply){}
    public Domain.EjecucionReply getEjecucion(Domain.IdElementoConRutReq idElementoConRutReq, StreamObserver<Domain.EjecucionReply> responseObserver) {
        Domain.IdElementoReq idSimulacionReq = Domain.IdElementoReq.newBuilder().setId(idElementoConRutReq.getId()).build();
        Simulacion simulacionDB = coreDaoSimulacion.getSimulacionDB(idSimulacionReq);
        Ejecucion ejecucionDB = coreDaoSimulacion.getEjecucionDB(idSimulacionReq);
        List<Domain.Secuencia> listaSecuenciasGrpc = coreDaoSimulacion.getGrpcSecuenciasSimulacion(idSimulacionReq);

        Domain.IdElementoConRutReq idSimulacionConRutReq = Domain.IdElementoConRutReq.newBuilder().setId( simulacionDB.getIdEquipo() ).setRut( idElementoConRutReq.getRut() ).build();
        Equipo equipoDB = coreDaoEquipo.getEquipo(idSimulacionConRutReq);

        return Domain.EjecucionReply.newBuilder()
                .setId(ejecucionDB.getId())
                .setNombre(simulacionDB.getNombre())
                .setDescripcion(simulacionDB.getDescripcion())
                .setNombreEquipo(equipoDB.getNombre())
                .setDescripcionEquipo(equipoDB.getDescripcion())
                .setFechaEjecucion(ejecucionDB.getFechaEjecucion())
                .addAllSecuencia(listaSecuenciasGrpc)
                .setAguaCaida(ejecucionDB.getAguaCaida())
                .build();
    }



    private void attachDataToEjecucionesAcotadas(Domain.EjecucionesReply.Builder listaAcotadaEjecuciones,
                                           List<Ejecucion> listaEjecuciones, Domain.RutEntityReq rutEntityReq) {
        for (int i = 0; i < listaEjecuciones.size(); i++) {
            Domain.IdElementoReq idSimulacionReq = Domain.IdElementoReq.newBuilder()
                    .setId( listaEjecuciones.get(i).getIdSimulacion() ).build();
            Simulacion simulacionEjecutada = coreDaoSimulacion.getSimulacionDB(idSimulacionReq);

            Domain.IdElementoConRutReq idSimulacionConRutReq = Domain.IdElementoConRutReq.newBuilder()
                    .setId( simulacionEjecutada.getIdEquipo() ).setRut( rutEntityReq.getRut() ).build();
            Equipo equipoUsado = coreDaoEquipo.getEquipo(idSimulacionConRutReq);

            Domain.EjecucionAcotada ejecucionAcotada = Domain.EjecucionAcotada.newBuilder()
                    .setId(listaEjecuciones.get(i).getId())
                    .setNombreSimulacion(simulacionEjecutada.getNombre())
                    .setNombreEquipo(equipoUsado.getNombre())
                    .setFechaEjecucion(listaEjecuciones.get(i).getFechaEjecucion())
                    .setAguaCaida(listaEjecuciones.get(i).getAguaCaida())
                    .build();

            listaAcotadaEjecuciones.addEjecucionAcotada(ejecucionAcotada);
        }
    }

//    rpc getEjecuciones(EmptyReq) returns (EjecucionesReply){}
    public Domain.EjecucionesReply getEjecuciones(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.EjecucionesReply> responseObserver) {
        List<Ejecucion> listaEjecuciones = coreDaoSimulacion.getEjecucionesDB();
        Domain.EjecucionesReply.Builder listaAcotadaEjecuciones = Domain.EjecucionesReply.newBuilder();
        this.attachDataToEjecucionesAcotadas(listaAcotadaEjecuciones, listaEjecuciones, rutEntityReq);
        return listaAcotadaEjecuciones.build();
    }




    public Domain.MensajeReply startSimulacion(Domain.StartSimulacionReq startSimulacionReq, StreamObserver<Domain.MensajeReply> responseObserver){
        String mensajeResultado = coreDaoSimulacion.startSimulacion(startSimulacionReq, ejecucionesEquipo);
        return Domain.MensajeReply.newBuilder().setMensajeTexto(mensajeResultado).build();
    }
}