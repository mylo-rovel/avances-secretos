from google.protobuf.internal import containers as _containers
from google.protobuf.internal import enum_type_wrapper as _enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Mapping as _Mapping, Optional as _Optional, Union as _Union

ARDUINO_2560: TipoPlaca
CONSTRUCCION: EstadoEquipo
DESCRIPTOR: _descriptor.FileDescriptor
ESP_32: TipoPlaca
ESTADO_NONE: EstadoEquipo
PROTOTIPO: EstadoEquipo
TIPO_NONE: TipoPlaca

class ArchivoEntity(_message.Message):
    __slots__ = ["archivo", "nombre_equipo", "tipo_archivo"]
    class TipoArchivo(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
        __slots__ = []
    ARCHIVO_FIELD_NUMBER: _ClassVar[int]
    JPEG: ArchivoEntity.TipoArchivo
    JPG: ArchivoEntity.TipoArchivo
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    PDF: ArchivoEntity.TipoArchivo
    PNG: ArchivoEntity.TipoArchivo
    TIPO_ARCHIVO_FIELD_NUMBER: _ClassVar[int]
    TIPO_NONE: ArchivoEntity.TipoArchivo
    archivo: bytes
    nombre_equipo: str
    tipo_archivo: ArchivoEntity.TipoArchivo
    def __init__(self, nombre_equipo: _Optional[str] = ..., archivo: _Optional[bytes] = ..., tipo_archivo: _Optional[_Union[ArchivoEntity.TipoArchivo, str]] = ...) -> None: ...

class ArchivoEntityReq(_message.Message):
    __slots__ = ["archivo"]
    ARCHIVO_FIELD_NUMBER: _ClassVar[int]
    archivo: ArchivoEntity
    def __init__(self, archivo: _Optional[_Union[ArchivoEntity, _Mapping]] = ...) -> None: ...

class ArchivosEntityReply(_message.Message):
    __slots__ = ["archivo"]
    ARCHIVO_FIELD_NUMBER: _ClassVar[int]
    archivo: ArchivoEntity
    def __init__(self, archivo: _Optional[_Union[ArchivoEntity, _Mapping]] = ...) -> None: ...

class AvisoTerminoEjecucionReq(_message.Message):
    __slots__ = ["agua_caida", "nombreEquipo"]
    AGUA_CAIDA_FIELD_NUMBER: _ClassVar[int]
    NOMBREEQUIPO_FIELD_NUMBER: _ClassVar[int]
    agua_caida: float
    nombreEquipo: str
    def __init__(self, nombreEquipo: _Optional[str] = ..., agua_caida: _Optional[float] = ...) -> None: ...

class Componente(_message.Message):
    __slots__ = ["descripcion", "estado", "id", "nombre", "pin_componente", "tipo", "tipo_placa", "url"]
    class EstadoComponente(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
        __slots__ = []
    class TipoComponente(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
        __slots__ = []
    ACTIVO: Componente.EstadoComponente
    CAMARA: Componente.TipoComponente
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    ESTADO_FIELD_NUMBER: _ClassVar[int]
    ESTADO_NONE: Componente.EstadoComponente
    FALLA: Componente.EstadoComponente
    FLUJOMETRO: Componente.TipoComponente
    ID_FIELD_NUMBER: _ClassVar[int]
    INACTIVO: Componente.EstadoComponente
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    PIN_COMPONENTE_FIELD_NUMBER: _ClassVar[int]
    REPARACION: Componente.EstadoComponente
    TERMOMETRO: Componente.TipoComponente
    TIPO_FIELD_NUMBER: _ClassVar[int]
    TIPO_NONE: Componente.TipoComponente
    TIPO_PLACA_FIELD_NUMBER: _ClassVar[int]
    URL_FIELD_NUMBER: _ClassVar[int]
    VALVULA: Componente.TipoComponente
    descripcion: str
    estado: Componente.EstadoComponente
    id: int
    nombre: str
    pin_componente: _containers.RepeatedCompositeFieldContainer[Pin]
    tipo: Componente.TipoComponente
    tipo_placa: TipoPlaca
    url: str
    def __init__(self, id: _Optional[int] = ..., nombre: _Optional[str] = ..., descripcion: _Optional[str] = ..., url: _Optional[str] = ..., estado: _Optional[_Union[Componente.EstadoComponente, str]] = ..., tipo: _Optional[_Union[Componente.TipoComponente, str]] = ..., tipo_placa: _Optional[_Union[TipoPlaca, str]] = ..., pin_componente: _Optional[_Iterable[_Union[Pin, _Mapping]]] = ...) -> None: ...

class ComponentesEquipoReply(_message.Message):
    __slots__ = ["componente"]
    COMPONENTE_FIELD_NUMBER: _ClassVar[int]
    componente: _containers.RepeatedCompositeFieldContainer[Componente]
    def __init__(self, componente: _Optional[_Iterable[_Union[Componente, _Mapping]]] = ...) -> None: ...

class CredencialesEntityReq(_message.Message):
    __slots__ = ["email", "password"]
    EMAIL_FIELD_NUMBER: _ClassVar[int]
    PASSWORD_FIELD_NUMBER: _ClassVar[int]
    email: str
    password: str
    def __init__(self, email: _Optional[str] = ..., password: _Optional[str] = ...) -> None: ...

class EjecucionAcotada(_message.Message):
    __slots__ = ["agua_caida", "fecha_ejecucion", "id", "nombre_equipo", "nombre_simulacion"]
    AGUA_CAIDA_FIELD_NUMBER: _ClassVar[int]
    FECHA_EJECUCION_FIELD_NUMBER: _ClassVar[int]
    ID_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_SIMULACION_FIELD_NUMBER: _ClassVar[int]
    agua_caida: float
    fecha_ejecucion: str
    id: int
    nombre_equipo: str
    nombre_simulacion: str
    def __init__(self, id: _Optional[int] = ..., nombre_simulacion: _Optional[str] = ..., nombre_equipo: _Optional[str] = ..., fecha_ejecucion: _Optional[str] = ..., agua_caida: _Optional[float] = ...) -> None: ...

class EjecucionReply(_message.Message):
    __slots__ = ["agua_caida", "descripcion", "descripcion_equipo", "fecha_ejecucion", "id", "nombre", "nombre_equipo", "secuencia"]
    AGUA_CAIDA_FIELD_NUMBER: _ClassVar[int]
    DESCRIPCION_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    FECHA_EJECUCION_FIELD_NUMBER: _ClassVar[int]
    ID_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    SECUENCIA_FIELD_NUMBER: _ClassVar[int]
    agua_caida: float
    descripcion: str
    descripcion_equipo: str
    fecha_ejecucion: str
    id: int
    nombre: str
    nombre_equipo: str
    secuencia: _containers.RepeatedCompositeFieldContainer[Secuencia]
    def __init__(self, id: _Optional[int] = ..., nombre: _Optional[str] = ..., descripcion: _Optional[str] = ..., nombre_equipo: _Optional[str] = ..., descripcion_equipo: _Optional[str] = ..., fecha_ejecucion: _Optional[str] = ..., secuencia: _Optional[_Iterable[_Union[Secuencia, _Mapping]]] = ..., agua_caida: _Optional[float] = ...) -> None: ...

class EjecucionesReply(_message.Message):
    __slots__ = ["ejecucion_acotada"]
    EJECUCION_ACOTADA_FIELD_NUMBER: _ClassVar[int]
    ejecucion_acotada: _containers.RepeatedCompositeFieldContainer[EjecucionAcotada]
    def __init__(self, ejecucion_acotada: _Optional[_Iterable[_Union[EjecucionAcotada, _Mapping]]] = ...) -> None: ...

class EmptyReq(_message.Message):
    __slots__ = []
    def __init__(self) -> None: ...

class EquipoEntity(_message.Message):
    __slots__ = ["componente", "descripcion", "estado", "id", "nombre", "placa", "url_repositorio"]
    COMPONENTE_FIELD_NUMBER: _ClassVar[int]
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    ESTADO_FIELD_NUMBER: _ClassVar[int]
    ID_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    PLACA_FIELD_NUMBER: _ClassVar[int]
    URL_REPOSITORIO_FIELD_NUMBER: _ClassVar[int]
    componente: _containers.RepeatedCompositeFieldContainer[Componente]
    descripcion: str
    estado: EstadoEquipo
    id: int
    nombre: str
    placa: _containers.RepeatedCompositeFieldContainer[Placa]
    url_repositorio: str
    def __init__(self, id: _Optional[int] = ..., nombre: _Optional[str] = ..., descripcion: _Optional[str] = ..., url_repositorio: _Optional[str] = ..., estado: _Optional[_Union[EstadoEquipo, str]] = ..., placa: _Optional[_Iterable[_Union[Placa, _Mapping]]] = ..., componente: _Optional[_Iterable[_Union[Componente, _Mapping]]] = ...) -> None: ...

class EquipoEntityAcotado(_message.Message):
    __slots__ = ["estado", "id", "nombre"]
    ESTADO_FIELD_NUMBER: _ClassVar[int]
    ID_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    estado: EstadoEquipo
    id: int
    nombre: str
    def __init__(self, id: _Optional[int] = ..., nombre: _Optional[str] = ..., estado: _Optional[_Union[EstadoEquipo, str]] = ...) -> None: ...

class EquipoEntityReply(_message.Message):
    __slots__ = ["equipo"]
    EQUIPO_FIELD_NUMBER: _ClassVar[int]
    equipo: EquipoEntity
    def __init__(self, equipo: _Optional[_Union[EquipoEntity, _Mapping]] = ...) -> None: ...

class EquipoEntityReq(_message.Message):
    __slots__ = ["equipo", "rut_configurador"]
    EQUIPO_FIELD_NUMBER: _ClassVar[int]
    RUT_CONFIGURADOR_FIELD_NUMBER: _ClassVar[int]
    equipo: EquipoEntity
    rut_configurador: str
    def __init__(self, rut_configurador: _Optional[str] = ..., equipo: _Optional[_Union[EquipoEntity, _Mapping]] = ...) -> None: ...

class EquiposEntityReply(_message.Message):
    __slots__ = ["equipo_acotado"]
    EQUIPO_ACOTADO_FIELD_NUMBER: _ClassVar[int]
    equipo_acotado: _containers.RepeatedCompositeFieldContainer[EquipoEntityAcotado]
    def __init__(self, equipo_acotado: _Optional[_Iterable[_Union[EquipoEntityAcotado, _Mapping]]] = ...) -> None: ...

class Evento(_message.Message):
    __slots__ = ["duracion", "intensidad", "posicion"]
    DURACION_FIELD_NUMBER: _ClassVar[int]
    INTENSIDAD_FIELD_NUMBER: _ClassVar[int]
    POSICION_FIELD_NUMBER: _ClassVar[int]
    duracion: int
    intensidad: int
    posicion: int
    def __init__(self, intensidad: _Optional[int] = ..., duracion: _Optional[int] = ..., posicion: _Optional[int] = ...) -> None: ...

class IdElementoConRutReq(_message.Message):
    __slots__ = ["id", "rut"]
    ID_FIELD_NUMBER: _ClassVar[int]
    RUT_FIELD_NUMBER: _ClassVar[int]
    id: int
    rut: str
    def __init__(self, id: _Optional[int] = ..., rut: _Optional[str] = ...) -> None: ...

class IdElementoReq(_message.Message):
    __slots__ = ["id"]
    ID_FIELD_NUMBER: _ClassVar[int]
    id: int
    def __init__(self, id: _Optional[int] = ...) -> None: ...

class LecturaSensoresReply(_message.Message):
    __slots__ = ["caudal_tiempo", "lista_size"]
    CAUDAL_TIEMPO_FIELD_NUMBER: _ClassVar[int]
    LISTA_SIZE_FIELD_NUMBER: _ClassVar[int]
    caudal_tiempo: str
    lista_size: int
    def __init__(self, caudal_tiempo: _Optional[str] = ..., lista_size: _Optional[int] = ...) -> None: ...

class LecturaSensoresReq(_message.Message):
    __slots__ = ["aguaCaida", "caudal", "hora", "humedad", "nombreEquipo", "pluviometro", "presion", "temperatura"]
    AGUACAIDA_FIELD_NUMBER: _ClassVar[int]
    CAUDAL_FIELD_NUMBER: _ClassVar[int]
    HORA_FIELD_NUMBER: _ClassVar[int]
    HUMEDAD_FIELD_NUMBER: _ClassVar[int]
    NOMBREEQUIPO_FIELD_NUMBER: _ClassVar[int]
    PLUVIOMETRO_FIELD_NUMBER: _ClassVar[int]
    PRESION_FIELD_NUMBER: _ClassVar[int]
    TEMPERATURA_FIELD_NUMBER: _ClassVar[int]
    aguaCaida: float
    caudal: float
    hora: str
    humedad: float
    nombreEquipo: str
    pluviometro: float
    presion: float
    temperatura: float
    def __init__(self, caudal: _Optional[float] = ..., temperatura: _Optional[float] = ..., humedad: _Optional[float] = ..., presion: _Optional[float] = ..., pluviometro: _Optional[float] = ..., hora: _Optional[str] = ..., nombreEquipo: _Optional[str] = ..., aguaCaida: _Optional[float] = ...) -> None: ...

class MensajeReply(_message.Message):
    __slots__ = ["mensaje_texto"]
    MENSAJE_TEXTO_FIELD_NUMBER: _ClassVar[int]
    mensaje_texto: str
    def __init__(self, mensaje_texto: _Optional[str] = ...) -> None: ...

class MensajeReq(_message.Message):
    __slots__ = ["mensaje_texto"]
    MENSAJE_TEXTO_FIELD_NUMBER: _ClassVar[int]
    mensaje_texto: str
    def __init__(self, mensaje_texto: _Optional[str] = ...) -> None: ...

class Pin(_message.Message):
    __slots__ = ["conexion", "descripcion", "nombre", "numero"]
    class ConexionPin(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
        __slots__ = []
    CONEXION_FIELD_NUMBER: _ClassVar[int]
    CONEXION_NONE: Pin.ConexionPin
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    INPUT_ANALOGICO: Pin.ConexionPin
    INPUT_DIGITAL: Pin.ConexionPin
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    NUMERO_FIELD_NUMBER: _ClassVar[int]
    OUTPUT_ANALOGICO: Pin.ConexionPin
    OUTPUT_DIGITAL: Pin.ConexionPin
    conexion: Pin.ConexionPin
    descripcion: str
    nombre: str
    numero: int
    def __init__(self, numero: _Optional[int] = ..., nombre: _Optional[str] = ..., descripcion: _Optional[str] = ..., conexion: _Optional[_Union[Pin.ConexionPin, str]] = ...) -> None: ...

class Placa(_message.Message):
    __slots__ = ["descripcion", "nombre", "tipo"]
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    TIPO_FIELD_NUMBER: _ClassVar[int]
    descripcion: str
    nombre: str
    tipo: TipoPlaca
    def __init__(self, nombre: _Optional[str] = ..., descripcion: _Optional[str] = ..., tipo: _Optional[_Union[TipoPlaca, str]] = ...) -> None: ...

class Registro(_message.Message):
    __slots__ = ["descripcion", "id", "id_entidad", "timestamp", "tipo", "usuario"]
    class TipoRegistro(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
        __slots__ = []
    CREACION_EQUIPO: Registro.TipoRegistro
    CREACION_SIMULACION: Registro.TipoRegistro
    CREACION_USUARIO: Registro.TipoRegistro
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    ID_ENTIDAD_FIELD_NUMBER: _ClassVar[int]
    ID_FIELD_NUMBER: _ClassVar[int]
    INICIO_SIMULACION: Registro.TipoRegistro
    LOGIN_USUARIO: Registro.TipoRegistro
    MODIFICACION_EQUIPO: Registro.TipoRegistro
    MODIFICACION_USUARIO: Registro.TipoRegistro
    REGISTRO_NONE: Registro.TipoRegistro
    TIMESTAMP_FIELD_NUMBER: _ClassVar[int]
    TIPO_FIELD_NUMBER: _ClassVar[int]
    UPLOAD_ARCHIVO: Registro.TipoRegistro
    USUARIO_FIELD_NUMBER: _ClassVar[int]
    descripcion: str
    id: int
    id_entidad: int
    timestamp: str
    tipo: Registro.TipoRegistro
    usuario: UsuarioEntity
    def __init__(self, id: _Optional[int] = ..., id_entidad: _Optional[int] = ..., timestamp: _Optional[str] = ..., descripcion: _Optional[str] = ..., tipo: _Optional[_Union[Registro.TipoRegistro, str]] = ..., usuario: _Optional[_Union[UsuarioEntity, _Mapping]] = ...) -> None: ...

class RegistrosReply(_message.Message):
    __slots__ = ["registro"]
    REGISTRO_FIELD_NUMBER: _ClassVar[int]
    registro: _containers.RepeatedCompositeFieldContainer[Registro]
    def __init__(self, registro: _Optional[_Iterable[_Union[Registro, _Mapping]]] = ...) -> None: ...

class RutEntityReq(_message.Message):
    __slots__ = ["rut"]
    RUT_FIELD_NUMBER: _ClassVar[int]
    rut: str
    def __init__(self, rut: _Optional[str] = ...) -> None: ...

class SaludoBoardReply(_message.Message):
    __slots__ = ["equipo", "respuestaSaludo"]
    EQUIPO_FIELD_NUMBER: _ClassVar[int]
    RESPUESTASALUDO_FIELD_NUMBER: _ClassVar[int]
    equipo: EquipoEntity
    respuestaSaludo: str
    def __init__(self, equipo: _Optional[_Union[EquipoEntity, _Mapping]] = ..., respuestaSaludo: _Optional[str] = ...) -> None: ...

class SaludoBoardReq(_message.Message):
    __slots__ = ["direccion_ip_equipo", "nombre_equipo"]
    DIRECCION_IP_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    direccion_ip_equipo: str
    nombre_equipo: str
    def __init__(self, nombre_equipo: _Optional[str] = ..., direccion_ip_equipo: _Optional[str] = ...) -> None: ...

class Secuencia(_message.Message):
    __slots__ = ["evento", "id_componente"]
    EVENTO_FIELD_NUMBER: _ClassVar[int]
    ID_COMPONENTE_FIELD_NUMBER: _ClassVar[int]
    evento: _containers.RepeatedCompositeFieldContainer[Evento]
    id_componente: int
    def __init__(self, id_componente: _Optional[int] = ..., evento: _Optional[_Iterable[_Union[Evento, _Mapping]]] = ...) -> None: ...

class SecuenciasComponente(_message.Message):
    __slots__ = ["secuencia_componente"]
    SECUENCIA_COMPONENTE_FIELD_NUMBER: _ClassVar[int]
    secuencia_componente: _containers.RepeatedCompositeFieldContainer[Secuencia]
    def __init__(self, secuencia_componente: _Optional[_Iterable[_Union[Secuencia, _Mapping]]] = ...) -> None: ...

class SecuenciasComponenteEquipoReply(_message.Message):
    __slots__ = ["secuencia_componente"]
    SECUENCIA_COMPONENTE_FIELD_NUMBER: _ClassVar[int]
    secuencia_componente: _containers.RepeatedCompositeFieldContainer[SecuenciasComponente]
    def __init__(self, secuencia_componente: _Optional[_Iterable[_Union[SecuenciasComponente, _Mapping]]] = ...) -> None: ...

class SesionEntityReply(_message.Message):
    __slots__ = ["json_web_token", "rol_usuario", "sesion_iniciada"]
    JSON_WEB_TOKEN_FIELD_NUMBER: _ClassVar[int]
    ROL_USUARIO_FIELD_NUMBER: _ClassVar[int]
    SESION_INICIADA_FIELD_NUMBER: _ClassVar[int]
    json_web_token: str
    rol_usuario: str
    sesion_iniciada: bool
    def __init__(self, sesion_iniciada: bool = ..., json_web_token: _Optional[str] = ..., rol_usuario: _Optional[str] = ...) -> None: ...

class SimulacionAcotada(_message.Message):
    __slots__ = ["id", "nombre", "nombre_equipo"]
    ID_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    id: int
    nombre: str
    nombre_equipo: str
    def __init__(self, id: _Optional[int] = ..., nombre: _Optional[str] = ..., nombre_equipo: _Optional[str] = ...) -> None: ...

class SimulacionBoardReq(_message.Message):
    __slots__ = ["id_simulacion", "secuencia"]
    ID_SIMULACION_FIELD_NUMBER: _ClassVar[int]
    SECUENCIA_FIELD_NUMBER: _ClassVar[int]
    id_simulacion: int
    secuencia: _containers.RepeatedCompositeFieldContainer[Secuencia]
    def __init__(self, id_simulacion: _Optional[int] = ..., secuencia: _Optional[_Iterable[_Union[Secuencia, _Mapping]]] = ...) -> None: ...

class SimulacionReply(_message.Message):
    __slots__ = ["descripcion", "descripcion_equipo", "id", "nombre", "nombre_equipo", "secuencia"]
    DESCRIPCION_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    ID_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    SECUENCIA_FIELD_NUMBER: _ClassVar[int]
    descripcion: str
    descripcion_equipo: str
    id: int
    nombre: str
    nombre_equipo: str
    secuencia: _containers.RepeatedCompositeFieldContainer[Secuencia]
    def __init__(self, id: _Optional[int] = ..., nombre: _Optional[str] = ..., descripcion: _Optional[str] = ..., nombre_equipo: _Optional[str] = ..., descripcion_equipo: _Optional[str] = ..., secuencia: _Optional[_Iterable[_Union[Secuencia, _Mapping]]] = ...) -> None: ...

class SimulacionReq(_message.Message):
    __slots__ = ["descripcion", "nombre", "nombre_equipo", "rut_operador", "secuencia"]
    DESCRIPCION_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    RUT_OPERADOR_FIELD_NUMBER: _ClassVar[int]
    SECUENCIA_FIELD_NUMBER: _ClassVar[int]
    descripcion: str
    nombre: str
    nombre_equipo: str
    rut_operador: str
    secuencia: _containers.RepeatedCompositeFieldContainer[Secuencia]
    def __init__(self, nombre: _Optional[str] = ..., descripcion: _Optional[str] = ..., nombre_equipo: _Optional[str] = ..., rut_operador: _Optional[str] = ..., secuencia: _Optional[_Iterable[_Union[Secuencia, _Mapping]]] = ...) -> None: ...

class SimulacionesReply(_message.Message):
    __slots__ = ["simulacion_acotada"]
    SIMULACION_ACOTADA_FIELD_NUMBER: _ClassVar[int]
    simulacion_acotada: _containers.RepeatedCompositeFieldContainer[SimulacionAcotada]
    def __init__(self, simulacion_acotada: _Optional[_Iterable[_Union[SimulacionAcotada, _Mapping]]] = ...) -> None: ...

class StartSimulacionReq(_message.Message):
    __slots__ = ["id_simulacion", "nombre_equipo"]
    ID_SIMULACION_FIELD_NUMBER: _ClassVar[int]
    NOMBRE_EQUIPO_FIELD_NUMBER: _ClassVar[int]
    id_simulacion: int
    nombre_equipo: str
    def __init__(self, id_simulacion: _Optional[int] = ..., nombre_equipo: _Optional[str] = ...) -> None: ...

class UsuarioEntity(_message.Message):
    __slots__ = ["apellido", "email", "estado", "nombre", "password", "rol", "rut"]
    class EstadoUsuario(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
        __slots__ = []
    class RolUsuario(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
        __slots__ = []
    ACTIVO: UsuarioEntity.EstadoUsuario
    ADMINISTRADOR: UsuarioEntity.RolUsuario
    APELLIDO_FIELD_NUMBER: _ClassVar[int]
    CONFIGURADOR: UsuarioEntity.RolUsuario
    EMAIL_FIELD_NUMBER: _ClassVar[int]
    ESTADO_FIELD_NUMBER: _ClassVar[int]
    ESTADO_NONE: UsuarioEntity.EstadoUsuario
    INACTIVO: UsuarioEntity.EstadoUsuario
    NOMBRE_FIELD_NUMBER: _ClassVar[int]
    OPERADOR: UsuarioEntity.RolUsuario
    PASSWORD_FIELD_NUMBER: _ClassVar[int]
    ROL_FIELD_NUMBER: _ClassVar[int]
    ROL_NONE: UsuarioEntity.RolUsuario
    RUT_FIELD_NUMBER: _ClassVar[int]
    apellido: str
    email: str
    estado: UsuarioEntity.EstadoUsuario
    nombre: str
    password: str
    rol: UsuarioEntity.RolUsuario
    rut: str
    def __init__(self, nombre: _Optional[str] = ..., apellido: _Optional[str] = ..., rut: _Optional[str] = ..., email: _Optional[str] = ..., password: _Optional[str] = ..., rol: _Optional[_Union[UsuarioEntity.RolUsuario, str]] = ..., estado: _Optional[_Union[UsuarioEntity.EstadoUsuario, str]] = ...) -> None: ...

class UsuarioEntityReply(_message.Message):
    __slots__ = ["id", "usuario"]
    ID_FIELD_NUMBER: _ClassVar[int]
    USUARIO_FIELD_NUMBER: _ClassVar[int]
    id: int
    usuario: UsuarioEntity
    def __init__(self, id: _Optional[int] = ..., usuario: _Optional[_Union[UsuarioEntity, _Mapping]] = ...) -> None: ...

class UsuarioEntityReq(_message.Message):
    __slots__ = ["rut_administrador", "usuario"]
    RUT_ADMINISTRADOR_FIELD_NUMBER: _ClassVar[int]
    USUARIO_FIELD_NUMBER: _ClassVar[int]
    rut_administrador: str
    usuario: UsuarioEntity
    def __init__(self, rut_administrador: _Optional[str] = ..., usuario: _Optional[_Union[UsuarioEntity, _Mapping]] = ...) -> None: ...

class UsuariosEntityReply(_message.Message):
    __slots__ = ["usuario"]
    USUARIO_FIELD_NUMBER: _ClassVar[int]
    usuario: _containers.RepeatedCompositeFieldContainer[UsuarioEntity]
    def __init__(self, usuario: _Optional[_Iterable[_Union[UsuarioEntity, _Mapping]]] = ...) -> None: ...

class TipoPlaca(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
    __slots__ = []

class EstadoEquipo(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
    __slots__ = []
