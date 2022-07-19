/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * The type Core dao extra.
 */
@Slf4j
@Repository
@Transactional
public class CoreDaoExtraImpl implements CoreDaoExtra {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * The constant formatoFecha.
     */
    public static final String formatoFecha = "dd-MM-yyyy HH:mm:ss";

    @Override
    public String fechaHoraActual(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
        return sdf.format(cal.getTime());
    }

    @Override
    public String addRegistroCreacionUsuario(Usuario usuarioRegistrar, Usuario usuarioAdministrador){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioRegistrar.getId());
        registroGuardado.setIdEntidad(1);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.CREACION_USUARIO.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ "+ fechaHoy + " ] " + "El rut {0} registra al rut {1} con el rol {2}";
        String descripcionRegistro = MessageFormat.format(format,usuarioAdministrador.getRut(),
                usuarioRegistrar.getRut(), usuarioRegistrar.getRol());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de creación de usuario";
    }

    @Override
    public String addRegistroLoginUsuario(Usuario usuarioLogin){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioLogin.getId());
        registroGuardado.setIdEntidad(1);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.LOGIN_USUARIO.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha iniciado sesion";
        String descripcionRegistro = MessageFormat.format(format, usuarioLogin.getRut());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de login de usuario";
    }

    @Override
    public String addRegistroModificacionUsuario(Usuario usuarioUpdate, Usuario usuarioAdministrador){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioUpdate.getId());
        registroGuardado.setIdEntidad(1);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.MODIFICACION_USUARIO.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha sido modificado por {1}";
        String descripcionRegistro = MessageFormat.format(format, usuarioUpdate.getRut(), usuarioAdministrador.getRut());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de modificacion de usuario";
    }

    //TODO: CONSIDERAR SI SE DEBERIA INCLUIR LA SIMULACION ESPECIFICA EN LA DESCRIPCION
    @Override
    public String addRegistroCreacionSimulacion(Usuario usuarioOperador){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioOperador.getId());
        registroGuardado.setIdEntidad(3);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.CREACION_SIMULACION.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha creado una simulación";
        String descripcionRegistro = MessageFormat.format(format, usuarioOperador.getId());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de creacion de simulacion";
    }

    @Override
    public String addRegistroInicioSimulacion(Usuario usuarioOperador){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioOperador.getId());
        registroGuardado.setIdEntidad(3);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.INICIO_SIMULACION.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha iniciado una simulación";
        String descripcionRegistro = MessageFormat.format(format, usuarioOperador.getId());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de inicio de simulacion";
    }

    @Override
    public String addRegistroUploadArchivo(Usuario usuario){
        return "A";
    }

    @Override
    public String addRegistroCreacionEquipo(Usuario usuario){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuario.getId());
        registroGuardado.setIdEntidad(2);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.CREACION_EQUIPO.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha creado un equipo";
        String descripcionRegistro = MessageFormat.format(format, usuario.getId());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de creacion de equipo";
    }

    @Override
    public String addRegistroModificacionEquipo(Usuario usuarioConfigurador){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioConfigurador.getId());
        registroGuardado.setIdEntidad(2);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.MODIFICACION_EQUIPO.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha modificado un equipo";
        String descripcionRegistro = MessageFormat.format(format, usuarioConfigurador.getId());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de modificacion de equipo";
    }
    /*
    public String addRegistro(Registro registroNuevo){
        String sqlQuery = "FROM Registro WHERE id = :id";
        List<Registro> listaRegistros = entityManager.createQuery(sqlQuery)
                .setParameter("id", registroNuevo.getId()).getResultList();
        if(listaRegistros.isEmpty()){
            Registro registroGuardar = new Registro();
            registroGuardar.setId(registroNuevo.getId());
            registroGuardar.setIdUsuario(registroNuevo.getIdUsuario());
            registroGuardar.setIdEntidad(registroNuevo.getIdEntidad());
            registroGuardar.setFecha(registroNuevo.getFecha());
            registroGuardar.setDescripcion(registroNuevo.getDescripcion());
            registroGuardar.setTipoRegistro(registroNuevo.getTipoRegistro());

        }

        return "a";
    }
    */

    @Override
    public List<Registro> getRegistros(Domain.RutEntityReq rutEntityReq) {
        String queryUsuarioRut = "SELECT usuario FROM Usuario usuario WHERE usuario.rut = :rut";
        List<Usuario> listaUsuarios = entityManager.createQuery(queryUsuarioRut)
                .setParameter("rut", rutEntityReq.getRut()).getResultList();
        if (listaUsuarios.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        String queryRegistros = "SELECT registro FROM Registro as registro WHERE registro.idUsuario = :id_usuario";
        List listaResultado = entityManager.createQuery(queryRegistros)
                .setParameter("id_usuario", listaUsuarios.get(0).getId()).getResultList();
        if (listaResultado.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }

    @Override
    public String getInnerJoin() {
        String query =
                "select secuencia, evento from  Secuencia as secuencia, Evento as evento " +
                        "where secuencia.idSimulacion = 3 AND secuencia.id = evento.idSecuencia";
        List<Object[]> results = entityManager.createQuery(query).getResultList();
        System.out.println("results = " + results);
        for (Object[] secuenciaANDevento: results) {
            System.out.println("\n");
            Secuencia secuencia = (Secuencia) secuenciaANDevento[0];
            Evento evento = (Evento) secuenciaANDevento[1];
            System.out.println("secuencia = " + secuencia);
            System.out.println("evento = " + evento);
        }
        return "hola";
    }
}
