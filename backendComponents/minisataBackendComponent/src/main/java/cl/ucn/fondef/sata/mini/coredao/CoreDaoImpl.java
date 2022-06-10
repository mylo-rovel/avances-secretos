/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;

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

        List <Login> listaResultado=  entityManager.createQuery(sqlQuery).setParameter("correo", correoUsuario).getResultList();

        if (listaResultado.isEmpty()) { return false; }

        String contrasenaBaseDatos = listaResultado.get(0).getContrasena();

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
        List <Usuario> listaResultado=  entityManager.createQuery(sqlQuery).setParameter("correo", correoUsuario).getResultList();
        if (listaResultado.isEmpty()) { return null; }
        return listaResultado.get(0);
    }

}
