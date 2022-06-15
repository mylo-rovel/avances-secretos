package cl.ucn.fondef.sata.mini.grpc;

import cl.ucn.fondef.sata.mini.grpcobjects.*;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import net.bytebuddy.matcher.FilterableList;
import org.springframework.stereotype.Service;

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
    public GrpcObjetoSesion loginUsuario (GrpcCredenciales grpcCredenciales) {
        // 3ro: Crear el objeto que será enviado al server RPC "Central Core"
        // Acá le metemos los datos recibidos desde el cliente
        Credenciales requestObject = Credenciales.newBuilder()
                .setCorreo( grpcCredenciales.getCorreo() )
                .setContrasena( grpcCredenciales.getContrasena() )
                .build();

        // 4to: Enviar la petición RPC y guardar la respuesta
        ObjetoSesion serverResponse = this.stub.loginUsuario(requestObject);

        // 5to: Crear un objeto que finalmente se transformará en JSON para enviar al browser
        // Esto es importante dado que enviar directamente "serverResponse" arroja errores
        GrpcObjetoSesion objetoSesion = new GrpcObjetoSesion();

        objetoSesion.setSesionIniciada( serverResponse.getSesionIniciada() );
        objetoSesion.setJsonWebToken( serverResponse.getJsonWebToken() );

        return objetoSesion;
    }

    public GrpcMensajeResultadoOperacion agregarUsuario (GrpcUsuarioNuevo grpcUsuarioNuevo){
        Usuario usuarioCrear = Usuario.newBuilder()
                .setRut(grpcUsuarioNuevo.getUsuarioNuevo().getRut())
                .setNombre(grpcUsuarioNuevo.getUsuarioNuevo().getNombre())
                .setApellido(grpcUsuarioNuevo.getUsuarioNuevo().getApellido())
                .setCorreo(grpcUsuarioNuevo.getUsuarioNuevo().getCorreo())
                .setContrasena(grpcUsuarioNuevo.getUsuarioNuevo().getContrasena())
                .setRol(grpcUsuarioNuevo.getUsuarioNuevo().getRol())
                .setEstado(grpcUsuarioNuevo.getUsuarioNuevo().isEstado())
                .build();
        UsuarioNuevo requestObject = UsuarioNuevo.newBuilder()
                .setRutAdministrador(grpcUsuarioNuevo.getRutAdministrador())
                .setUsuarioNuevo(usuarioCrear)
                .build();

        MensajeResultadoOperacion serverResponse = this.stub.agregarUsuario(requestObject);

        GrpcMensajeResultadoOperacion objetoResultadoOperacion = new GrpcMensajeResultadoOperacion();
        objetoResultadoOperacion.setMensajeTexto(serverResponse.getMensajeTexto());

        return objetoResultadoOperacion;
    }

    public GrpcMensajeResultadoOperacion crearEquipo (GrpcEquipo grpcEquipo){

        // GUARDAMOS LOS COMPONENTES DE TIPO "GrpcCompFisico" RECIBIDOS PARA PODER ITERAR Y CREAR
        // OTRA LISTA DE OBJETOS DE TIPO "CompFisico" LA CUAL ES LA QUE EFECTIVAMENTE SE ENVIARÁ
        List<GrpcCompFisico[]> componentesReq = new ArrayList<GrpcCompFisico[]>();
        componentesReq.add(grpcEquipo.getListaValvulas());
        componentesReq.add(grpcEquipo.getListaSensores());
        componentesReq.add(grpcEquipo.getListaCamaras());

        List<List<CompFisico>> componentesEnviar = new ArrayList<>();

        for (int i = 0; i < componentesReq.size(); i ++){
            List<CompFisico> listaAux = new ArrayList<CompFisico>();
            for (int j = 0; j < componentesReq.get(i).length; j ++){
                CompFisico compGrpcGuardar = CompFisico.newBuilder()
                        .setDescripcion(componentesReq.get(i)[j].getDescripcion())
                        .setEstado(componentesReq.get(i)[j].isEstado())
                        .setPin(componentesReq.get(i)[j].getPin())
                        .build();
                listaAux.add(compGrpcGuardar);
            }
            componentesEnviar.add(listaAux);
        }
        System.out.println("componentesEnviar = " + componentesEnviar +'\n');
        System.out.println("valvulas = " + componentesEnviar.get(0) +'\n');
        System.out.println("sensores = " + componentesEnviar.get(1) +'\n');
        System.out.println("camaras  = " + componentesEnviar.get(2) +'\n');


//        Equipo requestObject = Equipo.newBuilder()
//                .setNombre(grpcEquipo.getNombre())
//                .setDescripcion(grpcEquipo.getDescripcion())
//                .setEnlaceRepo(grpcEquipo.getEnlaceRepo())
//                .setEstado(grpcEquipo.isEstado())
//                .setListaValvulas(componentesEnviar[0])
//                .setListaSensores(componentesEnviar[1])
//                .setListaCamaras(componentesEnviar[2])
//                .setRutConfigurador(grpcEquipo.getRutConfigurador())
//                .build();
//
//        MensajeResultadoOperacion serverResponse = this.stub.crearEquipo(requestObject);

        GrpcMensajeResultadoOperacion objetoResultadoOperacion = new GrpcMensajeResultadoOperacion();
        objetoResultadoOperacion.setMensajeTexto("Equipo creado exitosamente");
        return objetoResultadoOperacion;
    }

    public GrpcListaSimulacionesAcotada getSimulaciones(){
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


        GrpcListaSimulacionesAcotada listaEnviar = new GrpcListaSimulacionesAcotada();
        listaEnviar.setListaSimulacionAcotada(listaRellenar);

        return listaEnviar;
    }

    public GrpcSimulacionEspecifica getSimulacionEspecifica(int idElemento){
        IdElemento idElementoReturn = IdElemento.newBuilder().setId(idElemento).build();
        SimulacionEspecifica simulacionEspecifica = this.stub.getSimulacionEspecifica(idElementoReturn);

        GrpcSimulacionEspecifica grpcSimulacionEspecifica = new GrpcSimulacionEspecifica();
        grpcSimulacionEspecifica.setIdSimulacion(simulacionEspecifica.getIdSimulacion());
        grpcSimulacionEspecifica.setNombreEquipo(simulacionEspecifica.getNombreEquipo());
        grpcSimulacionEspecifica.setDescrEquipo(simulacionEspecifica.getDescripcionEquipo());
        grpcSimulacionEspecifica.setFechaSimulacion(simulacionEspecifica.getFechaSimulacion());

        //revisar si esto esta bien
        grpcSimulacionEspecifica.setListaSecuencias(grpcSimulacionEspecifica.getListaSecuencias());

        grpcSimulacionEspecifica.setAguaCaida(simulacionEspecifica.getAguaCaida());

        return grpcSimulacionEspecifica;
    }
}
