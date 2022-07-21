"""The Python implementation of the gRPC testing server."""

import logging
import datetime
import json
from concurrent import futures
from dotenv import dotenv_values

import grpc
import coreBoardCommuService_pb2 as ReqResModule
import coreBoardCommuService_pb2_grpc as ClientServerModule
from boardArduinoCommunicator import BoardArduinoCommunicator
from sataBoardClient import SataBoardClient

venv_dict = dict(dotenv_values(".env"))

# put here aux functions
print("\n\n\nIniciando servidor")
print(venv_dict)

class CoreBoardCommuServiceServicer(ClientServerModule.CoreBoardCommuServiceServicer):

    def __init__(self):
        pass
        self.sataBoardClient = SataBoardClient()
        self.sataBoardClient.sendHelloWorldToCentralCore()
        # self.boardArduinoCommunicator = BoardArduinoCommunicator(venv_dict["ARDUINO_PORT"])

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
        for secReq in request.secuencia:
            dictSecuencias[str(secReq.id_componente)] = self._getHandyEventsList(secReq.evento)

        secuenciasJson = json.dumps(dictSecuencias)
        # self.boardArduinoCommunicator.enviarDatosToArduino(secuenciasJson);
        
        responseMessage = "Secuencias recibidas"
        return ReqResModule.MensajeReply(
            mensaje_texto = responseMessage
        )

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    ClientServerModule.add_CoreBoardCommuServiceServicer_to_server(CoreBoardCommuServiceServicer(), server)
    server.add_insecure_port(f'[::]:{venv_dict["BOARD_PORT"]}')
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    serve()
