# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: coreBoardCommuService.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x1b\x63oreBoardCommuService.proto\"\n\n\x08\x45mptyReq\".\n\x13IdElementoConRutReq\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0b\n\x03rut\x18\x02 \x01(\t\"\x1b\n\rIdElementoReq\x12\n\n\x02id\x18\x01 \x01(\x03\"#\n\nMensajeReq\x12\x15\n\rmensaje_texto\x18\x01 \x01(\t\"%\n\x0cMensajeReply\x12\x15\n\rmensaje_texto\x18\x01 \x01(\t\"8\n\x15\x43redencialesEntityReq\x12\r\n\x05\x65mail\x18\x01 \x01(\t\x12\x10\n\x08password\x18\x02 \x01(\t\"Y\n\x11SesionEntityReply\x12\x17\n\x0fsesion_iniciada\x18\x01 \x01(\x08\x12\x16\n\x0ejson_web_token\x18\x02 \x01(\t\x12\x13\n\x0brol_usuario\x18\x03 \x01(\t\"\x1b\n\x0cRutEntityReq\x12\x0b\n\x03rut\x18\x01 \x01(\t\"\xa1\x02\n\rUsuarioEntity\x12\x0e\n\x06nombre\x18\x01 \x01(\t\x12\x10\n\x08\x61pellido\x18\x02 \x01(\t\x12\x0b\n\x03rut\x18\x03 \x01(\t\x12\r\n\x05\x65mail\x18\x04 \x01(\t\x12\x10\n\x08password\x18\x05 \x01(\t\x12&\n\x03rol\x18\x06 \x01(\x0e\x32\x19.UsuarioEntity.RolUsuario\x12,\n\x06\x65stado\x18\x07 \x01(\x0e\x32\x1c.UsuarioEntity.EstadoUsuario\")\n\rEstadoUsuario\x12\n\n\x06\x41\x43TIVO\x10\x00\x12\x0c\n\x08INACTIVO\x10\x01\"?\n\nRolUsuario\x12\x0c\n\x08OPERADOR\x10\x00\x12\x10\n\x0c\x43ONFIGURADOR\x10\x01\x12\x11\n\rADMINISTRADOR\x10\x02\"N\n\x10UsuarioEntityReq\x12\x19\n\x11rut_administrador\x18\x01 \x01(\t\x12\x1f\n\x07usuario\x18\x02 \x01(\x0b\x32\x0e.UsuarioEntity\"A\n\x12UsuarioEntityReply\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x1f\n\x07usuario\x18\x02 \x01(\x0b\x32\x0e.UsuarioEntity\"6\n\x13UsuariosEntityReply\x12\x1f\n\x07usuario\x18\x01 \x03(\x0b\x32\x0e.UsuarioEntity\"\xdf\x02\n\x08Registro\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x12\n\nid_entidad\x18\x02 \x01(\x03\x12\x11\n\ttimestamp\x18\x03 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x04 \x01(\t\x12$\n\x04tipo\x18\x05 \x01(\x0e\x32\x16.Registro.TipoRegistro\x12\x1f\n\x07usuario\x18\x06 \x01(\x0b\x32\x0e.UsuarioEntity\"\xc3\x01\n\x0cTipoRegistro\x12\x14\n\x10\x43REACION_USUARIO\x10\x00\x12\x11\n\rLOGIN_USUARIO\x10\x01\x12\x18\n\x14MODIFICACION_USUARIO\x10\x02\x12\x17\n\x13\x43REACION_SIMULACION\x10\x03\x12\x15\n\x11INICIO_SIMULACION\x10\x04\x12\x12\n\x0eUPLOAD_ARCHIVO\x10\x05\x12\x13\n\x0f\x43REACION_EQUIPO\x10\x06\x12\x17\n\x13MODIFICACION_EQUIPO\x10\x07\"-\n\x0eRegistrosReply\x12\x1b\n\x08registro\x18\x01 \x03(\x0b\x32\t.Registro\"\xbf\x01\n\x03Pin\x12\x0e\n\x06numero\x18\x01 \x01(\x05\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x03 \x01(\t\x12\"\n\x08\x63onexion\x18\x04 \x01(\x0e\x32\x10.Pin.ConexionPin\"_\n\x0b\x43onexionPin\x12\x13\n\x0fINPUT_ANALOGICO\x10\x00\x12\x11\n\rINPUT_DIGITAL\x10\x01\x12\x14\n\x10OUTPUT_ANALOGICO\x10\x02\x12\x12\n\x0eOUTPUT_DIGITAL\x10\x03\"\xf4\x02\n\nComponente\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x03 \x01(\t\x12\x0b\n\x03url\x18\x04 \x01(\t\x12,\n\x06\x65stado\x18\x05 \x01(\x0e\x32\x1c.Componente.EstadoComponente\x12(\n\x04tipo\x18\x06 \x01(\x0e\x32\x1a.Componente.TipoComponente\x12\x1e\n\ntipo_placa\x18\x07 \x01(\x0e\x32\n.TipoPlaca\x12\x1c\n\x0epin_componente\x18\x08 \x03(\x0b\x32\x04.Pin\"G\n\x10\x45stadoComponente\x12\n\n\x06\x41\x43TIVO\x10\x00\x12\x0c\n\x08INACTIVO\x10\x01\x12\t\n\x05\x46\x41LLA\x10\x02\x12\x0e\n\nREPARACION\x10\x03\"I\n\x0eTipoComponente\x12\n\n\x06\x43\x41MARA\x10\x00\x12\x0e\n\nFLUJOMETRO\x10\x01\x12\x0e\n\nTERMOMETRO\x10\x02\x12\x0b\n\x07VALVULA\x10\x03\"F\n\x05Placa\x12\x0e\n\x06nombre\x18\x01 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x02 \x01(\t\x12\x18\n\x04tipo\x18\x03 \x01(\x0e\x32\n.TipoPlaca\"9\n\x16\x43omponentesEquipoReply\x12\x1f\n\ncomponente\x18\x01 \x03(\x0b\x32\x0b.Componente\"\xaf\x01\n\x0c\x45quipoEntity\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x03 \x01(\t\x12\x17\n\x0furl_repositorio\x18\x04 \x01(\t\x12\x1d\n\x06\x65stado\x18\x05 \x01(\x0e\x32\r.EstadoEquipo\x12\x15\n\x05placa\x18\x06 \x03(\x0b\x32\x06.Placa\x12\x1f\n\ncomponente\x18\x07 \x03(\x0b\x32\x0b.Componente\"J\n\x0f\x45quipoEntityReq\x12\x18\n\x10rut_configurador\x18\x01 \x01(\t\x12\x1d\n\x06\x65quipo\x18\x02 \x01(\x0b\x32\r.EquipoEntity\"2\n\x11\x45quipoEntityReply\x12\x1d\n\x06\x65quipo\x18\x01 \x01(\x0b\x32\r.EquipoEntity\"\x9d\x01\n\rArchivoEntity\x12\x15\n\rnombre_equipo\x18\x01 \x01(\t\x12\x0f\n\x07\x61rchivo\x18\x02 \x01(\x0c\x12\x30\n\x0ctipo_archivo\x18\x03 \x01(\x0e\x32\x1a.ArchivoEntity.TipoArchivo\"2\n\x0bTipoArchivo\x12\x07\n\x03PNG\x10\x00\x12\x07\n\x03PDF\x10\x01\x12\x07\n\x03JPG\x10\x02\x12\x08\n\x04JPEG\x10\x03\"3\n\x10\x41rchivoEntityReq\x12\x1f\n\x07\x61rchivo\x18\x01 \x01(\x0b\x32\x0e.ArchivoEntity\"6\n\x13\x41rchivosEntityReply\x12\x1f\n\x07\x61rchivo\x18\x01 \x01(\x0b\x32\x0e.ArchivoEntity\"P\n\x13\x45quipoEntityAcotado\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x1d\n\x06\x65stado\x18\x03 \x01(\x0e\x32\r.EstadoEquipo\"B\n\x12\x45quiposEntityReply\x12,\n\x0e\x65quipo_acotado\x18\x01 \x03(\x0b\x32\x14.EquipoEntityAcotado\"@\n\x06\x45vento\x12\x12\n\nintensidad\x18\x01 \x01(\x05\x12\x10\n\x08\x64uracion\x18\x02 \x01(\x05\x12\x10\n\x08posicion\x18\x03 \x01(\x05\";\n\tSecuencia\x12\x15\n\rid_componente\x18\x01 \x01(\x03\x12\x17\n\x06\x65vento\x18\x02 \x03(\x0b\x32\x07.Evento\"\x80\x01\n\rSimulacionReq\x12\x0e\n\x06nombre\x18\x01 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x02 \x01(\t\x12\x15\n\rnombre_equipo\x18\x03 \x01(\t\x12\x14\n\x0crut_operador\x18\x04 \x01(\t\x12\x1d\n\tsecuencia\x18\x05 \x03(\x0b\x32\n.Secuencia\"@\n\x14SecuenciasComponente\x12(\n\x14secuencia_componente\x18\x01 \x03(\x0b\x32\n.Secuencia\"V\n\x1fSecuenciasComponenteEquipoReply\x12\x33\n\x14secuencia_componente\x18\x01 \x03(\x0b\x32\x15.SecuenciasComponente\"B\n\x12StartSimulacionReq\x12\x15\n\rid_simulacion\x18\x01 \x01(\x03\x12\x15\n\rnombre_equipo\x18\x02 \x01(\t\"J\n\x12SimulacionBoardReq\x12\x15\n\rid_simulacion\x18\x01 \x01(\x03\x12\x1d\n\tsecuencia\x18\x03 \x03(\x0b\x32\n.Secuencia\"\x94\x01\n\x0fSimulacionReply\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x03 \x01(\t\x12\x15\n\rnombre_equipo\x18\x04 \x01(\t\x12\x1a\n\x12\x64\x65scripcion_equipo\x18\x05 \x01(\t\x12\x1d\n\tsecuencia\x18\x06 \x03(\x0b\x32\n.Secuencia\"F\n\x11SimulacionAcotada\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x15\n\rnombre_equipo\x18\x03 \x01(\t\"C\n\x11SimulacionesReply\x12.\n\x12simulacion_acotada\x18\x01 \x03(\x0b\x32\x12.SimulacionAcotada\"\xc0\x01\n\x0e\x45jecucionReply\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x13\n\x0b\x64\x65scripcion\x18\x03 \x01(\t\x12\x15\n\rnombre_equipo\x18\x04 \x01(\t\x12\x1a\n\x12\x64\x65scripcion_equipo\x18\x05 \x01(\t\x12\x17\n\x0f\x66\x65\x63ha_ejecucion\x18\x06 \x01(\t\x12\x1d\n\tsecuencia\x18\x07 \x03(\x0b\x32\n.Secuencia\x12\x12\n\nagua_caida\x18\x08 \x01(\x01\"}\n\x10\x45jecucionAcotada\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x19\n\x11nombre_simulacion\x18\x02 \x01(\t\x12\x15\n\rnombre_equipo\x18\x03 \x01(\t\x12\x17\n\x0f\x66\x65\x63ha_ejecucion\x18\x04 \x01(\t\x12\x12\n\nagua_caida\x18\x05 \x01(\x01\"@\n\x10\x45jecucionesReply\x12,\n\x11\x65jecucion_acotada\x18\x01 \x03(\x0b\x32\x11.EjecucionAcotada\"w\n\x14LecturaSensoresReply\x12\x15\n\rid_simulacion\x18\x01 \x01(\x03\x12\x15\n\rnombre_equipo\x18\x02 \x01(\t\x12\x14\n\x0c\x66lujo_medido\x18\x03 \x01(\x01\x12\x1b\n\x13tiempo_transcurrido\x18\x04 \x01(\x01\"D\n\x0eSaludoBoardReq\x12\x15\n\rnombre_equipo\x18\x01 \x01(\t\x12\x1b\n\x13\x64ireccion_ip_equipo\x18\x02 \x01(\t\"J\n\x10SaludoBoardReply\x12\x1d\n\x06\x65quipo\x18\x01 \x01(\x0b\x32\r.EquipoEntity\x12\x17\n\x0frespuestaSaludo\x18\x02 \x01(\t\"M\n\x18\x41visoTerminoEjecucionReq\x12\x1d\n\x06\x65quipo\x18\x01 \x01(\x0b\x32\r.EquipoEntity\x12\x12\n\nagua_caida\x18\x02 \x01(\x01*)\n\tTipoPlaca\x12\x10\n\x0c\x41RDUINO_2560\x10\x00\x12\n\n\x06\x45SP_32\x10\x01*/\n\x0c\x45stadoEquipo\x12\r\n\tPROTOTIPO\x10\x00\x12\x10\n\x0c\x43ONSTRUCCION\x10\x01\x32\x93\x02\n\x15\x43oreBoardCommuService\x12\x37\n\x0fstartSimulacion\x12\x13.SimulacionBoardReq\x1a\r.MensajeReply\"\x00\x12<\n\x14sendLecturasSensores\x12\x15.LecturaSensoresReply\x1a\t.EmptyReq\"\x00(\x01\x12<\n\x14sendMensajeEncendido\x12\x0f.SaludoBoardReq\x1a\x11.SaludoBoardReply\"\x00\x12\x45\n\x1bsendMensajeTerminoEjecucion\x12\x19.AvisoTerminoEjecucionReq\x1a\t.EmptyReq\"\x00\x62\x06proto3')

