from concurrent import futures
import logging
import datetime
# from dotenv import dotenv_values

import grpc
import node_conn_pb2 as ReqResModule
import node_conn_pb2_grpc as ClientServerModule

from ledRoutines import *

venv_dict = {"PORT": "50051"}

print(f'Iniciando servidor en puerto {venv_dict["PORT"]}')

class LedManipulationServiceServicer(ClientServerModule.LedManipulationServiceServicer):
    counter = -1
    encendidoDeLuces = [
        rutina_secuencia1, rutina_secuencia2, rutina_secuencia3
    ]

    """Provides methods that implement functionality of testing server."""
    def StartLedPerformance(self, request, context):    
        messageReceived = request.message
        responseMessage = ""
        print(f'The received message is {messageReceived}')

        if (messageReceived == "encenderLed"):
            responseMessage = "MENSAJE DESDE PYTHON \nORDEN RECIBIDA. LED PERFORMANCE REALIZADA"
        else:
            responseMessage = "MENSAJE DESDE PYTHON \nERROR. NO SE HA RECIBIDO UNA ORDEN DE ENCENDER LED"
        print(responseMessage)

        self.counter += 1
        indiceFuncion = (self.counter%3)
        funcionLedUsar = self.encendidoDeLuces[indiceFuncion]
        
        # DESCOMENTAR SÓLO SI ESTAMOS USANDO LA RASPI
        funcionLedUsar()

        # SI NO SE RETORNA LA RESPUESTA gRPC, EL PROGRAMÁ ARROJARÁ UN ERROR
        return ReqResModule.TextMessage(
            message = f"{responseMessage}"
        )

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    ClientServerModule.add_LedManipulationServiceServicer_to_server(
        LedManipulationServiceServicer(), server)
    server.add_insecure_port(f'[::]:{venv_dict["PORT"]}')
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    serve()
