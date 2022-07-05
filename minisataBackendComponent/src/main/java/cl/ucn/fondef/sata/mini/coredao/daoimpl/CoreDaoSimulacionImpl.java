/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoSimulacion;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Componente;
import cl.ucn.fondef.sata.mini.model.Evento;
import cl.ucn.fondef.sata.mini.model.Secuencia;
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
        if(listaResultado.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        return (Simulacion) listaResultado.get(0);
    }

    @Override
    public List<Simulacion> getSimulaciones(){
        String sqlQuery = "FROM Simulacion";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            log.warn("La lista no contiene elementos");
            return null;
        }
        return listaResultado;
    }


    @Override
    public String addSecuencias(SecuenciasReq secuenciasReq) {
        String nombreEquipo = secuenciasReq.getNombreEquipo();
        String rutOperador = secuenciasReq.getRutOperador();
        // Chequear si existe el nombre del equipo y el operador

        List<Domain.Secuencia> listaSecuencias = secuenciasReq.getSecuenciaList();
        for (int i = 0; i < listaSecuencias.size(); i++) {
            long idComponente = listaSecuencias.get(i).getIdComponente();
            String nombreComponente = listaSecuencias.get(i).getNombreComponente();
            // realmente necesitamos el nombre del componente?
            Secuencia secuenciaGuardar = new Secuencia();
            secuenciaGuardar.setIdComponente(idComponente);
            entityManager.persist(secuenciaGuardar);
            long idSecuencia =secuenciaGuardar.getId();

            List<Domain.Evento> listaEventos = listaSecuencias.get(i).getEventoList();
            for (int j = 0; j < listaEventos.size(); j ++) {
                Evento eventoGuardar = new Evento();
                eventoGuardar.setIntensidad(    listaEventos.get(j).getIntensidad());
                eventoGuardar.setDuracion(      listaEventos.get(j).getDuracion());
                eventoGuardar.setPosicion(      listaEventos.get(j).getPosicion());
                eventoGuardar.setIdSecuencia(   idSecuencia);
                entityManager.persist(eventoGuardar);
            }
        }
        return "good";
    }
}