_TIPOPLACA = DESCRIPTOR.enum_types_by_name['TipoPlaca']
TipoPlaca = enum_type_wrapper.EnumTypeWrapper(_TIPOPLACA)
_ESTADOEQUIPO = DESCRIPTOR.enum_types_by_name['EstadoEquipo']
EstadoEquipo = enum_type_wrapper.EnumTypeWrapper(_ESTADOEQUIPO)
ARDUINO_2560 = 0
ESP_32 = 1
PROTOTIPO = 0
CONSTRUCCION = 1


_EMPTYREQ = DESCRIPTOR.message_types_by_name['EmptyReq']
_IDELEMENTOCONRUTREQ = DESCRIPTOR.message_types_by_name['IdElementoConRutReq']
_IDELEMENTOREQ = DESCRIPTOR.message_types_by_name['IdElementoReq']
_MENSAJEREQ = DESCRIPTOR.message_types_by_name['MensajeReq']
_MENSAJEREPLY = DESCRIPTOR.message_types_by_name['MensajeReply']
_CREDENCIALESENTITYREQ = DESCRIPTOR.message_types_by_name['CredencialesEntityReq']
_SESIONENTITYREPLY = DESCRIPTOR.message_types_by_name['SesionEntityReply']
_RUTENTITYREQ = DESCRIPTOR.message_types_by_name['RutEntityReq']
_USUARIOENTITY = DESCRIPTOR.message_types_by_name['UsuarioEntity']
_USUARIOENTITYREQ = DESCRIPTOR.message_types_by_name['UsuarioEntityReq']
_USUARIOENTITYREPLY = DESCRIPTOR.message_types_by_name['UsuarioEntityReply']
_USUARIOSENTITYREPLY = DESCRIPTOR.message_types_by_name['UsuariosEntityReply']
_REGISTRO = DESCRIPTOR.message_types_by_name['Registro']
_REGISTROSREPLY = DESCRIPTOR.message_types_by_name['RegistrosReply']
_PIN = DESCRIPTOR.message_types_by_name['Pin']
_COMPONENTE = DESCRIPTOR.message_types_by_name['Componente']
_PLACA = DESCRIPTOR.message_types_by_name['Placa']
_COMPONENTESEQUIPOREPLY = DESCRIPTOR.message_types_by_name['ComponentesEquipoReply']
_EQUIPOENTITY = DESCRIPTOR.message_types_by_name['EquipoEntity']
_EQUIPOENTITYREQ = DESCRIPTOR.message_types_by_name['EquipoEntityReq']
_EQUIPOENTITYREPLY = DESCRIPTOR.message_types_by_name['EquipoEntityReply']
_ARCHIVOENTITY = DESCRIPTOR.message_types_by_name['ArchivoEntity']
_ARCHIVOENTITYREQ = DESCRIPTOR.message_types_by_name['ArchivoEntityReq']
_ARCHIVOSENTITYREPLY = DESCRIPTOR.message_types_by_name['ArchivosEntityReply']
_EQUIPOENTITYACOTADO = DESCRIPTOR.message_types_by_name['EquipoEntityAcotado']
_EQUIPOSENTITYREPLY = DESCRIPTOR.message_types_by_name['EquiposEntityReply']
_EVENTO = DESCRIPTOR.message_types_by_name['Evento']
_SECUENCIA = DESCRIPTOR.message_types_by_name['Secuencia']
_SIMULACIONREQ = DESCRIPTOR.message_types_by_name['SimulacionReq']
_SECUENCIASCOMPONENTE = DESCRIPTOR.message_types_by_name['SecuenciasComponente']
_SECUENCIASCOMPONENTEEQUIPOREPLY = DESCRIPTOR.message_types_by_name['SecuenciasComponenteEquipoReply']
_STARTSIMULACIONREQ = DESCRIPTOR.message_types_by_name['StartSimulacionReq']
_SIMULACIONBOARDREQ = DESCRIPTOR.message_types_by_name['SimulacionBoardReq']
_SIMULACIONREPLY = DESCRIPTOR.message_types_by_name['SimulacionReply']
_SIMULACIONACOTADA = DESCRIPTOR.message_types_by_name['SimulacionAcotada']
_SIMULACIONESREPLY = DESCRIPTOR.message_types_by_name['SimulacionesReply']
_EJECUCIONREPLY = DESCRIPTOR.message_types_by_name['EjecucionReply']
_EJECUCIONACOTADA = DESCRIPTOR.message_types_by_name['EjecucionAcotada']
_EJECUCIONESREPLY = DESCRIPTOR.message_types_by_name['EjecucionesReply']
_LECTURASENSORESREPLY = DESCRIPTOR.message_types_by_name['LecturaSensoresReply']
_SALUDOBOARDREQ = DESCRIPTOR.message_types_by_name['SaludoBoardReq']
_SALUDOBOARDREPLY = DESCRIPTOR.message_types_by_name['SaludoBoardReply']
_AVISOTERMINOEJECUCIONREQ = DESCRIPTOR.message_types_by_name['AvisoTerminoEjecucionReq']
_USUARIOENTITY_ESTADOUSUARIO = _USUARIOENTITY.enum_types_by_name['EstadoUsuario']
_USUARIOENTITY_ROLUSUARIO = _USUARIOENTITY.enum_types_by_name['RolUsuario']
_REGISTRO_TIPOREGISTRO = _REGISTRO.enum_types_by_name['TipoRegistro']
_PIN_CONEXIONPIN = _PIN.enum_types_by_name['ConexionPin']
_COMPONENTE_ESTADOCOMPONENTE = _COMPONENTE.enum_types_by_name['EstadoComponente']
_COMPONENTE_TIPOCOMPONENTE = _COMPONENTE.enum_types_by_name['TipoComponente']
_ARCHIVOENTITY_TIPOARCHIVO = _ARCHIVOENTITY.enum_types_by_name['TipoArchivo']
EmptyReq = _reflection.GeneratedProtocolMessageType('EmptyReq', (_message.Message,), {
  'DESCRIPTOR' : _EMPTYREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EmptyReq)
  })
