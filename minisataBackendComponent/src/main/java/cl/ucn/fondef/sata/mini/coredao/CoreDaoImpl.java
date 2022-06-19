/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcEquipoEntity;
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

@Repository
@Transactional
public class CoreDaoImpl implements CoreDao {

    @PersistenceContext
    private EntityManager entityManager;

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

            System.out.println("listaUsuarios = " + listaUsuariosEspecifica);
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
    public String addEquipo(EquipoEntityReq equipoEntityReq) {
        String rutConfigurador = equipoEntityReq.getEquipo().getRutConfigurador();
        EquipoEntity equipoGuardar = equipoEntityReq.getEquipo();

        String mensaje = "Guardado? idk";

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
//            System.out.println("ex = " + ex);
            return null;
        }
    }
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

    @Override
    public List<Simulacion> getSimulaciones(){
        String mensaje;
        String sqlQuery = "FROM Simulacion WHERE 1=1";
        List <Simulacion> listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            mensaje = "No se encontraron simulaciones";
        }else{
            mensaje = "Hay simulaciones";
        }
        return listaResultado;

    }
}
