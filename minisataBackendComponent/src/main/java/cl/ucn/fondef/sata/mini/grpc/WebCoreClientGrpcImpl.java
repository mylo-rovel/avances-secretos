package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpcobjects.*;
import cl.ucn.fondef.sata.mini.model.Usuario;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import java.util.ArrayList;
import java.util.List;

@Service
// CLIENTE gRPC "Backend Application"
// Esta clase se usa para ENVIAR peticiones desde "Backend Application" hasta el "Central Core"
// Usaremos esta clase en el package "web". Específicamente en "WebController"
public class WebCoreClientGrpcImpl {
    private final String channelTargetWebToCore = "localhost:9090";
    // PROCESO DE ENVÍO DE PETICIÓN GRPC
    // 1ro: Crear el canal de comuncación que apunte a la dirección del servidor RPC
    // (en este caso, el servidor que apuntamos es el servidor rpc "Central Core")
    private final ManagedChannel channel = NettyChannelBuilder.forTarget(this.channelTargetWebToCore).usePlaintext().build();
    // 2do: Crear un stub (o cliente rpc) asociado al canal de comunicación y al servicio gRPC
    private final WebCoreCommuServiceGrpc.WebCoreCommuServiceBlockingStub stub = WebCoreCommuServiceGrpc.newBlockingStub(this.channel);


    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
    public GrpcSesionEntityReply authenticate (GrpcCredencialesEntityReq grpcCredencialesEntityReq) {
        // 3ro: Crear el objeto que será enviado al server RPC "Central Core"
        // Acá le metemos los datos recibidos desde el cliente
        CredencialesEntityReq requestObject = CredencialesEntityReq.newBuilder()
                .setEmail( grpcCredencialesEntityReq.getEmail() )
                .setPassword( grpcCredencialesEntityReq.getPassword() )
                .build();

        // 4to: Enviar la petición RPC y guardar la respuesta
        SesionEntityReply serverResponse = this.stub.authenticate(requestObject);

        // 5to: Crear un objeto que finalmente se transformará en JSON para enviar al browser
        // Esto es importante dado que enviar directamente "serverResponse" arroja errores
        GrpcSesionEntityReply sesion = new GrpcSesionEntityReply();

        sesion.setSesionIniciada( serverResponse.getSesionIniciada() );
        sesion.setJsonWebToken( serverResponse.getJsonWebToken() );

        return sesion;
    }
    public GrpcMensajeReply addUsuario (GrpcUsuarioEntityReq grpcUsuarioEntityReq){

        // CREAR EL OBJETO RPC A ENVIAR
        UsuarioEntity usuarioNuevo = UsuarioEntity.newBuilder()
                .setNombre(grpcUsuarioEntityReq.getUsuario().getNombre())
                .setApellido(grpcUsuarioEntityReq.getUsuario().getApellido())
                .setRut(grpcUsuarioEntityReq.getUsuario().getRut())
                .setEmail(grpcUsuarioEntityReq.getUsuario().getEmail())
                .setPassword(grpcUsuarioEntityReq.getUsuario().getPassword())
                .setRol(grpcUsuarioEntityReq.getUsuario().getRol())
                .setEstado(grpcUsuarioEntityReq.getUsuario().getEstado())
                .build();

        UsuarioEntityReq requestObject = UsuarioEntityReq.newBuilder()
                .setUsuario(usuarioNuevo)
                .setRutAdministrador(grpcUsuarioEntityReq.getRutAdministrador())
                .build();

        // ENVIAR EL OBJETO RPC Y GUARDAR LA RESPUESTA
        MensajeReply serverResponse = this.stub.addUsuario(requestObject);

        // GUARDARLA EN OTRO OBJETO QUE SE PUEDA VOLVER JSON
        GrpcMensajeReply mensajeRespuesta = new GrpcMensajeReply();
        mensajeRespuesta.setMensajeTexto(serverResponse.getMensajeTexto());

        return mensajeRespuesta;
    }

/*
    public GrpcMensajeReply addEquipo (GrpcEquipoEntityReq equipoNuevo){

        // GUARDAMOS LOS COMPONENTES DE TIPO "GrpcComponenteFisico" RECIBIDOS PARA PODER ITERAR Y CREAR
        // OTRA LISTA DE OBJETOS DE TIPO "ComponenteFisico" LA CUAL ES LA QUE EFECTIVAMENTE SE ENVIARÁ
        List<GrpcComponenteFisico[]> componentesReq = new ArrayList<GrpcComponenteFisico[]>();
        componentesReq.add(equipoNuevo.getEquipo().getListaValvulas());
        componentesReq.add(equipoNuevo.getEquipo().getListaSensores());
        componentesReq.add(equipoNuevo.getEquipo().getListaCamaras());

        List<List<ComponenteFisico>> componentesEnviar = new ArrayList<>();

        // TODO: set valores del enum
        for (int i = 0; i < componentesReq.size(); i ++){
            List<ComponenteFisico> listaAux = new ArrayList<ComponenteFisico>();
            for (int j = 0; j < componentesReq.get(i).length; j ++){
                ComponenteFisico compGrpcGuardar = ComponenteFisico.newBuilder()
                        .setNombre(componentesReq.get(i)[j].getNombre())
                        .setDescripcion(componentesReq.get(i)[j].getDescripcion())
                        .setPin(componentesReq.get(i)[j].getPin())
                        .build();
                listaAux.add(compGrpcGuardar);
            }
            componentesEnviar.add(listaAux);
        }

        EquipoEntity.Builder requestObject = EquipoEntity.newBuilder()
                .setNombre(equipoNuevo.getEquipo().getNombre())
                .setDescripcion(equipoNuevo.getEquipo().getDescripcion())
                .setEnlaceRepo("www.google.com")
                .setRutConfigurador(equipoNuevo.getEquipo().getRutConfigurador());

        for (int i = 0; i < componentesEnviar.size(); i++) {
            for (int j = 0; j < componentesEnviar.get(i).size(); j++) {
                ComponenteFisico compFisico = componentesEnviar.get(i).get(j);
                switch (i){
                    case 0:
                        requestObject.addValvula(compFisico);
                        break;
                    case 1:
                        requestObject.addSensor(compFisico);
                        break;
                    case 2:
                        requestObject.addCamara(compFisico);
                        break;
                    default:
                        break;
                }
            }
        }
        EquipoEntityReq equipoEntityReq = EquipoEntityReq.newBuilder()
                .setEquipo(requestObject.build())
                .build();

        MensajeReply serverResponse = this.stub.addEquipo(equipoEntityReq);

        GrpcMensajeReply objetoResultadoOperacion = new GrpcMensajeReply();
        objetoResultadoOperacion.setMensajeTexto(serverResponse.getMensajeTexto());
        return objetoResultadoOperacion;
    }


    public GrpcSimulacionesReply getSimulaciones(){
        Empty empty = Empty.newBuilder().build();
        ListaSimulacionesAcotada listaSimulacionesAcotada = this.stub.getSimulaciones(empty);
        List<GrpcSimulacionAcotada> listaRellenar = new ArrayList<>();
        for(SimulacionAcotada  simulacionAcotada : listaSimulacionesAcotada.getSimulacionAcotadaList()){
            GrpcSimulacionAcotada simAcoBuilder = new GrpcSimulacionAcotada();
            simAcoBuilder.setIdSimulacion(
                    Math.toIntExact(
                            simulacionAcotada.getIdSimulacion()
                    )
            );
            simAcoBuilder.setNombreEquipo(simulacionAcotada.getNombreEquipo());
            simAcoBuilder.setFechaSimulacion(simulacionAcotada.getFechaSimulacion());
            simAcoBuilder.setAguaCaida(simulacionAcotada.getAguaCaida());
        }


        GrpcSimulacionesReply listaEnviar = new GrpcSimulacionesReply();
        listaEnviar.setListaSimulacionAcotada(listaRellenar);

        return listaEnviar;
    }

    public borrarLuegoSimuEspecifica getSimulacion(int idElemento){
        IdElemento idElementoReturn = IdElemento.newBuilder().setId(idElemento).build();
        SimulacionEspecifica simulacionEspecifica = this.stub.getSimulacionEspecifica(idElementoReturn);

        borrarLuegoSimuEspecifica borrarLuegoSimuEspecifica = new borrarLuegoSimuEspecifica();
        borrarLuegoSimuEspecifica.setIdSimulacion(simulacionEspecifica.getIdSimulacion());
        borrarLuegoSimuEspecifica.setNombreEquipo(simulacionEspecifica.getNombreEquipo());
        borrarLuegoSimuEspecifica.setDescrEquipo(simulacionEspecifica.getDescripcionEquipo());
        borrarLuegoSimuEspecifica.setFechaSimulacion(simulacionEspecifica.getFechaSimulacion());

        //revisar si esto esta bien
        borrarLuegoSimuEspecifica.setListaSecuencias(borrarLuegoSimuEspecifica.getListaSecuencias());

        borrarLuegoSimuEspecifica.setAguaCaida(simulacionEspecifica.getAguaCaida());

        return borrarLuegoSimuEspecifica;
    }*/
}