_sym_db.RegisterMessage(EmptyReq)

IdElementoConRutReq = _reflection.GeneratedProtocolMessageType('IdElementoConRutReq', (_message.Message,), {
  'DESCRIPTOR' : _IDELEMENTOCONRUTREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:IdElementoConRutReq)
  })
_sym_db.RegisterMessage(IdElementoConRutReq)

IdElementoReq = _reflection.GeneratedProtocolMessageType('IdElementoReq', (_message.Message,), {
  'DESCRIPTOR' : _IDELEMENTOREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:IdElementoReq)
  })
_sym_db.RegisterMessage(IdElementoReq)

MensajeReq = _reflection.GeneratedProtocolMessageType('MensajeReq', (_message.Message,), {
  'DESCRIPTOR' : _MENSAJEREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:MensajeReq)
  })
_sym_db.RegisterMessage(MensajeReq)

MensajeReply = _reflection.GeneratedProtocolMessageType('MensajeReply', (_message.Message,), {
  'DESCRIPTOR' : _MENSAJEREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:MensajeReply)
  })
_sym_db.RegisterMessage(MensajeReply)

CredencialesEntityReq = _reflection.GeneratedProtocolMessageType('CredencialesEntityReq', (_message.Message,), {
  'DESCRIPTOR' : _CREDENCIALESENTITYREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:CredencialesEntityReq)
  })
