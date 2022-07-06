/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Equipo;
import cl.ucn.fondef.sata.mini.model.Simulacion;
import cl.ucn.fondef.sata.mini.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.Registro;

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
    public String addRegistroCreacionSimulacion(Usuario usuarioOperador, Domain.SimulacionReq simulacion){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioOperador.getId());
        registroGuardado.setIdEntidad(3);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.CREACION_SIMULACION.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha creado la simulación {1}";
        String descripcionRegistro = MessageFormat.format(format, usuarioOperador.getId(), simulacion.getNombre());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de creacion de simulacion";
    }

    @Override
    public String addRegistroInicioSimulacion(Usuario usuarioOperador, Simulacion simulacion){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioOperador.getId());
        registroGuardado.setIdEntidad(3);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.INICIO_SIMULACION.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha iniciado la simulación";
        String descripcionRegistro = MessageFormat.format(format, usuarioOperador.getId(), simulacion.getNombre());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de inicio de simulacion";
    }

    @Override
    public String addRegistroUploadArchivo(Usuario usuario){
        return "A";
    }

    @Override
    public String addRegistroCreacionEquipo(Usuario usuario, Equipo equipo){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuario.getId());
        registroGuardado.setIdEntidad(2);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.CREACION_EQUIPO.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha creado el equipo {1}";
        String descripcionRegistro = MessageFormat.format(format, usuario.getId(), equipo.getNombre());
        registroGuardado.setDescripcion(descripcionRegistro);

        entityManager.persist(registroGuardado);

        return "Se ha realizado un registro de creacion de equipo";
    }

    @Override
    public String addRegistroModificacionEquipo(Usuario usuarioConfigurador, Equipo equipo){
        Registro registroGuardado = new Registro();
        registroGuardado.setIdUsuario(usuarioConfigurador.getId());
        registroGuardado.setIdEntidad(2);
        registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.MODIFICACION_EQUIPO.name());

        String fechaHoy = fechaHoraActual();
        /*registroGuardado.setFecha(fechaHoy);*/

        String format = "[ " + fechaHoy + " ] " + "El usuario {0} ha modificado el equipo {1}";
        String descripcionRegistro = MessageFormat.format(format, usuarioConfigurador.getId(), equipo.getNombre());
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
        String queryUsuarioRut = "FROM Usuario WHERE rut = :rut";
        List<Usuario> listaUsuarios = entityManager.createQuery(queryUsuarioRut)
                .setParameter("rut", rutEntityReq.getRut()).getResultList();
        if (listaUsuarios.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        String queryRegistros = "FROM Registro WHERE id_usuario = :id_usuario";
        List listaResultado = entityManager.createQuery(queryRegistros)
                .setParameter("id_usuario", listaUsuarios.get(0).getId()).getResultList();
        if (listaResultado.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }
}
