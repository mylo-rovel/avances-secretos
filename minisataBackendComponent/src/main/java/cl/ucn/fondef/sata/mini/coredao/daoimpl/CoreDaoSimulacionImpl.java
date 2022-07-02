/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The type Core dao simulacion.
 */
@Slf4j
@Repository
@Transactional
public class CoreDaoSimulacionImpl implements CoreDaoSimulacion {

    @PersistenceContext
    private EntityManager entityManager;

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
        String sqlQuery = "FROM Simulacion";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            log.warn("La lista no contiene elementos");
            return null;
        }else{
            return listaResultado;
        }

    }
}