_sym_db.RegisterMessage(CredencialesEntityReq)

SesionEntityReply = _reflection.GeneratedProtocolMessageType('SesionEntityReply', (_message.Message,), {
  'DESCRIPTOR' : _SESIONENTITYREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SesionEntityReply)
  })
_sym_db.RegisterMessage(SesionEntityReply)

RutEntityReq = _reflection.GeneratedProtocolMessageType('RutEntityReq', (_message.Message,), {
  'DESCRIPTOR' : _RUTENTITYREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:RutEntityReq)
  })
_sym_db.RegisterMessage(RutEntityReq)

UsuarioEntity = _reflection.GeneratedProtocolMessageType('UsuarioEntity', (_message.Message,), {
  'DESCRIPTOR' : _USUARIOENTITY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:UsuarioEntity)
  })
_sym_db.RegisterMessage(UsuarioEntity)

UsuarioEntityReq = _reflection.GeneratedProtocolMessageType('UsuarioEntityReq', (_message.Message,), {
  'DESCRIPTOR' : _USUARIOENTITYREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:UsuarioEntityReq)
  })
_sym_db.RegisterMessage(UsuarioEntityReq)

UsuarioEntityReply = _reflection.GeneratedProtocolMessageType('UsuarioEntityReply', (_message.Message,), {
  'DESCRIPTOR' : _USUARIOENTITYREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:UsuarioEntityReply)
  })
