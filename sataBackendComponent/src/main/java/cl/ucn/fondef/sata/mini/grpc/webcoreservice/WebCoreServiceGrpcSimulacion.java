package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.utilities.InformacionBoard;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Web core service grpc simulacion.
 */
@Slf4j
@Service
public class WebCoreServiceGrpcSimulacion {

    /**
     * The Ejecuciones equipo.
     */
    public final HashMap<String, InformacionBoard> ejecucionesEquipo = new HashMap<>();

    @Autowired
    private CoreDaoSimulacion coreDaoSimulacion;

    @Autowired
    private CoreDaoEquipo coreDaoEquipo;

    @Autowired
    private WebCoreServiceGrpcEquipo webCoreServiceGrpcEquipo;

    private Equipo getEquipoAsociadoASimulacion(Domain.IdElementoConRutReq idElementoConRutReq, Simulacion simulacionGuardada) {
        Domain.IdElementoConRutReq idEquipoConRut = Domain.IdElementoConRutReq.newBuilder()
                .setRut(idElementoConRutReq.getRut()).setId(simulacionGuardada.getId_equipo()).build();
        return coreDaoEquipo.getEquipo(idEquipoConRut);
    }

    private void attachDataToSimulacionReply(Domain.SimulacionReply.Builder simulacionRetornar,
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

    /**
     * Get simulacion domain . simulacion reply.
     *
     * @param idElementoConRutReq the id elemento con rut req
     * @param responseObserver    the response observer
     * @return the domain . simulacion reply
     */
    public Domain.SimulacionReply getSimulacion(Domain.IdElementoConRutReq idElementoConRutReq, StreamObserver<Domain.SimulacionReply> responseObserver) {
        // idElementoConRutReq => id (long) de la simulacion y rut del operador
        Domain.IdElementoReq idSimulacionReq = Domain.IdElementoReq.newBuilder().setId(idElementoConRutReq.getId()).build();
        Simulacion simulacionGuardada = coreDaoSimulacion.getSimulacionDB(idSimulacionReq);
        if (simulacionGuardada == null) {
            return Domain.SimulacionReply.newBuilder().build();
        }

        List<Domain.Secuencia> secuenciasGrpcEnviar = coreDaoSimulacion.getGrpcSecuenciasSimulacion(idSimulacionReq);
        Equipo equipoAsociado = this.getEquipoAsociadoASimulacion(idElementoConRutReq, simulacionGuardada);

        if (secuenciasGrpcEnviar == null || equipoAsociado == null) {
            return Domain.SimulacionReply.newBuilder().build();
        }

        Domain.SimulacionReply.Builder simulacionRetornar = Domain.SimulacionReply.newBuilder();
        this.attachDataToSimulacionReply(simulacionRetornar, simulacionGuardada, equipoAsociado, secuenciasGrpcEnviar);

        return simulacionRetornar.build();
    }


    /**
     * Get simulaciones domain . simulaciones reply.
     *
     * @param rutEntityReq     the rut entity req
     * @param responseObserver the response observer
     * @return the domain . simulaciones reply
     */
    public Domain.SimulacionesReply getSimulaciones(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.SimulacionesReply> responseObserver) {
        List<Simulacion> listaSimuGuardadas = coreDaoSimulacion.getSimulaciones();
        Domain.SimulacionesReply.Builder listaRetornar = Domain.SimulacionesReply.newBuilder();
        if (listaSimuGuardadas == null) {
            return listaRetornar.build();
        }

        for (Simulacion simulacion : listaSimuGuardadas) {
            Domain.IdElementoConRutReq idEquipoConRut = Domain.IdElementoConRutReq.newBuilder().setRut(rutEntityReq.getRut()).setId(simulacion.getId_equipo()).build();
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


    /**
     * Add simulacion domain . mensaje reply.
     *
     * @param simulacionReq    the simulacion req
     * @param responseObserver the response observer
     * @return the domain . mensaje reply
     */
// rpc addSecuencias(SecuenciasReq)  returns (MensajeReply){}
    public Domain.MensajeReply addSimulacion(Domain.SimulacionReq simulacionReq, StreamObserver<Domain.MensajeReply> responseObserver) {
        String mensajeResultado = coreDaoSimulacion.addSimulacion(simulacionReq);
        return Domain.MensajeReply.newBuilder().setMensajeTexto(mensajeResultado).build();
    }


    /**
     * Gets ejecucion.
     *
     * @param idEjecucionConRutReq the id elemento con rut req
     * @param responseObserver     the response observer
     * @return the ejecucion
     */
//    rpc getEjecucion(IdElementoReq) returns (EjecucionReply){}
    public Domain.EjecucionReply getEjecucion(Domain.IdElementoConRutReq idEjecucionConRutReq, StreamObserver<Domain.EjecucionReply> responseObserver) {
        Domain.IdElementoReq idEjecucionReq = Domain.IdElementoReq.newBuilder().setId(idEjecucionConRutReq.getId()).build();
        Ejecucion ejecucionDB = coreDaoSimulacion.getEjecucionDB(idEjecucionReq);
        if (ejecucionDB == null) {
            log.warn("Ejecucion de id " + idEjecucionReq.getId() + " no existe");
            return Domain.EjecucionReply.newBuilder().build();
        }

        Domain.IdElementoReq idSimulacionReq = Domain.IdElementoReq.newBuilder().setId(ejecucionDB.getIdSimulacion()).build();
        Simulacion simulacionDB = coreDaoSimulacion.getSimulacionDB(idSimulacionReq);
        if (simulacionDB == null) {
            log.warn("Simulacion de id " + idSimulacionReq.getId() + " no existe");
            return Domain.EjecucionReply.newBuilder().build();
        }

        List<Domain.Secuencia> listaSecuenciasGrpc = coreDaoSimulacion.getGrpcSecuenciasSimulacion(idSimulacionReq);
        Domain.IdElementoConRutReq idSimulacionConRutReq = Domain.IdElementoConRutReq.newBuilder()
                .setId(simulacionDB.getId_equipo()).setRut(idEjecucionConRutReq.getRut()).build();
        Equipo equipoDB = coreDaoEquipo.getEquipo(idSimulacionConRutReq);

        return Domain.EjecucionReply.newBuilder()
                .setId(ejecucionDB.getId())
                .setNombre(simulacionDB.getNombre())
                .setDescripcion(simulacionDB.getDescripcion())
                .setNombreEquipo(equipoDB.getNombre())
                .setDescripcionEquipo(equipoDB.getDescripcion())
                .setFechaEjecucion(ejecucionDB.getFechaEjecucion())
                .addAllSecuencia(listaSecuenciasGrpc)
                .build();
    }


    private void attachDataToEjecucionesAcotadas(Domain.EjecucionesReply.Builder listaAcotadaEjecuciones,
                                                 List<Ejecucion> listaEjecuciones, Domain.RutEntityReq rutEntityReq) {
        for (int i = 0; i < listaEjecuciones.size(); i++) {
            Domain.IdElementoReq idSimulacionReq = Domain.IdElementoReq.newBuilder()
                    .setId(listaEjecuciones.get(i).getIdSimulacion()).build();
            Simulacion simulacionEjecutada = coreDaoSimulacion.getSimulacionDB(idSimulacionReq);

            Domain.IdElementoConRutReq idSimulacionConRutReq = Domain.IdElementoConRutReq.newBuilder()
                    .setId(simulacionEjecutada.getId_equipo()).setRut(rutEntityReq.getRut()).build();
            Equipo equipoUsado = coreDaoEquipo.getEquipo(idSimulacionConRutReq);

            Domain.EjecucionAcotada ejecucionAcotada = Domain.EjecucionAcotada.newBuilder()
                    .setId(listaEjecuciones.get(i).getId())
                    .setNombreSimulacion(simulacionEjecutada.getNombre())
                    .setNombreEquipo(equipoUsado.getNombre())
                    .setFechaEjecucion(listaEjecuciones.get(i).getFechaEjecucion())
                    .build();

            listaAcotadaEjecuciones.addEjecucionAcotada(ejecucionAcotada);
        }
    }

    /**
     * Gets ejecuciones.
     *
     * @param rutEntityReq     the rut entity req
     * @param responseObserver the response observer
     * @return the ejecuciones
     */
//    rpc getEjecuciones(EmptyReq) returns (EjecucionesReply){}
    public Domain.EjecucionesReply getEjecuciones(Domain.RutEntityReq rutEntityReq, StreamObserver<Domain.EjecucionesReply> responseObserver) {
        List<Ejecucion> listaEjecuciones = coreDaoSimulacion.getEjecucionesDB();
        Domain.EjecucionesReply.Builder listaAcotadaEjecuciones = Domain.EjecucionesReply.newBuilder();
        this.attachDataToEjecucionesAcotadas(listaAcotadaEjecuciones, listaEjecuciones, rutEntityReq);
        return listaAcotadaEjecuciones.build();
    }


    /**
     * Start simulacion domain . mensaje reply.
     *
     * @param startSimulacionReq the start simulacion req
     * @param responseObserver   the response observer
     * @return the domain . mensaje reply
     */
    public Domain.MensajeReply startSimulacion(Domain.StartSimulacionReq startSimulacionReq, StreamObserver<Domain.MensajeReply> responseObserver) {
        // le pasamos el hashmap ejecucionesEquipo a la funcion para que podamos recuperar de la estructura
        // el objeto de informacion de cierto equipo en segun del nombre enviado por el frontend
        // el objeto que hace las llamads grpc hacia cierta placa que corre un servidor grpc está dentro
        // del objeto que constituye el valor del par llave valor: nombreEquipo, InformacionBoard
        String mensajeResultado = coreDaoSimulacion.startSimulacion(startSimulacionReq, ejecucionesEquipo);
        return Domain.MensajeReply.newBuilder().setMensajeTexto(mensajeResultado).build();
    }

    // SI PUDIMOS ENVIAR ESTA PETICIÓN DESDE EL RASPBERRY, ENTONCES EL EQUIPO YA ESTÁ
    // DENTRO DEL HASHMAP DE EQUIPOS ENCENDIDOS
    public Domain.EmptyReq sendLecturasSensores(Domain.LecturaSensoresReq lecturaSensoresReq, StreamObserver<Domain.EmptyReq> responseObserver) {
        // SEGURIDAD POR SI EL SERVER SE REINICIA MIENTRAS QUE LOS EQUIPOS ESTÁN VIVOS
        if (!(ejecucionesEquipo.containsKey(lecturaSensoresReq.getNombreEquipo()))) {
            return Domain.EmptyReq.newBuilder().build();
        }

        log.info("LEYENDO SENSORES DEL EQUIPO " + lecturaSensoresReq.getNombreEquipo() + ": "
                + lecturaSensoresReq.getCaudal() + "#" + lecturaSensoresReq.getHora());

        // CODIGO ANTIGUO => LOS DATOS SE GUARDABAN EN MEMORIA
        //InformacionBoard equipoSimulando = ejecucionesEquipo.get(lecturaSensoresReq.getNombreEquipo());
        //quipoSimulando.getValoresGrafico().add(lecturaSensoresReq.getCaudal() + "#" + lecturaSensoresReq.getHora());
        //log.info("Cantidad lecturas: " + equipoSimulando.getValoresGrafico().size());

        // AHORA QUEREMOS GUARDAR EN LA BASE DE DATOS
        var mensajeRespuesta = coreDaoSimulacion.storeDatosLecturaArduino(
                lecturaSensoresReq.getCaudal(),
                lecturaSensoresReq.getTemperatura(),
                lecturaSensoresReq.getHumedad(),
                lecturaSensoresReq.getPresion(),
                lecturaSensoresReq.getPluviometro()
        );
        log.info(mensajeRespuesta);
        return Domain.EmptyReq.newBuilder().build();
    }

    /**
     * Send mensaje encendido domain . saludo board reply.
     *
     * @param saludoBoardReq   the saludo board req
     * @param responseObserver the response observer
     * @return the domain . saludo board reply
     */
// --------------- RESPUESTA A LA LLAMADA 'sendMensajeEncendido(SaludoBoardReq)' -------------
    // ESTA FUNCION ES EJECUTADA DESDE EL 'CoreBoardServiceGrpcImpl'
    // cuando el raspberry es enchufado, envía una llamada al central core y el 'CoreBoardServiceGrpcImpl'
    // responde esas peticiones (es el encargado de la comunicacion CentralCore <--> Raspberry)
    // "ejecucionesEquipo" es una estructura que guarda los equipos que están encendidos en forma de
    // objetos de tipo "InformacionBoard" (disponible en el paquete 'utilities'
    public Domain.SaludoBoardReply sendMensajeEncendido(Domain.SaludoBoardReq saludoBoardReq, StreamObserver<Domain.SaludoBoardReply> responseObserver) {
        //aqui se deberia actualizar el HashMap para que ahora tenga el nombre del equipo y un nuevo InformacionBoard
        //que sera construido en esta funcion
        // BUSCAMOS EL EQUIPO PARA QUE EL RASPBERRY OBTENGA LA CONFIGURACION GUARDADA EN LA DB
        Equipo equipoGuardadoDB = coreDaoEquipo.getEquipoPorNombre(saludoBoardReq.getNombreEquipo());
        if (equipoGuardadoDB == null) {
            log.warn("El equipo no existe en la DB");
            return Domain.SaludoBoardReply.newBuilder().setRespuestaSaludo("Equipo no existente").build();
        }
        log.info("EQUIPO ENCENDIDO");
        log.info("equipo = " + equipoGuardadoDB);

        // SI NO ESTA DENTRO DEL HASHMAP DE EQUIPOS DISPONIBLES, LO AGREGAMOS
        if (!(ejecucionesEquipo.containsKey(saludoBoardReq.getNombreEquipo()))) {
            InformacionBoard informacionBoardNueva = new InformacionBoard(saludoBoardReq.getDireccionIpEquipo());
            ejecucionesEquipo.put(equipoGuardadoDB.getNombre(), informacionBoardNueva);
        }
        // Y SI ESTÁ, ACTUALIZAMOS SU IP POR CAMBIA SU VALOR EN EL ARCHIVO .env
        else {
            InformacionBoard entradaEquipoOLD = ejecucionesEquipo.get(equipoGuardadoDB.getNombre());
            entradaEquipoOLD.resetCoreBoardClient(saludoBoardReq.getDireccionIpEquipo());
            entradaEquipoOLD.setValoresGrafico(new ArrayList<>());
            entradaEquipoOLD.setEstaEjecutandose(false);
        }

        //aqui esta el proceso de convertir equipoGuardadoDB a un equipoEntity para enviarlo
        Domain.EquipoEntity.Builder equipoEnte = webCoreServiceGrpcEquipo.getEquipoEntityBuilder(equipoGuardadoDB);
        Domain.IdElementoReq idEquipoReq = Domain.IdElementoReq.newBuilder().setId(equipoGuardadoDB.getId()).build();
        webCoreServiceGrpcEquipo.addPlacasToEquipo(equipoEnte, idEquipoReq);
        webCoreServiceGrpcEquipo.addComponentesYPinesToEquipo(equipoEnte, idEquipoReq);

        return Domain.SaludoBoardReply.newBuilder()
                .setRespuestaSaludo("EXITO EN LA OPERACION")
                .setEquipo(equipoEnte.build())
                .build();
    }

    // SI PUDIMOS ENVIAR ESTA PETICIÓN DESDE EL RASPBERRY, ENTONCES EL EQUIPO YA ESTÁ
    // DENTRO DEL HASHMAP DE EQUIPOS ENCENDIDOS
    public Domain.EmptyReq sendMensajeTerminoEjecucion(Domain.AvisoTerminoEjecucionReq avisoTerminoEjecucionReq,
                                                       StreamObserver<Domain.EmptyReq> responseObserver) {
        if (!(ejecucionesEquipo.containsKey(avisoTerminoEjecucionReq.getNombreEquipo()))) {
            return Domain.EmptyReq.newBuilder().build();
        }
        InformacionBoard equipoEjecutandose = ejecucionesEquipo.get(avisoTerminoEjecucionReq.getNombreEquipo());
        equipoEjecutandose.setEstaEjecutandose(false);

        log.info("EJECUCION FINALIZADA");
        log.info("Equipo = " + avisoTerminoEjecucionReq.getNombreEquipo());
        return Domain.EmptyReq.newBuilder().build();
    }

    /**
     * Get equipos trabajando domain . equipos entity reply.
     *
     * @param emptyReq       the empty req
     * @param streamObserver the stream observer
     * @return the domain . equipos entity reply
     */
    public Domain.EquiposEntityReply getEquiposTrabajando(Domain.EmptyReq emptyReq, StreamObserver<Domain.EquiposEntityReply> streamObserver) {

        //construir un equiposEntityReply, utilizar la funcion setKey de ejecucionesEquipo para retornar todos los nombres de los
        //equipos del hashmap
        Domain.EquiposEntityReply.Builder equiposEnviar = Domain.EquiposEntityReply.newBuilder();
        for (String nombreEquipo : ejecucionesEquipo.keySet()) {
            if (ejecucionesEquipo.get(nombreEquipo).isEstaEjecutandose()) {
                Domain.EquipoEntityAcotado equipoAgregar = Domain.EquipoEntityAcotado.newBuilder()
                        .setNombre(nombreEquipo)
                        .build();
                equiposEnviar.addEquipoAcotado(equipoAgregar);
            }
        }
        return equiposEnviar.build();
    }


    public Domain.LecturaSensoresReply getValoresGrafico(Domain.EstadoGraficoUsuarioReq estadoGraficoUsuarioReq, StreamObserver<Domain.LecturaSensoresReply> responseObserver) {
        String nombreEquipo = estadoGraficoUsuarioReq.getNombreEquipo();

        if (!(ejecucionesEquipo.containsKey(nombreEquipo))) {
            return Domain.LecturaSensoresReply.newBuilder().build();
        }
        InformacionBoard equipoEjecutandose = ejecucionesEquipo.get(nombreEquipo);
        List<String> listaValoresGrafico = equipoEjecutandose.getValoresGrafico();

        int indiceInicial = (estadoGraficoUsuarioReq.getIndiceInicial() > listaValoresGrafico.size())
                ? listaValoresGrafico.size() - 1 : estadoGraficoUsuarioReq.getIndiceInicial();

        int indiceFinal = (estadoGraficoUsuarioReq.getIndiceFinal() > listaValoresGrafico.size())
                ? listaValoresGrafico.size() : estadoGraficoUsuarioReq.getIndiceFinal();

        return Domain.LecturaSensoresReply.newBuilder()
                .addAllCaudalTiempo(listaValoresGrafico.subList(indiceInicial, indiceFinal))
                .setListaSize(listaValoresGrafico.size()).build();
    }
    // Nuevas funciones para Capstone 2022-2
    //1 Obtener Lista de sensores y cantidad de los mismos que tiene el equipo.

    public Domain.SensoresEquipoEntityReply getSensores(Domain.SensoresEquipoEntityReq sensoresEquipoEntityReq, StreamObserver<Domain.SensoresEquipoEntityReply> responseObserver) {
        List<Componente> listaSensores = coreDaoEquipo.getSensoresDB(sensoresEquipoEntityReq.getIdEquipo());
        Domain.SensoresEquipoEntityReply.Builder reply = Domain.SensoresEquipoEntityReply.newBuilder();
        listaSensores.forEach(componente -> reply.addNombreSensor(componente.getNombre()));
        listaSensores.forEach(componente -> reply.addTipoSensor(componente.getTipo()));
        reply.setIdEquipo(sensoresEquipoEntityReq.getIdEquipo());
        return reply.build();
    }
    public Domain.SimulacionesEquipoMesReply getSimulacionesEjecutadas(Domain.DatosSimulacionesEquipoMesReq datosSimulacionesEquipoMesReq, StreamObserver<Domain.SimulacionesEquipoMesReply> responseObserver) {
        List<Ejecucion> listaSimulacionesMes = coreDaoSimulacion.getSimulacionesEjectuadasDB(datosSimulacionesEquipoMesReq.getIdEquipo(), datosSimulacionesEquipoMesReq.getMes());
        Domain.SimulacionesEquipoMesReply.Builder reply = Domain.SimulacionesEquipoMesReply.newBuilder();
        listaSimulacionesMes.forEach(componente -> {
            Domain.Simulacione simulacione = Domain.Simulacione.newBuilder().setIdEquipo(datosSimulacionesEquipoMesReq.getIdEquipo()).setMes(datosSimulacionesEquipoMesReq.getMes()).build();
            reply.addSimulacione(simulacione);
        });
        return reply.build();

    }
    public Domain.ResumenSimulacionReply getDatosResumen(Domain.DatosSimulacionReq datosSimulacionReq, StreamObserver<Domain.ResumenSimulacionReply> responseObserver) {
        List<Ejecucion> listaDatosResumen = coreDaoSimulacion.getDatosResumenDB(datosSimulacionReq.getIdSimulacion(), datosSimulacionReq.getCaudal(), datosSimulacionReq.getTemperatura(),datosSimulacionReq.getPluviometro(),  datosSimulacionReq.getPresion(),  datosSimulacionReq.getHumedad());
        Domain.ResumenSimulacionReply.Builder reply = Domain.ResumenSimulacionReply.newBuilder();
        reply.setIdSimulacion(datosSimulacionReq.getIdSimulacion());
        reply.setCaudal(datosSimulacionReq.getCaudal());
        reply.setTemperatura(datosSimulacionReq.getTemperatura());
        reply.setPresion(datosSimulacionReq.getPresion());
        reply.setHumedad(datosSimulacionReq.getHumedad());
        return reply.build();
    }
    private String getMedida (Ejecucion mediciones){
        return mediciones.getCaudal()+"#"+ mediciones.getTemperatura()+"#"+mediciones.getPluviometro()+"#"+mediciones.getPresion()+"#"+mediciones.getHumedad();
    }
    public Domain.MedidasEjecucionSensorReply getMedidas (Domain.DatosEjecucionSensorReq datosEjecucionSensorReq, StreamObserver<Domain.MedidasEjecucionSensorReply> responseObserver) {
        List<Ejecucion> listaMedidas = coreDaoSimulacion.getMedidasDB ( datosEjecucionSensorReq.getIdEjecucion(), datosEjecucionSensorReq.getIdSensor());
        Domain.MedidasEjecucionSensorReply.Builder reply = Domain.MedidasEjecucionSensorReply.newBuilder();
        listaMedidas.forEach(componente -> {
            Domain.Medida medidas = Domain.Medida.newBuilder().setIdEjecucion(datosEjecucionSensorReq.getIdEjecucion()).setSensores (this.getMedida(componente)).build();
            reply.addMedida(medidas);
        });
        return reply.build();
    }


    public Domain.UltimaMedidaReply getUltimaMedida(Domain.DatosEjecucionUltimaMedidaReq datosEjecucionUltimaMedidaReq, StreamObserver<Domain.UltimaMedidaReply> responseObserver) {
        var ultimaMedida = coreDaoSimulacion.getUltimaMedidasDB (datosEjecucionUltimaMedidaReq.getIdEjecucion(), datosEjecucionUltimaMedidaReq.getIdSensor());
        Domain.UltimaMedidaReply.Builder reply = Domain.UltimaMedidaReply.newBuilder();
        reply.setIdSensor(datosEjecucionUltimaMedidaReq.getIdSensor());
        reply.setIdEjecucion(datosEjecucionUltimaMedidaReq.getIdEjecucion());
        reply.setUltMedida(ultimaMedida);
        return reply.build();
    }


    public Domain.UltimasMedidasEjecucionReply getUltimasMedidas(Domain.UltimosDatosEjecucionReq ultimosDatosEjecucionReq, StreamObserver<Domain.UltimasMedidasEjecucionReply> responseObserver) {
        System.out.println("AAAAAAAAAA");
        List<Ejecucion> listaUltimasMedidas = coreDaoSimulacion.getUltimasMedidasDB((int) ultimosDatosEjecucionReq.getIdEjecucion(), (int) ultimosDatosEjecucionReq.getIdSensor(), ultimosDatosEjecucionReq.getTimeStamp(), ultimosDatosEjecucionReq.getLastSecond(), ultimosDatosEjecucionReq.getLastEntrities());
        System.out.println("BBBBBBBBBBB");
        Domain.UltimasMedidasEjecucionReply.Builder reply = Domain.UltimasMedidasEjecucionReply.newBuilder();
        reply.setIdEjecucion(ultimosDatosEjecucionReq.getIdEjecucion());
        reply.setIdSensor(ultimosDatosEjecucionReq.getIdSensor());
        reply.setTimeStamp(ultimosDatosEjecucionReq.getTimeStamp());
        reply.setLastSecond(ultimosDatosEjecucionReq.getLastSecond());
        reply.setLastEntrities(ultimosDatosEjecucionReq.getLastEntrities());
        System.out.println("CCCCCCCCC");
        listaUltimasMedidas.forEach(ejecucion -> {
            var ultimasmedidas = ejecucion.getCaudal()+"#"+ ejecucion.getTemperatura()+"#"+ejecucion.getPluviometro()+"#"+ejecucion.getPresion()+"#"+ejecucion.getHumedad();
            reply.addUltMedidas(ultimasmedidas);
        });
        return reply.build();
    }

    public Domain.UmbralesSensorReply getUmbralesPorSensor(Domain.UmbralesSensorReq umbralesSensorReq, StreamObserver<Domain.UmbralesSensorReply> responseObserver) {
        List<Componente> listaUmbralesPorSensor = coreDaoEquipo.getUmbralesPorSensorDB(umbralesSensorReq.getIdSensor());
        Domain.UmbralesSensorReply.Builder reply = Domain.UmbralesSensorReply.newBuilder();
        listaUmbralesPorSensor.forEach(componente -> {
            Domain.Umbrales umbrales = Domain.Umbrales.newBuilder().setIdSensor(umbralesSensorReq.getIdSensor()).build();
            reply.addUmbrales(umbrales);
        });
        return reply.build();

    }
    }



