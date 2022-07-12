# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

import coreBoardCommuService_pb2 as coreBoardCommuService__pb2


class CoreBoardCommuServiceStub(object):
    """python3 -m grpc_tools.protoc -I /home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/proto_files --python_out=/home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/ --grpc_python_out=/home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/ /home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/proto_files/coreBoardCommuService.proto

    Estas son las peticiones que se enviaran entre el "Central Core"
    y el Componente Raspi para manejar el hardware
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.startSimulacion = channel.unary_unary(
                '/CoreBoardCommuService/startSimulacion',
                request_serializer=coreBoardCommuService__pb2.SimulacionBoardReq.SerializeToString,
                response_deserializer=coreBoardCommuService__pb2.MensajeReply.FromString,
                )
        self.getSimulacionActual = channel.unary_unary(
                '/CoreBoardCommuService/getSimulacionActual',
                request_serializer=coreBoardCommuService__pb2.EmptyReq.SerializeToString,
                response_deserializer=coreBoardCommuService__pb2.SimulacionReply.FromString,
                )
        self.getLecturasSensores = channel.unary_stream(
                '/CoreBoardCommuService/getLecturasSensores',
                request_serializer=coreBoardCommuService__pb2.EmptyReq.SerializeToString,
                response_deserializer=coreBoardCommuService__pb2.LecturaSensoresReply.FromString,
                )
        self.sendMensajeEncendido = channel.unary_unary(
                '/CoreBoardCommuService/sendMensajeEncendido',
                request_serializer=coreBoardCommuService__pb2.MensajeReq.SerializeToString,
                response_deserializer=coreBoardCommuService__pb2.MensajeReply.FromString,
                )


class CoreBoardCommuServiceServicer(object):
    """python3 -m grpc_tools.protoc -I /home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/proto_files --python_out=/home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/ --grpc_python_out=/home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/ /home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/proto_files/coreBoardCommuService.proto

    Estas son las peticiones que se enviaran entre el "Central Core"
    y el Componente Raspi para manejar el hardware
    """

    def startSimulacion(self, request, context):
        """(1)
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def getSimulacionActual(self, request, context):
        """(2)
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def getLecturasSensores(self, request, context):
        """(3)
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def sendMensajeEncendido(self, request, context):
        """Esta llamada se ejecuta al iniciar el servicio del board(raspberry)
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_CoreBoardCommuServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'startSimulacion': grpc.unary_unary_rpc_method_handler(
                    servicer.startSimulacion,
                    request_deserializer=coreBoardCommuService__pb2.SimulacionBoardReq.FromString,
                    response_serializer=coreBoardCommuService__pb2.MensajeReply.SerializeToString,
            ),
            'getSimulacionActual': grpc.unary_unary_rpc_method_handler(
                    servicer.getSimulacionActual,
                    request_deserializer=coreBoardCommuService__pb2.EmptyReq.FromString,
                    response_serializer=coreBoardCommuService__pb2.SimulacionReply.SerializeToString,
            ),
            'getLecturasSensores': grpc.unary_stream_rpc_method_handler(
                    servicer.getLecturasSensores,
                    request_deserializer=coreBoardCommuService__pb2.EmptyReq.FromString,
                    response_serializer=coreBoardCommuService__pb2.LecturaSensoresReply.SerializeToString,
            ),
            'sendMensajeEncendido': grpc.unary_unary_rpc_method_handler(
                    servicer.sendMensajeEncendido,
                    request_deserializer=coreBoardCommuService__pb2.MensajeReq.FromString,
                    response_serializer=coreBoardCommuService__pb2.MensajeReply.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'CoreBoardCommuService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class CoreBoardCommuService(object):
    """python3 -m grpc_tools.protoc -I /home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/proto_files --python_out=/home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/ --grpc_python_out=/home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/ /home/emilio/Documents/Capstone/Code/avances-secretos/sataBoardServicer/src/proto_files/coreBoardCommuService.proto

    Estas son las peticiones que se enviaran entre el "Central Core"
    y el Componente Raspi para manejar el hardware
    """

    @staticmethod
    def startSimulacion(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/CoreBoardCommuService/startSimulacion',
            coreBoardCommuService__pb2.SimulacionBoardReq.SerializeToString,
            coreBoardCommuService__pb2.MensajeReply.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def getSimulacionActual(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/CoreBoardCommuService/getSimulacionActual',
            coreBoardCommuService__pb2.EmptyReq.SerializeToString,
            coreBoardCommuService__pb2.SimulacionReply.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def getLecturasSensores(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_stream(request, target, '/CoreBoardCommuService/getLecturasSensores',
            coreBoardCommuService__pb2.EmptyReq.SerializeToString,
            coreBoardCommuService__pb2.LecturaSensoresReply.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def sendMensajeEncendido(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/CoreBoardCommuService/sendMensajeEncendido',
            coreBoardCommuService__pb2.MensajeReq.SerializeToString,
            coreBoardCommuService__pb2.MensajeReply.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