_sym_db.RegisterMessage(UsuarioEntityReply)

UsuariosEntityReply = _reflection.GeneratedProtocolMessageType('UsuariosEntityReply', (_message.Message,), {
  'DESCRIPTOR' : _USUARIOSENTITYREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:UsuariosEntityReply)
  })
_sym_db.RegisterMessage(UsuariosEntityReply)

Registro = _reflection.GeneratedProtocolMessageType('Registro', (_message.Message,), {
  'DESCRIPTOR' : _REGISTRO,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:Registro)
  })
_sym_db.RegisterMessage(Registro)

RegistrosReply = _reflection.GeneratedProtocolMessageType('RegistrosReply', (_message.Message,), {
  'DESCRIPTOR' : _REGISTROSREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:RegistrosReply)
  })
_sym_db.RegisterMessage(RegistrosReply)

Pin = _reflection.GeneratedProtocolMessageType('Pin', (_message.Message,), {
  'DESCRIPTOR' : _PIN,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:Pin)
  })
_sym_db.RegisterMessage(Pin)

Componente = _reflection.GeneratedProtocolMessageType('Componente', (_message.Message,), {
  'DESCRIPTOR' : _COMPONENTE,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:Componente)
  })
_sym_db.RegisterMessage(Componente)

Placa = _reflection.GeneratedProtocolMessageType('Placa', (_message.Message,), {
  'DESCRIPTOR' : _PLACA,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:Placa)
  })
