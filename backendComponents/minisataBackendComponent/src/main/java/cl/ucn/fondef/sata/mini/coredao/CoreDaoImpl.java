/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao;


import cl.ucn.fondef.sata.mini.model.Login;
import cl.ucn.fondef.sata.mini.model.RegistroUsuarios;
import cl.ucn.fondef.sata.mini.model.Usuario;
import com.mysql.cj.log.Log;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
    public boolean iniciarSesion(Login usuarioLogin) {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        // dentro de las clases de models se señala qué tabla y campo es cada clase y atributo
        String sqlQuery = "FROM Login WHERE correo = :correo";

        List <Login> listaResultado=  entityManager
                .createQuery(sqlQuery)
                .setParameter("correo", usuarioLogin.getCorreo())
                .getResultList();

        if (listaResultado.isEmpty()) {
            return false;
        }

        String contrasenaHashBaseDatos = listaResultado.get(0).getContrasena();

        // instanciar el objeto que nos permitirá comparar las contraseñas
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        // si las contraseñas son equivalentes, entonces retornaremos true
        return argon2.verify(contrasenaHashBaseDatos, usuarioLogin.getContrasena());
    }

    @Override
    public void registrarUsuario(Usuario usuario) {

    }

    @Override
    public List<RegistroUsuarios> obtenerAdmins() {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        String sqlQuery = "FROM RegistroUsuarios WHERE rutAdministrador = :rutAdministrador ";

        // dentro de las clases de models hay que señalar que se refieren a las tablas de la db
        return entityManager
                .createQuery(sqlQuery)
                .setParameter("rutAdministrador", "12345678-0")
                .getResultList();
    }
}
