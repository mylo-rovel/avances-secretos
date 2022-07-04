package cl.ucn.fondef.sata.mini.utilities;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import org.springframework.stereotype.Service;

/**
 * The type String enum transformer.
 */
@Service
public class StringEnumTransformer {

    /**
     * Gets enum rol usuario.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum rol usuario
     */
    public Domain.UsuarioEntity.RolUsuario getEnumRolUsuario(String stringEvaluar) {
        switch (stringEvaluar){
            case "OPERADOR": return Domain.UsuarioEntity.RolUsuario.OPERADOR;
            case "CONFIGURADOR": return Domain.UsuarioEntity.RolUsuario.CONFIGURADOR;
            case "ADMINISTRADOR": return Domain.UsuarioEntity.RolUsuario.ADMINISTRADOR;
            default: return null;
        }

    }

    /**
     * Gets enum estado usuario.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum estado usuario
     */
    public Domain.UsuarioEntity.EstadoUsuario getEnumEstadoUsuario(String stringEvaluar) {
        switch (stringEvaluar){
            case "ACTIVO": return Domain.UsuarioEntity.EstadoUsuario.ACTIVO;
            case "INACTIVO": return Domain.UsuarioEntity.EstadoUsuario.INACTIVO;
            default: return null;
        }
    }

    /**
     * Gets enum tipo registro.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum tipo registro
     */
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

    /**
     * Gets enum tipo componente.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum tipo componente
     */
    public Domain.Componente.TipoComponente getEnumTipoComponente(String stringEvaluar) {
        switch (stringEvaluar){
            case "CAMARA": return Domain.Componente.TipoComponente.CAMARA;
            case "FLUJOMETRO": return Domain.Componente.TipoComponente.FLUJOMETRO;
            case "TERMOMETRO": return Domain.Componente.TipoComponente.TERMOMETRO;
            case "VALVULA": return Domain.Componente.TipoComponente.VALVULA;
            default: return null;
        }
    }

    /**
     * Gets enum estado componente.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum estado componente
     */
    public Domain.Componente.EstadoComponente getEnumEstadoComponente(String stringEvaluar) {
        switch (stringEvaluar){
            case "ACTIVO": return Domain.Componente.EstadoComponente.ACTIVO;
            case "INACTIVO": return Domain.Componente.EstadoComponente.INACTIVO;
            case "FALLA": return Domain.Componente.EstadoComponente.FALLA;
            case "REPARACION": return Domain.Componente.EstadoComponente.REPARACION;
            default: return null;
        }
    }

    /**
     * Gets enum conexion pin.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum conexion pin
     */
    public Domain.Pin.ConexionPin getEnumConexionPin(String stringEvaluar) {
        switch (stringEvaluar){
            case "INPUT_ANALOGICO": return Domain.Pin.ConexionPin.INPUT_ANALOGICO;
            case "INPUT_DIGITAL": return Domain.Pin.ConexionPin.INPUT_DIGITAL;
            case "OUTPUT_ANALOGICO": return Domain.Pin.ConexionPin.OUTPUT_ANALOGICO;
            case "OUTPUT_DIGITAL": return Domain.Pin.ConexionPin.OUTPUT_DIGITAL;
            default: return null;
        }
    }


    /**
     * Gets enum placa pin.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum placa pin
     */
    public Domain.TipoPlaca getEnumTipoPlaca(String stringEvaluar) {
        switch (stringEvaluar){
            case "ARDUINO_2560": return Domain.TipoPlaca.ARDUINO_2560;
            case "ESP_32": return Domain.TipoPlaca.ESP_32;
            default: return null;
        }
    }

    /**
     * Gets enum estado equipo.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum estado equipo
     */
    public Domain.EstadoEquipo getEnumEstadoEquipo(String stringEvaluar) {
        switch (stringEvaluar){
            case "PROTOTIPO": return Domain.EstadoEquipo.PROTOTIPO;
            case "CONSTRUCCION": return Domain.EstadoEquipo.CONSTRUCCION;
            default: return null;
        }
    }

    /**
     * Gets enum tipo archivo.
     *
     * @param stringEvaluar the string evaluar
     * @return the enum tipo archivo
     */
    public Domain.ArchivoEntity.TipoArchivo getEnumTipoArchivo(String stringEvaluar) {
        switch (stringEvaluar){
            case "PNG": return Domain.ArchivoEntity.TipoArchivo.PNG;
            case "PDF": return Domain.ArchivoEntity.TipoArchivo.PDF;
            case "JPG": return Domain.ArchivoEntity.TipoArchivo.JPG;
            case "JPEG": return Domain.ArchivoEntity.TipoArchivo.JPEG;
            default: return null;
        }
    }
}