_sym_db.RegisterMessage(Placa)

ComponentesEquipoReply = _reflection.GeneratedProtocolMessageType('ComponentesEquipoReply', (_message.Message,), {
  'DESCRIPTOR' : _COMPONENTESEQUIPOREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:ComponentesEquipoReply)
  })
_sym_db.RegisterMessage(ComponentesEquipoReply)

EquipoEntity = _reflection.GeneratedProtocolMessageType('EquipoEntity', (_message.Message,), {
  'DESCRIPTOR' : _EQUIPOENTITY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EquipoEntity)
  })
_sym_db.RegisterMessage(EquipoEntity)

EquipoEntityReq = _reflection.GeneratedProtocolMessageType('EquipoEntityReq', (_message.Message,), {
  'DESCRIPTOR' : _EQUIPOENTITYREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EquipoEntityReq)
  })
_sym_db.RegisterMessage(EquipoEntityReq)

EquipoEntityReply = _reflection.GeneratedProtocolMessageType('EquipoEntityReply', (_message.Message,), {
  'DESCRIPTOR' : _EQUIPOENTITYREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EquipoEntityReply)
  })
_sym_db.RegisterMessage(EquipoEntityReply)

ArchivoEntity = _reflection.GeneratedProtocolMessageType('ArchivoEntity', (_message.Message,), {
  'DESCRIPTOR' : _ARCHIVOENTITY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:ArchivoEntity)
  })
_sym_db.RegisterMessage(ArchivoEntity)

ArchivoEntityReq = _reflection.GeneratedProtocolMessageType('ArchivoEntityReq', (_message.Message,), {
  'DESCRIPTOR' : _ARCHIVOENTITYREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:ArchivoEntityReq)
  })
_sym_db.RegisterMessage(ArchivoEntityReq)

ArchivosEntityReply = _reflection.GeneratedProtocolMessageType('ArchivosEntityReply', (_message.Message,), {
  'DESCRIPTOR' : _ARCHIVOSENTITYREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:ArchivosEntityReply)
  })
_sym_db.RegisterMessage(ArchivosEntityReply)

EquipoEntityAcotado = _reflection.GeneratedProtocolMessageType('EquipoEntityAcotado', (_message.Message,), {
  'DESCRIPTOR' : _EQUIPOENTITYACOTADO,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EquipoEntityAcotado)
  })
_sym_db.RegisterMessage(EquipoEntityAcotado)

EquiposEntityReply = _reflection.GeneratedProtocolMessageType('EquiposEntityReply', (_message.Message,), {
  'DESCRIPTOR' : _EQUIPOSENTITYREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EquiposEntityReply)
  })
_sym_db.RegisterMessage(EquiposEntityReply)

Evento = _reflection.GeneratedProtocolMessageType('Evento', (_message.Message,), {
  'DESCRIPTOR' : _EVENTO,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:Evento)
  })
_sym_db.RegisterMessage(Evento)

Secuencia = _reflection.GeneratedProtocolMessageType('Secuencia', (_message.Message,), {
  'DESCRIPTOR' : _SECUENCIA,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:Secuencia)
  })
_sym_db.RegisterMessage(Secuencia)

SimulacionReq = _reflection.GeneratedProtocolMessageType('SimulacionReq', (_message.Message,), {
  'DESCRIPTOR' : _SIMULACIONREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SimulacionReq)
  })
_sym_db.RegisterMessage(SimulacionReq)

SecuenciasComponente = _reflection.GeneratedProtocolMessageType('SecuenciasComponente', (_message.Message,), {
  'DESCRIPTOR' : _SECUENCIASCOMPONENTE,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SecuenciasComponente)
  })
_sym_db.RegisterMessage(SecuenciasComponente)

SecuenciasComponenteEquipoReply = _reflection.GeneratedProtocolMessageType('SecuenciasComponenteEquipoReply', (_message.Message,), {
  'DESCRIPTOR' : _SECUENCIASCOMPONENTEEQUIPOREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SecuenciasComponenteEquipoReply)
  })
_sym_db.RegisterMessage(SecuenciasComponenteEquipoReply)

StartSimulacionReq = _reflection.GeneratedProtocolMessageType('StartSimulacionReq', (_message.Message,), {
  'DESCRIPTOR' : _STARTSIMULACIONREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:StartSimulacionReq)
  })
_sym_db.RegisterMessage(StartSimulacionReq)

SimulacionBoardReq = _reflection.GeneratedProtocolMessageType('SimulacionBoardReq', (_message.Message,), {
  'DESCRIPTOR' : _SIMULACIONBOARDREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SimulacionBoardReq)
  })
_sym_db.RegisterMessage(SimulacionBoardReq)

