/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
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
import java.text.MessageFormat;
import java.util.List;

/**
 * The type Core dao usuario.
 */
@Slf4j
@Repository
@Transactional
public class CoreDaoUsuarioImpl implements CoreDaoUsuario {

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
            String sqlQuery = "FROM Usuario WHERE email = :email AND estado = 'ACTIVO' ";
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
        String sqlQuery = "FROM Usuario WHERE email = :email AND estado = 'ACTIVO' ";
        List listaResultado=  entityManager.createQuery(sqlQuery).setParameter("email", emailUsuario).getResultList();
        if (listaResultado.isEmpty()) { return null; }
        return (Usuario) listaResultado.get(0);
    }

    @Override
    public String addUsuario(UsuarioEntityReq usuarioNuevo) {
        // Buscar si existe el rut o el correo => Si existe uno de los dos, existe el usuario
        String sqlQueryUsuario = "FROM Usuario WHERE email = :emailUsuario OR rut = :rutUsuario AND estado = 'ACTIVO' ";
        List listaUsuarios=  entityManager.createQuery(sqlQueryUsuario)
                .setParameter("emailUsuario", usuarioNuevo.getUsuario().getEmail())
                .setParameter("rutUsuario", usuarioNuevo.getUsuario().getRut())
                .getResultList();
        log.info("Cantidad de usuarios matcheados: "+ listaUsuarios.size());
        String mensaje;
        if(listaUsuarios.isEmpty()){
            // RECORDAR QUE LAS CLASES DE /model/ REPRESENTAN LAS TABLAS DE LA DB
            Usuario usuarioRegistrar = new Usuario();
            usuarioRegistrar.setRut(usuarioNuevo.getUsuario().getRut());
            usuarioRegistrar.setEmail(usuarioNuevo.getUsuario().getEmail());
            usuarioRegistrar.setPassword(usuarioNuevo.getUsuario().getPassword());
            usuarioRegistrar.setNombre(usuarioNuevo.getUsuario().getNombre());
            usuarioRegistrar.setApellido(usuarioNuevo.getUsuario().getApellido());

            usuarioRegistrar.setRol(usuarioNuevo.getUsuario().getRol().name());
            usuarioRegistrar.setEstado(usuarioNuevo.getUsuario().getEstado().name());
            entityManager.persist(usuarioRegistrar);

            sqlQueryUsuario = "FROM Usuario WHERE email = :emailUsuario AND rut = :rutUsuario AND estado = 'ACTIVO' ";
            //noinspection unchecked
            List<Usuario> listaUsuariosEspecifica = entityManager.createQuery(sqlQueryUsuario)
                    .setParameter("emailUsuario", usuarioRegistrar.getEmail())
                    .setParameter("rutUsuario", usuarioRegistrar.getRut())
                    .getResultList();

            long idUsuario = listaUsuariosEspecifica.get(0).getId();

            Registro registroGuardado = new Registro();
            registroGuardado.setIdUsuario(idUsuario);
            registroGuardado.setTipoRegistro(Domain.Registro.TipoRegistro.CREACION_USUARIO.name());

            String format = "El rut {0} registra al rut {1} con el rol {2}";
            String descripcionRegistro = MessageFormat.format(format,usuarioNuevo.getRutAdministrador(), usuarioRegistrar.getRut(), usuarioRegistrar.getRol());
            registroGuardado.setDescripcion(descripcionRegistro);
            registroGuardado.setIdEntidad(1);

            entityManager.persist(registroGuardado);

            mensaje = "El usuario ha sido ingresado existosamente";
        }else{
            mensaje = "El rut o email del usuario ya existe";
        }

        return mensaje;
    }

    @Override
    public String updateUsuario(UsuarioEntityReq usuarioEntityReq){
        String sqlQuery = "FROM Usuario WHERE rut = :rut";
        List listaUsuarios = entityManager.createQuery(sqlQuery).setParameter("rut", usuarioEntityReq.getUsuario().getRut()).getResultList();
        if (listaUsuarios.isEmpty()) {
            return "No se pudo actualizar. No existe el usuario";
        }
        Usuario usuarioEncontrado = (Usuario) listaUsuarios.get(0);

        Usuario usuarioEditar = entityManager.find(Usuario.class, usuarioEncontrado.getId());

        usuarioEditar.setNombre(usuarioEntityReq.getUsuario().getNombre());
        usuarioEditar.setApellido(usuarioEntityReq.getUsuario().getApellido());
        usuarioEditar.setEmail(usuarioEntityReq.getUsuario().getEmail());
        usuarioEditar.setPassword(usuarioEntityReq.getUsuario().getPassword());
        usuarioEditar.setEstado(usuarioEntityReq.getUsuario().getEstado().name());
        usuarioEditar.setRol(usuarioEntityReq.getUsuario().getRol().name());

        String mensaje;
        try {
            entityManager.merge(usuarioEditar);
            mensaje = "El usuario ha sido actualizado exitosamente";
        }
        catch (IllegalArgumentException ex) {
            mensaje = "No se pudo actualizar";
        }
        return mensaje;
    }

    @Override
    public Usuario getUsuario(RutEntityReq rutEntityReq){
        String sqlQuery = "FROM Usuario WHERE rut = :rut AND estado = 'ACTIVO' ";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("rut", rutEntityReq.getRut()).getResultList();
        if(listaResultado.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        return (Usuario) listaResultado.get(0);
    }

    @Override
    public List<Usuario> getUsuarios(){
        String sqlQuery = "FROM Usuario WHERE 1=1 AND estado = 'ACTIVO' ";
        return (List<Usuario>) entityManager.createQuery(sqlQuery).getResultList();
    }
}