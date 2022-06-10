package cl.ucn.fondef.sata.mini.grpc;

import org.springframework.stereotype.Service;

@Service
// CLIENTE gRPC "Central Core"
// Esta clase se usa para ENVIAR peticiones desde "Central Core" hasta el RASPBERRY PI
// Usaremos esta clase en el package "grpc". Específicamente en "WebCoreServiceGrpcImpl"
public class CoreRaspiClientGrpcImpl {
    private final String channelTargetCoreToRaspi = "localhost:50051";

    // USAR EL MISMO NOMBRE DE LA FUNCIÓN A LA QUE SE HACE REFERENCIA EN EL ARCHIVO .proto
}
