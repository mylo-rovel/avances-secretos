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

class CoreBoardCommuServiceServicer(ClientServerModule.CoreBoardCommuServiceServicer):
    """Provides methods that implement functionality of testing server."""

    # def __init__(self):
    #     self.db = route_guide_resources.read_route_guide_database()

    # rpc startSimulacion(SimulacionBoardReq) returns (MensajeReply){}
    def startSimulacion(self, request, context):    
        # message SimulacionBoardReq {
        #    int64 id_simulacion = 1;
        #     repeated Secuencia secuencia = 3;
        # }
        infoRecibida = request
        responseMessage = "IT WOOOORKS"

        print(f'The received message is {infoRecibida} and the context is {context}')
        print(f'We are sending back this {responseMessage} \n')
        
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
