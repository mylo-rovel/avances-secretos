package com.backendCore.gRPCcore.dao;

import com.backendCore.gRPCcore.models.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CoreDaoImpl implements CoreDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        // Usuario => con mayuscula porque se refiere a la clase models/Usuario
        String sqlQuery = "FROM Usuario";
        // dentro de las clases de models hay que señalar que se refieren a las tablas de la db
        List <Usuario> resultado = entityManager.createQuery(sqlQuery).getResultList();
        return resultado;
    }

    @Override
    public void inhabilitarUsuario(int id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }
}
