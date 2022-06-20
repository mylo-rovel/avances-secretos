/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.model.Registro;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class CoreDaoImpl implements CoreDao {

    @PersistenceContext
    private EntityManager entityManager;

    //TODO: PONER REGISTROS EN LOS RESPECTIVOS METODOS

    @Override
    public boolean credencialesSonCorrectas(CredencialesEntityReq reqCredenciales) {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        // dentro de las clases de models se señala qué tabla y campo es cada clase y atributo
        String emailUsuario = reqCredenciales.getEmail();
        String passwordUsuario = reqCredenciales.getPassword();

        try {
            String sqlQuery = "FROM Usuario WHERE email = :email";
            List listaResultado=  entityManager.createQuery(sqlQuery).setParameter("email", emailUsuario).getResultList();

            if (listaResultado.isEmpty()) { return false; }

            Usuario usuarioLogin = (Usuario) listaResultado.get(0);
            String passwordBaseDatos = usuarioLogin.getPassword();

            // instanciar el objeto que nos permitirá comparar las contraseñas
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            // si las contraseñas son equivalentes, entonces retornaremos true
            return argon2.verify(passwordBaseDatos, passwordUsuario);
        }
        catch (Exception ex) {
            log.debug("ex = " + ex);
            return false;
        }
    }

    @Override
    public Usuario getUsuarioPorCorreo(String emailUsuario) {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        // dentro de las clases de models se señala qué tabla y campo es cada clase y atributo
        String sqlQuery = "FROM Usuario WHERE email = :email";
        List listaResultado=  entityManager.createQuery(sqlQuery).setParameter("email", emailUsuario).getResultList();
        if (listaResultado.isEmpty()) { return null; }
        return (Usuario) listaResultado.get(0);
    }

    @Override
    public String addUsuario(UsuarioEntityReq usuarioNuevo) {
        // Buscar si existe el rut o el correo => Si existe uno de los dos, existe el usuario
        String sqlQueryUsuario = "FROM Usuario WHERE email = :emailUsuario OR rut = :rutUsuario";
        List listaUsuarios=  entityManager.createQuery(sqlQueryUsuario)
                .setParameter("emailUsuario", usuarioNuevo.getUsuario().getEmail())
                .setParameter("rutUsuario", usuarioNuevo.getUsuario().getRut())
                .getResultList();

        String mensaje;
        if(listaUsuarios.isEmpty()){
            // RECORDAR QUE LAS CLASES DE /model/ REPRESENTAN LAS TABLAS DE LA DB
            Usuario usuarioRegistrar = new Usuario();
            usuarioRegistrar.setRut(usuarioNuevo.getUsuario().getRut());
            usuarioRegistrar.setEmail(usuarioNuevo.getUsuario().getEmail());
            usuarioRegistrar.setPassword(usuarioNuevo.getUsuario().getPassword());
            usuarioRegistrar.setNombre(usuarioNuevo.getUsuario().getNombre());
            usuarioRegistrar.setApellido(usuarioNuevo.getUsuario().getApellido());
            usuarioRegistrar.setRol(usuarioNuevo.getUsuario().getRol());
            usuarioRegistrar.setEstado(usuarioNuevo.getUsuario().getEstado());

            entityManager.persist(usuarioRegistrar);

            sqlQueryUsuario = "SELECT '*' FROM Usuario WHERE email = :emailUsuario AND rut = :rutUsuario";
            //noinspection unchecked
            List<Usuario> listaUsuariosEspecifica = entityManager.createQuery(sqlQueryUsuario)
                    .setParameter("emailUsuario", usuarioRegistrar.getEmail())
                    .setParameter("rutUsuario", usuarioRegistrar.getRut())
                    .getResultList();

            log.info("listaUsuarios = " + listaUsuariosEspecifica);
            long idUsuario = listaUsuariosEspecifica.get(0).getId();

            Registro registroGuardado = new Registro();
            registroGuardado.setIdUsuario(idUsuario);
            registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.CREACION_USUARIO);

            String format = "El rut {0} registra al rut {1} con el rol {2}";
            String descripcionRegistro = MessageFormat.format(format,usuarioNuevo.getRutAdministrador(), usuarioRegistrar.getRut(), usuarioRegistrar.getRol());
            registroGuardado.setDescripcion(descripcionRegistro);
            registroGuardado.setIdEntidad(1);

            entityManager.persist(registroGuardado);

            mensaje = "El usuario ha sido ingresado existosamente";
        }else{
            mensaje = "El usuario ya existe";
        }

        return mensaje;
    }
    @Override
    public Usuario getUsuario(RutEntityReq rutEntityReq){
        String sqlQuery = "FROM Usuario WHERE rut = :rut";
        Query listaResultadoQuery = entityManager.createQuery(sqlQuery)
                .setParameter("rut", rutEntityReq.getRut());
        try{
            List listaResultado = listaResultadoQuery.getResultList();
            if(listaResultado.isEmpty()){
                return null;
            }else{
                return (Usuario) listaResultado.get(0);
            }
        }
        catch (Exception ex) {
            log.debug("ex = " + ex);
            return null;
        }
    }

    //TODO: IMPLEMENTAR UN SYSTEMOUTPRINT PARA INDICAR QUE LA LISTA DE GETUSUARIOS ESTA VACIA
    @Override
    public List<Usuario> getUsuarios(){
        String sqlQuery = "FROM Usuario WHERE 1=1";
        List<Usuario> listaResultadoQuery = entityManager.createQuery(sqlQuery).getResultList();
        if(listaResultadoQuery.isEmpty()){
            return null;
        }else{
            return listaResultadoQuery;
        }
    }
    @Override
    public String setUsuario(UsuarioEntityReq usuarioEntityReq){
        /*
        String sqlQuery = "FROM Usuario WHERE rut = :rut";
        Query listaResultadoQuery = entityManager.createQuery(sqlQuery)
                .setParameter("rut", usuarioEntityReq.getUsuario().getRut());
        try{
            List listaResultado = listaResultadoQuery.getResultList();
            if(listaResultado.isEmpty()){
                return null;
            }else{
                Usuario usuarioEditar = new Usuario();
                usuarioEditar.setNombre(usuarioEntityReq.getUsuario().getNombre());
                usuarioEditar.setApellido(usuarioEntityReq.getUsuario().getApellido());
                usuarioEditar.setEmail(usuarioEntityReq.getUsuario().getEmail());
                usuarioEditar.setPassword(usuarioEntityReq.getUsuario().getPassword());
                usuarioEditar.setEstado(usuarioEntityReq.getUsuario().getEstado());
                usuarioEditar.setRol(usuarioEntityReq.getUsuario().getRol());


            }
        }
         */

        /*
        RutEntityReq rutUsuarioBuscado = RutEntityReq.newBuilder()
                                            .setRut(usuarioEntityReq.getUsuario().getRut())
                                            .build();

        Usuario usuarioEditar = this.getUsuario(rutUsuarioBuscado);
        usuarioEditar.setNombre(usuarioEntityReq.getUsuario().getNombre());
        usuarioEditar.setApellido(usuarioEntityReq.getUsuario().getApellido());
        usuarioEditar.setEmail(usuarioEntityReq.getUsuario().getEmail());
        usuarioEditar.setPassword(usuarioEntityReq.getUsuario().getPassword());
        usuarioEditar.setEstado(usuarioEntityReq.getUsuario().getEstado());
        usuarioEditar.setRol(usuarioEntityReq.getUsuario().getRol());
        */

        /*
        String sqlQuery = "UPDATE Usuario " +
                "SET nombre = :nombre, apellido = :apellido, email = :email, password = :password, estado = :estado, rol = :rol " +
                "WHERE rut = :rut";
        Query listaResultadoQuery = entityManager.createQuery(sqlQuery)
                .setParameter("nombre", usuarioEntityReq.getUsuario().getNombre())
                .setParameter("apellido", usuarioEntityReq.getUsuario().getApellido())
                .setParameter("email", usuarioEntityReq.getUsuario().getEmail())
                .setParameter("password", usuarioEntityReq.getUsuario().getPassword())
                .setParameter("estado", usuarioEntityReq.getUsuario().getEstado())
                .setParameter("rol", usuarioEntityReq.getUsuario().getRol());

        */

        Usuario usuarioEditar = entityManager.find(Usuario.class, usuarioEntityReq.getUsuario().getRut());

        usuarioEditar.setNombre(usuarioEntityReq.getUsuario().getNombre());
        usuarioEditar.setApellido(usuarioEntityReq.getUsuario().getApellido());
        usuarioEditar.setEmail(usuarioEntityReq.getUsuario().getEmail());
        usuarioEditar.setPassword(usuarioEntityReq.getUsuario().getPassword());
        usuarioEditar.setEstado(usuarioEntityReq.getUsuario().getEstado());
        usuarioEditar.setRol(usuarioEntityReq.getUsuario().getRol());

        entityManager.merge(usuarioEditar);

        String mensaje = "el usuario ha sido actualizado exitosamente";

        return mensaje;
    }

    //TODO: COMPLETAR EL METODO ADDEQUIPO
    @Override
    public String addEquipo(EquipoEntityReq equipoEntityReq) {
        String sqlQuery = "FROM Equipo WHERE nombre = :nombre";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("nombre", equipoEntityReq.getEquipo().getNombre())
                .getResultList();
        String mensaje;
        if(listaResultado.isEmpty()){
            Equipo equipo = new Equipo();
            equipo.setNombre(equipoEntityReq.getEquipo().getNombre());
            equipo.setDescripcion(equipoEntityReq.getEquipo().getDescripcion());
            equipo.setRutConfigurador(equipoEntityReq.getEquipo().getUrlRepositorio());
            equipo.setRutConfigurador(equipoEntityReq.getRutConfigurador());
            equipo.setEstado(equipoEntityReq.getEquipo().getEstado().toString());

            entityManager.persist(equipo);

            mensaje = "El equipo se ha agregado exitosamente";
        }else{
            mensaje = "El equipo ingresado ya existe";
        }

        return mensaje;
    }
    @Override
    public String setEquipo(EquipoEntityReq equipoEntityReq){
        Equipo equipoEditar = entityManager.find(Equipo.class, equipoEntityReq.getEquipo().getId());

        equipoEditar.setNombre(equipoEntityReq.getEquipo().getNombre());
        equipoEditar.setDescripcion(equipoEntityReq.getEquipo().getDescripcion());
        equipoEditar.setUrlRepositorio(equipoEntityReq.getEquipo().getUrlRepositorio());
        equipoEditar.setEstado(equipoEntityReq.getEquipo().getEstado().toString());

        entityManager.merge(equipoEditar);

        String mensaje = "El equipo se ha actualizado exitosamente";

        return mensaje;
    }

    @Override
    public Equipo getEquipo(IdElementoReq idEquipo){
        String sqlQuery = "FROM Equipo WHERE id = :id";
        Query listaResultadoQuery = entityManager.createQuery(sqlQuery)
                .setParameter("id", idEquipo.getId());
        try {
            List listaResultado = listaResultadoQuery.getResultList();
            if(listaResultado.isEmpty()){
                return null;
            }else{
                return (Equipo) listaResultado.get(0);
            }
        }
        catch (Exception ex) {
            log.debug("ex = " + ex);
            return null;
        }
    }

    //TODO: IMPLEMENTAR UN SYSTEMOUTPRINT PARA INDICAR QUE LA LISTA DE GETEQUIPOS ESTA VACIA
    @Override
    public List<Equipo> getEquipos(){
        String mensaje;
        String sqlQuery = "FROM Equipo WHERE 1=1";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            mensaje = "No se han encontrado equipos";
        }else{
            mensaje = "Se han encontrado equipos";
        }

        return listaResultado;
    }

    /*public String uploadArchivo(ArchivosEquipoEntityReq archivosEquipoEntityReq){

    }*/

    @Override
    public Simulacion getSimulacion(IdElementoReq idElementoReq){
        String sqlQuery = "FROM Simulacion WHERE id = :idSimulacion";

        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("idSimulacion", idElementoReq.getId()).getResultList();
        if(listaResultado.isEmpty()){
            return null;
        }else{
            return (Simulacion) listaResultado.get(0);
        }
    }

    //TODO: IMPLEMENTAR UN SYSTEMOUTPRINT PARA INDICAR QUE LA LISTA DE GETSIMULACIONES ESTA VACIA
    @Override
    public List<Simulacion> getSimulaciones(){
        String mensaje;
        String sqlQuery = "FROM Simulacion WHERE 1=1";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            mensaje = "No se encontraron simulaciones";
        }else{
            mensaje = "Hay simulaciones";
        }
        return listaResultado;

    }

    //TODO: IMPLEMENTAR EN EL DAO UNA FUNCION ADD, GETCOMPONENTEFISICO Y GETCOMPONENTESFISICOS

    @Override
    public List<ComponenteFisico> getComponentesFisicosEquipo(IdElementoReq idElementoReq){
        String mensaje;
        String sqlQuery = "FROM ComponenteFisico WHERE id_equipo = :id_equipo";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId())
                .getResultList();
        if(listaResultado.isEmpty()){
            mensaje = "No se encontraron componentes fisicos";
        }else{
            mensaje = "Hay componentes fisicos";
        }
        return listaResultado;
    }
}
