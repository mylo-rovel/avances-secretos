/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntity;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcUsuarioEntity;
import cl.ucn.fondef.sata.mini.model.*;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class CoreDaoImpl implements CoreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean credencialesSonCorrectas(String emailUsuario, String passwordUsuario) {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        // dentro de las clases de models se señala qué tabla y campo es cada clase y atributo
        try {
            String sqlQuery = "FROM Usuario WHERE email = :email";

            List listaResultado=  entityManager.createQuery(sqlQuery).setParameter("email", emailUsuario).getResultList();

            if (listaResultado.isEmpty()) { return false; }

            Usuario usuarioLogin = (Usuario) listaResultado.get(0);
            String passwordBaseDatos = usuarioLogin.getPassword();
            System.out.println("usuarioLogin = " + usuarioLogin);
            // instanciar el objeto que nos permitirá comparar las contraseñas
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

            // si las contraseñas son equivalentes, entonces retornaremos true
            return argon2.verify(passwordBaseDatos, passwordUsuario);
        }
        catch (Exception ex) {
            System.out.println("ex = " + ex);
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
    public String addUsuario(GrpcUsuarioEntity usuarioNuevo) {

        // Buscar si existe el rut o el correo => Si existe uno de los dos, existe el usuario
        String sqlQueryUsuario = "FROM Usuario WHERE email = :correoUsuario OR rut = :rutUsuario";
        List listaUsuarios=  entityManager.createQuery(sqlQueryUsuario)
                .setParameter("correoUsuario", usuarioNuevo.getEmail())
                .setParameter("rutUsuario", usuarioNuevo.getRut())
                .getResultList();

        String mensaje;
        if(listaUsuarios.isEmpty()){
            // RECORDAR QUE LAS CLASES DE /model/ REPRESENTAN LAS TABLAS DE LA DB


            // tupla para la tabla "Usuario"
            Usuario usuarioRegistrar = new Usuario();
            usuarioRegistrar.setRut(usuarioNuevo.getRut());
            usuarioRegistrar.setNombre(usuarioNuevo.getNombre());
            usuarioRegistrar.setApellido(usuarioNuevo.getApellido());

            entityManager.persist(usuarioRegistrar);

            mensaje = "El usuario ha sido ingresado existosamente";
        }else{
            mensaje = "El usuario ya existe";
        }

        return mensaje;
    }

    @Override
    public String addEquipo(String rutConfigurador, GrpcEquipoEntity equipo) {

        String mensaje = "";
        //no se me ocurre como podria saber si el equipo ya existe

        return mensaje;
    }

    @Override
    public List<Simulacion> getSimulaciones(){

        String mensaje;
        String sqlQuery = "FROM Simulacion";
        Query listaResultadoQuery = entityManager.createQuery(sqlQuery);
        List listaResultado = listaResultadoQuery.getResultList();

        if(listaResultado.isEmpty()){
            mensaje = "No se encontraron simulaciones";
        }else{
            mensaje = "Hay simulaciones";
        }
        return listaResultado;

    }

    @Override
    public Equipo getEquipo(Long idEquipo){
        String sqlQuery = "FROM Equipo WHERE id = :id";
        Query listaResultadoQuery = entityManager.createQuery(sqlQuery)
                .setParameter("id", Long.valueOf(idEquipo));
        try {
            List listaResultado = listaResultadoQuery.getResultList();
            if(listaResultado.isEmpty()){
                return null;
            }else{
                return (Equipo) listaResultado.get(0);
            }
        }
        catch (Exception ex) {
//            System.out.println("ex = " + ex);
            return null;
        }
    }
    @Override
    public Simulacion getSimulacion(int idSimulacion){
        String sqlQuery = "FROM Simulacion WHERE id = :id";
        List listaResultado = entityManager.createQuery(sqlQuery).setParameter("id", idSimulacion).getResultList();
        if(listaResultado.isEmpty()){
            return null;
        }else{
            return (Simulacion) listaResultado.get(0);
        }
    }
}
