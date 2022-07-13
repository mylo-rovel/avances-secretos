# Copyright 2015 gRPC authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
"""The Python implementation of the gRPC testing client."""
# I COPIED AND ADAPTED THE ORIGINAL FILE XDD

from __future__ import print_function

import logging
import random
from math import floor
from dotenv import dotenv_values

import grpc
import coreBoardCommuService_pb2 as ReqResModule
import coreBoardCommuService_pb2_grpc as ClientServerModule

venv_dict = dict(dotenv_values(".env"))


class SataBoardClient:
    def __init__(self):
        self.channel = grpc.insecure_channel(f'{venv_dict["CENTRAL_CORE_ADDRESS"]}:{venv_dict["CENTRAL_CORE_PORT"]}')
        self.stub = ClientServerModule.CoreBoardCommuServiceStub(self.channel)
        print(f'Servidor a alcanzar: {venv_dict["CENTRAL_CORE_ADDRESS"]}:{venv_dict["CENTRAL_CORE_PORT"]}')


    def sendHelloWorldToCentralCore(self):
        nombreEquipo = venv_dict["NOMBRE_EQUIPO"]
        direccionIpEquipo = 'f{venv_dict["BOARD_ADDRESS"]}:{venv_dict["BOARD_PORT"]}'
        serverResponse = self.stub.sendMensajeEncendido(
            ReqResModule.SaludoBoardReq(
                nombre_equipo = nombreEquipo,
                direccion_ip_equipo = direccionIpEquipo
        ))
        return


# def run():
#     # NOTE(gRPC Python Team): .close() is possible on a channel and should be
#     # used in circumstances in which the with statement does not fit the needs
#     # of the code.

#     with grpc.insecure_channel(f'{venv_dict["SERVER_ADDRESS"]}:{venv_dict["PORT"]}') as channel:
#         # stub is the client object
#         stub = ClientServerModule.CoreBoardCommuServiceStub(channel)
#         sendHelloWorldToCentralCore(stub)

# if __name__ == '__main__':
#     logging.basicConfig()
#     run()
