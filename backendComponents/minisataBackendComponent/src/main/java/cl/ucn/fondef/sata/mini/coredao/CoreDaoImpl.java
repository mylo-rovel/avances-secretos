/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuario;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuarioNuevo;
import cl.ucn.fondef.sata.mini.model.RegistroUsuarios;
import cl.ucn.fondef.sata.mini.model.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import cl.ucn.fondef.sata.mini.model.Login;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CoreDaoImpl implements CoreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean credencialesSonCorrectas(String correoUsuario, String contrasenaUsuario) {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        // dentro de las clases de models se señala qué tabla y campo es cada clase y atributo
        String sqlQuery = "FROM Login WHERE correo = :correo";

        List listaResultado=  entityManager.createQuery(sqlQuery).setParameter("correo", correoUsuario).getResultList();

        if (listaResultado.isEmpty()) { return false; }

        Login usuarioLogin = (Login) listaResultado.get(0);
        String contrasenaBaseDatos = usuarioLogin.getContrasena();

        // instanciar el objeto que nos permitirá comparar las contraseñas
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        // si las contraseñas son equivalentes, entonces retornaremos true
        return argon2.verify(contrasenaBaseDatos, contrasenaUsuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correoUsuario) {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        // dentro de las clases de models se señala qué tabla y campo es cada clase y atributo
        String sqlQuery = "FROM Usuario WHERE correo = :correo";
        List listaResultado=  entityManager.createQuery(sqlQuery).setParameter("correo", correoUsuario).getResultList();
        if (listaResultado.isEmpty()) { return null; }
        return (Usuario) listaResultado.get(0);
    }

    @Override
    public String anadirUsuario(String rutAdmin, GrpcUsuario usuarioNuevo) {

        // Existe la combinación rutAdmin y rutUsuario? => la combinacion es PK
        String sqlQueryRegistros = "FROM RegistroUsuarios WHERE rut_administrador = :rutAdmin AND rut_usuario = :rutUsuarioNuevo";
        List listaRegistros=  entityManager.createQuery(sqlQueryRegistros)
                .setParameter("rutAdmin", rutAdmin)
                .setParameter("rutUsuarioNuevo", usuarioNuevo.getRut())
                .getResultList();

        if( ! listaRegistros.isEmpty()){
            return "El usuario ya existe";
        }

        // Buscar si existe el rut o el correo => Si existe uno de los dos, existe el usuario
        String sqlQueryUsuario = "FROM Usuario WHERE correo = :correoUsuario OR rut = :rutUsuario";
        List listaUsuarios=  entityManager.createQuery(sqlQueryUsuario)
                .setParameter("correoUsuario", usuarioNuevo.getCorreo())
                .setParameter("rutUsuario", usuarioNuevo.getRut())
                .getResultList();
        System.out.println("listaUsuarios = " + listaUsuarios);

        String mensaje;
        if(listaUsuarios.isEmpty()){
            // RECORDAR QUE LAS CLASES DE /model/ REPRESENTAN LAS TABLAS DE LA DB
            // tupla para la tabla "RegistroUsuarios"
            RegistroUsuarios registroUsuarios = new RegistroUsuarios();
            registroUsuarios.setRutAdministrador(rutAdmin);
            registroUsuarios.setRutUsuario(usuarioNuevo.getRut());

            // tupla para la tabla "Login"
            Login loginUsuario = new Login();
            loginUsuario.setRutUsuario(usuarioNuevo.getRut());
            loginUsuario.setCorreo(usuarioNuevo.getCorreo());
            loginUsuario.setContrasena(usuarioNuevo.getContrasena());

            // tupla para la tabla "Usuario"
            Usuario usuarioRegistrar = new Usuario();
            usuarioRegistrar.setRut(usuarioNuevo.getRut());
            usuarioRegistrar.setCorreo(usuarioNuevo.getCorreo());
            usuarioRegistrar.setNombre(usuarioNuevo.getNombre());
            usuarioRegistrar.setApellido(usuarioNuevo.getApellido());
            usuarioRegistrar.setRol(usuarioNuevo.getRol());
            usuarioRegistrar.setEstado(usuarioNuevo.isEstado());

            entityManager.persist(registroUsuarios);
            entityManager.persist(loginUsuario);
            entityManager.persist(usuarioRegistrar);

            mensaje = "El usuario ha sido ingresado existosamente";
        }else{
            mensaje = "El usuario ya existe";
        }

        return mensaje;
    }
}
