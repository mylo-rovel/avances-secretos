/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
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

/**
 * The type Core dao extra.
 */
@Slf4j
@Repository
@Transactional
public class CoreDaoExtraImpl implements CoreDaoExtra {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Registro> getRegistros(Domain.RutEntityReq rutEntityReq) {
        String queryUsuarioRut = "FROM Usuario WHERE rut = :rut";
        List<ComponenteFisico> listaUsuarios = entityManager.createQuery(queryUsuarioRut)
                .setParameter("rut", rutEntityReq.getRut()).getResultList();

        String queryRegistros = "FROM Registro WHERE id_usuario = :id_usuario";
        return (List<Registro>) entityManager.createQuery(queryRegistros)
                .setParameter("id_usuario", listaUsuarios.get(0).getId()).getResultList();
    }
}