SimulacionReply = _reflection.GeneratedProtocolMessageType('SimulacionReply', (_message.Message,), {
  'DESCRIPTOR' : _SIMULACIONREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SimulacionReply)
  })
_sym_db.RegisterMessage(SimulacionReply)

SimulacionAcotada = _reflection.GeneratedProtocolMessageType('SimulacionAcotada', (_message.Message,), {
  'DESCRIPTOR' : _SIMULACIONACOTADA,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SimulacionAcotada)
  })
_sym_db.RegisterMessage(SimulacionAcotada)

SimulacionesReply = _reflection.GeneratedProtocolMessageType('SimulacionesReply', (_message.Message,), {
  'DESCRIPTOR' : _SIMULACIONESREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SimulacionesReply)
  })
_sym_db.RegisterMessage(SimulacionesReply)

EjecucionReply = _reflection.GeneratedProtocolMessageType('EjecucionReply', (_message.Message,), {
  'DESCRIPTOR' : _EJECUCIONREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EjecucionReply)
  })
_sym_db.RegisterMessage(EjecucionReply)

EjecucionAcotada = _reflection.GeneratedProtocolMessageType('EjecucionAcotada', (_message.Message,), {
  'DESCRIPTOR' : _EJECUCIONACOTADA,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EjecucionAcotada)
  })
_sym_db.RegisterMessage(EjecucionAcotada)

EjecucionesReply = _reflection.GeneratedProtocolMessageType('EjecucionesReply', (_message.Message,), {
  'DESCRIPTOR' : _EJECUCIONESREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:EjecucionesReply)
  })
_sym_db.RegisterMessage(EjecucionesReply)

LecturaSensoresReply = _reflection.GeneratedProtocolMessageType('LecturaSensoresReply', (_message.Message,), {
  'DESCRIPTOR' : _LECTURASENSORESREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:LecturaSensoresReply)
  })
_sym_db.RegisterMessage(LecturaSensoresReply)

SaludoBoardReq = _reflection.GeneratedProtocolMessageType('SaludoBoardReq', (_message.Message,), {
  'DESCRIPTOR' : _SALUDOBOARDREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SaludoBoardReq)
  })
_sym_db.RegisterMessage(SaludoBoardReq)

SaludoBoardReply = _reflection.GeneratedProtocolMessageType('SaludoBoardReply', (_message.Message,), {
  'DESCRIPTOR' : _SALUDOBOARDREPLY,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:SaludoBoardReply)
  })
_sym_db.RegisterMessage(SaludoBoardReply)

AvisoTerminoEjecucionReq = _reflection.GeneratedProtocolMessageType('AvisoTerminoEjecucionReq', (_message.Message,), {
  'DESCRIPTOR' : _AVISOTERMINOEJECUCIONREQ,
  '__module__' : 'coreBoardCommuService_pb2'
  # @@protoc_insertion_point(class_scope:AvisoTerminoEjecucionReq)
  })
_sym_db.RegisterMessage(AvisoTerminoEjecucionReq)

