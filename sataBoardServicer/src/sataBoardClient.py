from __future__ import print_function

import logging
import random
from math import floor
from dotenv import dotenv_values
from datetime import datetime

import grpc
import coreBoardCommuService_pb2 as ReqResModule
import coreBoardCommuService_pb2_grpc as ClientServerModule

venv_dict = dict(dotenv_values(".env"))


class SataBoardClient:

    def __init__(self):
        self.channel = grpc.insecure_channel(f'{venv_dict["CENTRAL_CORE_ADDRESS"]}:{venv_dict["CENTRAL_CORE_PORT"]}')
        self.stub = ClientServerModule.CoreBoardCommuServiceStub(self.channel)
        print(f'Servidor Central Core a alcanzar: {venv_dict["CENTRAL_CORE_ADDRESS"]}:{venv_dict["CENTRAL_CORE_PORT"]}')

    # ESTA FUNCION BUSCA CREAR UN JSON DEL EQUIPO PARA ENTREGARSELO A ARDUINO
    def _getHandyEquipoDict(self, equipoProtobuf):
        for key in equipoProtobuf.componente:
            pass
        return {}

    def sendHelloWorldToCentralCore(self):
        try:
            nombreEquipo = venv_dict["NOMBRE_EQUIPO"]
            direccionIpEquipo = f'{venv_dict["BOARD_ADDRESS"]}:{venv_dict["BOARD_PORT"]}'
            serverResponse = self.stub.sendMensajeEncendido(
                ReqResModule.SaludoBoardReq(
                    nombre_equipo = nombreEquipo,
                    direccion_ip_equipo = direccionIpEquipo
            ))
            print("Mensaje saludo ya enviado al Central Core")
            print(f"Mensaje recibido desde Central Core: {serverResponse.respuestaSaludo}")
            # self._getHandyEquipoDict(serverResponse.equipo)
            return {}
        except Exception as e:
            print("ERROR AL ENVIAR EL SALUDO. NO HUBO RESPUESTA\n")
            print(e)
            return None


    def sendCaudalToCentralCore(self, caudalArduino):
        try:
            now = datetime.now()
            horaCaudal = ":".join(str(now.time()).split(":")[0:2])
            nombreEquipo = venv_dict["NOMBRE_EQUIPO"]
            serverResponse = self.stub.sendLecturasSensores(
            ReqResModule.LecturaSensoresReq(
                caudal = caudalArduino,
                hora = horaCaudal,
                nombreEquipo = nombreEquipo
            ))
            print("Caudal enviado al Central Core")

        except Exception as e:
            print("ERROR AL ENVIAR CAUDAL\n")
            print(e)
            return None 

    def sendAvisoTerminoToCentralCore(self, volumenTotal):
        try:
            nombreEquipo = venv_dict["NOMBRE_EQUIPO"]
            serverResponse = self.stub.sendMensajeTerminoEjecucion(
            ReqResModule.AvisoTerminoEjecucionReq(
                agua_caida = volumenTotal,
                nombreEquipo = nombreEquipo
            ))
            print("Aviso termino enviado al Central Core")

        except Exception as e:
            print("ERROR AL ENVIAR EL AVISO DE TERMINO\n")
            print(e)
            return None             
        
