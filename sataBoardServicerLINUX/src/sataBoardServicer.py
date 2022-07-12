"""The Python implementation of the gRPC testing server."""

from concurrent import futures
import logging
import datetime
from dotenv import dotenv_values

import grpc
import coreBoardCommuService_pb2 as ReqResModule
import coreBoardCommuService_pb2_grpc as ClientServerModule

venv_dict = dict(dotenv_values(".env"))

# put here aux functions
print("iniciando servidor")
print(venv_dict)

class CoreBoardCommuServiceServicer(ClientServerModule.CoreBoardCommuServiceServicer):
    """Provides methods that implement functionality of testing server."""

    # def __init__(self):
    #     self.db = route_guide_resources.read_route_guide_database()

    # PODRIAMOS CREAR UNA CLASE QUE SE COMUNIQUE CON EL ARDUINO => SERVIRIA COMO PUENTE
    # ENTRE EL RASPI gRPC Y EL ARDUINO

    def _getHandyEventsList(ignorableArg, eventsListProtobuf):
        listaEventos = []
        for event in eventsListProtobuf:
            eventArr = [event.intensidad, event.duracion]
            listaEventos.append(eventArr)
        return listaEventos

    # rpc startSimulacion(SimulacionBoardReq) returns (MensajeReply){}
    def startSimulacion(self, request, context):    
        # message SimulacionBoardReq {
        #    int64 id_simulacion = 1;
        #     repeated Secuencia secuencia = 3;
        # }
        dictSecuencias = {}
        for x in request.secuencia:
            dictSecuencias[str(x.id_componente)] = self._getHandyEventsList(x.evento)
        print(dictSecuencias)

        responseMessage = "IT WOOOORKS"
        return ReqResModule.MensajeReply(
            mensaje_texto = responseMessage
        )

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    ClientServerModule.add_CoreBoardCommuServiceServicer_to_server(CoreBoardCommuServiceServicer(), server)
    server.add_insecure_port(f'[::]:{venv_dict["PORT"]}')
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    serve()