_COREBOARDCOMMUSERVICE = DESCRIPTOR.services_by_name['CoreBoardCommuService']
if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  _TIPOPLACA._serialized_start=4277
  _TIPOPLACA._serialized_end=4318
  _ESTADOEQUIPO._serialized_start=4320
  _ESTADOEQUIPO._serialized_end=4367
  _EMPTYREQ._serialized_start=31
  _EMPTYREQ._serialized_end=41
  _IDELEMENTOCONRUTREQ._serialized_start=43
  _IDELEMENTOCONRUTREQ._serialized_end=89
  _IDELEMENTOREQ._serialized_start=91
  _IDELEMENTOREQ._serialized_end=118
  _MENSAJEREQ._serialized_start=120
  _MENSAJEREQ._serialized_end=155
  _MENSAJEREPLY._serialized_start=157
  _MENSAJEREPLY._serialized_end=194
  _CREDENCIALESENTITYREQ._serialized_start=196
  _CREDENCIALESENTITYREQ._serialized_end=252
  _SESIONENTITYREPLY._serialized_start=254
  _SESIONENTITYREPLY._serialized_end=343
  _RUTENTITYREQ._serialized_start=345
  _RUTENTITYREQ._serialized_end=372
  _USUARIOENTITY._serialized_start=375
  _USUARIOENTITY._serialized_end=664
  _USUARIOENTITY_ESTADOUSUARIO._serialized_start=558
  _USUARIOENTITY_ESTADOUSUARIO._serialized_end=599
  _USUARIOENTITY_ROLUSUARIO._serialized_start=601
  _USUARIOENTITY_ROLUSUARIO._serialized_end=664
  _USUARIOENTITYREQ._serialized_start=666
  _USUARIOENTITYREQ._serialized_end=744
  _USUARIOENTITYREPLY._serialized_start=746
  _USUARIOENTITYREPLY._serialized_end=811
  _USUARIOSENTITYREPLY._serialized_start=813
  _USUARIOSENTITYREPLY._serialized_end=867
  _REGISTRO._serialized_start=870
  _REGISTRO._serialized_end=1221
  _REGISTRO_TIPOREGISTRO._serialized_start=1026
  _REGISTRO_TIPOREGISTRO._serialized_end=1221
  _REGISTROSREPLY._serialized_start=1223
  _REGISTROSREPLY._serialized_end=1268
  _PIN._serialized_start=1271
  _PIN._serialized_end=1462
  _PIN_CONEXIONPIN._serialized_start=1367
  _PIN_CONEXIONPIN._serialized_end=1462
  _COMPONENTE._serialized_start=1465
  _COMPONENTE._serialized_end=1837
  _COMPONENTE_ESTADOCOMPONENTE._serialized_start=1691
  _COMPONENTE_ESTADOCOMPONENTE._serialized_end=1762
  _COMPONENTE_TIPOCOMPONENTE._serialized_start=1764
  _COMPONENTE_TIPOCOMPONENTE._serialized_end=1837
  _PLACA._serialized_start=1839
  _PLACA._serialized_end=1909
  _COMPONENTESEQUIPOREPLY._serialized_start=1911
  _COMPONENTESEQUIPOREPLY._serialized_end=1968
  _EQUIPOENTITY._serialized_start=1971
  _EQUIPOENTITY._serialized_end=2146
  _EQUIPOENTITYREQ._serialized_start=2148
  _EQUIPOENTITYREQ._serialized_end=2222
  _EQUIPOENTITYREPLY._serialized_start=2224
  _EQUIPOENTITYREPLY._serialized_end=2274
  _ARCHIVOENTITY._serialized_start=2277
  _ARCHIVOENTITY._serialized_end=2434
  _ARCHIVOENTITY_TIPOARCHIVO._serialized_start=2384
  _ARCHIVOENTITY_TIPOARCHIVO._serialized_end=2434
  _ARCHIVOENTITYREQ._serialized_start=2436
  _ARCHIVOENTITYREQ._serialized_end=2487
  _ARCHIVOSENTITYREPLY._serialized_start=2489
  _ARCHIVOSENTITYREPLY._serialized_end=2543
  _EQUIPOENTITYACOTADO._serialized_start=2545
  _EQUIPOENTITYACOTADO._serialized_end=2625
  _EQUIPOSENTITYREPLY._serialized_start=2627
  _EQUIPOSENTITYREPLY._serialized_end=2693
  _EVENTO._serialized_start=2695
  _EVENTO._serialized_end=2759
  _SECUENCIA._serialized_start=2761
  _SECUENCIA._serialized_end=2820
  _SIMULACIONREQ._serialized_start=2823
  _SIMULACIONREQ._serialized_end=2951
  _SECUENCIASCOMPONENTE._serialized_start=2953
  _SECUENCIASCOMPONENTE._serialized_end=3017
  _SECUENCIASCOMPONENTEEQUIPOREPLY._serialized_start=3019
  _SECUENCIASCOMPONENTEEQUIPOREPLY._serialized_end=3105
  _STARTSIMULACIONREQ._serialized_start=3107
  _STARTSIMULACIONREQ._serialized_end=3173
  _SIMULACIONBOARDREQ._serialized_start=3175
  _SIMULACIONBOARDREQ._serialized_end=3249
  _SIMULACIONREPLY._serialized_start=3252
  _SIMULACIONREPLY._serialized_end=3400
  _SIMULACIONACOTADA._serialized_start=3402
  _SIMULACIONACOTADA._serialized_end=3472
  _SIMULACIONESREPLY._serialized_start=3474
  _SIMULACIONESREPLY._serialized_end=3541
  _EJECUCIONREPLY._serialized_start=3544
  _EJECUCIONREPLY._serialized_end=3736
  _EJECUCIONACOTADA._serialized_start=3738
  _EJECUCIONACOTADA._serialized_end=3863
  _EJECUCIONESREPLY._serialized_start=3865
  _EJECUCIONESREPLY._serialized_end=3929
  _LECTURASENSORESREPLY._serialized_start=3931
  _LECTURASENSORESREPLY._serialized_end=4050
  _SALUDOBOARDREQ._serialized_start=4052
  _SALUDOBOARDREQ._serialized_end=4120
  _SALUDOBOARDREPLY._serialized_start=4122
  _SALUDOBOARDREPLY._serialized_end=4196
  _AVISOTERMINOEJECUCIONREQ._serialized_start=4198
  _AVISOTERMINOEJECUCIONREQ._serialized_end=4275
  _COREBOARDCOMMUSERVICE._serialized_start=4370
  _COREBOARDCOMMUSERVICE._serialized_end=4645
# @@protoc_insertion_point(module_scope)
