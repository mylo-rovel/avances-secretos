"""The Python implementation of the gRPC testing server."""

import json
import grpc
import logging
import datetime
from threading import Thread
from concurrent import futures
from dotenv import dotenv_values

import coreBoardCommuService_pb2 as ReqResModule
import coreBoardCommuService_pb2_grpc as ClientServerModule
from boardArduinoCommunicator import BoardArduinoCommunicator
from sataBoardClient import SataBoardClient

venv_dict = dict(dotenv_values(".env"))

print("\n\n\nIniciando servidor")
print(venv_dict)

class CoreBoardCommuServiceServicer(ClientServerModule.CoreBoardCommuServiceServicer):

    def __init__(self):
        self.sataBoardClient = SataBoardClient()
        self.sataBoardClient.sendHelloWorldToCentralCore()
        self.boardArduinoCommunicator = BoardArduinoCommunicator(venv_dict["ARDUINO_PORT"])
        # RETORNAR OBJETO EQUIPO DE "sendHelloWorldToCentralCore()" Y ENVIARLO A ARDUINO PARA EL SETUP

    def _getHandyListaEventos(self, eventsListProtobuf):
        listaEventos = []
        for event in eventsListProtobuf:
            eventArr = {"i": event.intensidad, "d": event.duracion}
            listaEventos.append(eventArr)
        return listaEventos

    def _getDictSimulacion(self, dictSecuencias):        
        return {
            "ids": list(dictSecuencias.keys()),
            "secuencias": dictSecuencias
        }

    def _enviarDatosToArduinoThreaded(self, simulacionJson):
        self.boardArduinoCommunicator.enviarDatosToArduino(simulacionJson, self.sataBoardClient)


    # rpc startSimulacion(SimulacionBoardReq) returns (MensajeReply){}
    def startSimulacion(self, request, context):    
        # message SimulacionBoardReq {
        #    int64 id_simulacion = 1;
        #     repeated Secuencia secuencia = 3;
        # }
        dictSecuencias = {}
        for secReq in request.secuencia:
            # CHEQUEAR SI LA ID ES 0 => SI ES ASÍ, NO INCLUIRLA
            if (secReq.id_componente > 0):
                dictSecuencias[str(secReq.id_componente)] = self._getHandyListaEventos(secReq.evento)

        simulacionJson = json.dumps(self._getDictSimulacion(dictSecuencias))
        # print(simulacionJson)

        arduinoThread = Thread(target=self._enviarDatosToArduinoThreaded, args = (simulacionJson, ))
        arduinoThread.start()
        print("Secuencias recibidas\nHilo arduino iniciado")

        responseMessage = "Secuencias recibidas"
        return ReqResModule.MensajeReply( mensaje_texto = responseMessage )

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    ClientServerModule.add_CoreBoardCommuServiceServicer_to_server(CoreBoardCommuServiceServicer(), server)
    server.add_insecure_port(f'[::]:{venv_dict["BOARD_PORT"]}')
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    serve()
