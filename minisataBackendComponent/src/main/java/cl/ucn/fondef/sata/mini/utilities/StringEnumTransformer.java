package cl.ucn.fondef.sata.mini.utilities;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import org.springframework.stereotype.Service;

@Service
public class StringEnumTransformer {

    public Domain.UsuarioEntity.RolUsuario getEnumRolUsuario(String stringEvaluar) {
        switch (stringEvaluar){
            case "OPERADOR": return Domain.UsuarioEntity.RolUsuario.OPERADOR;
            case "CONFIGURADOR": return Domain.UsuarioEntity.RolUsuario.CONFIGURADOR;
            case "ADMINISTRADOR": return Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR;
            default: return null;
        }

    }

    public Domain.UsuarioEntity.EstadoUsuario getEnumEstadoUsuario(String stringEvaluar) {
        switch (stringEvaluar){
            case "ACTIVO": return Domain.UsuarioEntity.EstadoUsuario.ACTIVO;
            case "INACTIVO": return Domain.UsuarioEntity.EstadoUsuario.INACTIVO;
            default: return null;
        }
    }

    public Domain.Registro.TipoRegistro getEnumTipoRegistro(String stringEvaluar) {
        switch (stringEvaluar){
            case "CREACION_USUARIO": return Domain.Registro.TipoRegistro.CREACION_USUARIO;
            case "LOGIN_USUARIO": return Domain.Registro.TipoRegistro.LOGIN_USUARIO;
            case "MODIFICACION_USUARIO": return Domain.Registro.TipoRegistro.MODIFICACION_USUARIO;
            case "CREACION_SIMULACION": return Domain.Registro.TipoRegistro.CREACION_SIMULACION;
            case "INICIO_SIMULACION": return Domain.Registro.TipoRegistro.INICIO_SIMULACION;
            case "UPLOAD_ARCHIVO": return Domain.Registro.TipoRegistro.UPLOAD_ARCHIVO;
            case "CREACION_EQUIPO": return Domain.Registro.TipoRegistro.CREACION_EQUIPO;
            case "MODIFICACION_EQUIPO": return Domain.Registro.TipoRegistro.MODIFICACION_EQUIPO;
            default: return null;
        }
    }

    public Domain.ComponenteFisico.TipoComponente getEnumTipoComponente(String stringEvaluar) {
        switch (stringEvaluar){
            case "CAMARA": return Domain.ComponenteFisico.TipoComponente.CAMARA;
            case "FLUJOMETRO": return Domain.ComponenteFisico.TipoComponente.FLUJOMETRO;
            case "TERMOMETRO": return Domain.ComponenteFisico.TipoComponente.TERMOMETRO;
            case "VALVULA": return Domain.ComponenteFisico.TipoComponente.VALVULA;
            default: return null;
        }
    }

    public Domain.ComponenteFisico.EstadoComponente getEnumEstadoComponente(String stringEvaluar) {
        switch (stringEvaluar){
            case "ACTIVO": return Domain.ComponenteFisico.EstadoComponente.ACTIVO;
            case "INACTIVO": return Domain.ComponenteFisico.EstadoComponente.INACTIVO;
            case "FALLA": return Domain.ComponenteFisico.EstadoComponente.FALLA;
            case "REPARACION": return Domain.ComponenteFisico.EstadoComponente.REPARACION;
            default: return null;
        }
    }

    public Domain.Pin.ConexionPin getEnumConexionPin(String stringEvaluar) {
        switch (stringEvaluar){
            case "INPUT_ANALOGICO": return Domain.Pin.ConexionPin.INPUT_ANALOGICO;
            case "INPUT_DIGITAL": return Domain.Pin.ConexionPin.INPUT_DIGITAL;
            case "OUPUT_ANALOGICO": return Domain.Pin.ConexionPin.OUPUT_ANALOGICO;
            case "OUPUT_DIGITAL": return Domain.Pin.ConexionPin.OUPUT_DIGITAL;
            default: return null;
        }
    }


    public Domain.Pin.PlacaPin getEnumPlacaPin(String stringEvaluar) {
        switch (stringEvaluar){
            case "ARDUINO_2560": return Domain.Pin.PlacaPin.ARDUINO_2560;
            default: return null;
        }
    }

    public Domain.EstadoEquipo getEnumEstadoEquipo(String stringEvaluar) {
        switch (stringEvaluar){
            case "PROTOTIPO": return Domain.EstadoEquipo.PROTOTIPO;
            case "CONSTRUCCION": return Domain.EstadoEquipo.CONSTRUCCION;
            default: return null;
        }
    }

    public Domain.ArchivoEquipoEntityReq.TipoArchivo getEnumTipoArchivo(String stringEvaluar) {
        switch (stringEvaluar){
            case "PNG": return Domain.ArchivoEquipoEntityReq.TipoArchivo.PNG;
            case "PDF": return Domain.ArchivoEquipoEntityReq.TipoArchivo.PDF;
            case "JPG": return Domain.ArchivoEquipoEntityReq.TipoArchivo.JPG;
            case "JPEG": return Domain.ArchivoEquipoEntityReq.TipoArchivo.JPEG;
            default: return null;
        }
    }
}
