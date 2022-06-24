/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.model.Registro;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.List;

/**
 * The type Core dao equipo.
 */
@Slf4j
@Repository
@Transactional
public class CoreDaoEquipoImpl implements CoreDaoEquipo {

    @PersistenceContext
    private EntityManager entityManager;

    //TODO: COMPLETAR EL METODO ADDEQUIPO
    @Override
    public String addEquipo(EquipoEntityReq equipoEntityReq) {
        // CHEQUEAR SI EL USUARIO EXISTE
        String usuarioQuery = "FROM Usuario WHERE rut = :rut";
        List listaUsuariosQuery = entityManager.createQuery(usuarioQuery)
                .setParameter("rut", equipoEntityReq.getRutConfigurador()).getResultList();;
        if (listaUsuariosQuery.isEmpty()) {
            return "Usuario configurador no encontrado";
        }

        // CHEQUEAR SI EL USUARIO ES CONFIGURADOR
        var usuarioEncontrado = (Usuario) listaUsuariosQuery.get(0);
        if (!(usuarioEncontrado.getRol().equals(UsuarioEntity.RolUsuario.CONFIGURADOR.name()))) {
            return "Usuario NO tiene permisos para agregar un equipo";
        }

        // CHEQUEAR SI EL NOMBRE DEL EQUIPO YA EXISTE
        String sqlQuery = "FROM Equipo WHERE nombre = :nombre";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("nombre", equipoEntityReq.getEquipo().getNombre())
                .getResultList();
        String mensaje;
        if(listaResultado.isEmpty()){
            // GUARDAR EN LA TABLA EQUIPO
            Equipo equipo = new Equipo();
            equipo.setNombre(equipoEntityReq.getEquipo().getNombre());
            equipo.setDescripcion(equipoEntityReq.getEquipo().getDescripcion());
            equipo.setRutConfigurador(equipoEntityReq.getEquipo().getUrlRepositorio());
            equipo.setRutConfigurador(equipoEntityReq.getRutConfigurador());
            equipo.setEstado(equipoEntityReq.getEquipo().getEstado().name());
            entityManager.persist(equipo);



            mensaje = "El equipo se ha agregado exitosamente";
        }else{
            mensaje = "El equipo ingresado ya existe";
        }

        return mensaje;
    }

    // TODO: AÑADIR PARTE DE REGISTRO EN LA DB
    @Override
    public String updateEquipo(EquipoEntityReq equipoEntityReq){
        Equipo equipoEditar = entityManager.find(Equipo.class, equipoEntityReq.getEquipo().getId());

        equipoEditar.setNombre(equipoEntityReq.getEquipo().getNombre());
        equipoEditar.setDescripcion(equipoEntityReq.getEquipo().getDescripcion());
        equipoEditar.setUrlRepositorio(equipoEntityReq.getEquipo().getUrlRepositorio());
        equipoEditar.setEstado(equipoEntityReq.getEquipo().getEstado().name());

        entityManager.merge(equipoEditar);

        String mensaje = "El equipo se ha actualizado exitosamente";

        return mensaje;
    }

    @Override
    public Equipo getEquipo(IdElementoReq idEquipo){
        String sqlQuery = "FROM Equipo WHERE id = :id";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("id", idEquipo.getId()).getResultList();
        if(listaResultado.isEmpty()){
            return null;
        }else{
            return (Equipo) listaResultado.get(0);
        }
    }

    @Override
    public List<Equipo> getEquipos(){
        String sqlQuery = "FROM Equipo WHERE 1=1";
        List listaResultado = entityManager.createQuery(sqlQuery).getResultList();

        if(listaResultado.isEmpty()){
            log.warn("La lista no contiene elementos");
            return null;
        }else{
            return listaResultado;
        }
    }

    //TODO: IMPLEMENTAR EN EL DAO UNA FUNCION ADD, GETCOMPONENTEFISICO Y GETCOMPONENTESFISICOS
    @Override
    public List<ComponenteFisico> getComponentesFisicosEquipo(IdElementoReq idElementoReq){
        String mensaje;
        String sqlQuery = "FROM ComponenteFisico WHERE id_equipo = :id_equipo";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId())
                .getResultList();
        if(listaResultado.isEmpty()){
            log.warn("La lista no contiene componentes fisicos");
            return null;
        }else{
            return listaResultado;
        }
    }

    @Override
    public List<ComponenteFisico> getValvulasEquipo(IdElementoReq idElementoReq){
        String mensaje;
        String sqlQuery = "FROM ComponenteFisico WHERE id_equipo = :id_equipo AND tipo = :tipo";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId())
                .setParameter("tipo", "VALVULA")
                .getResultList();
        if(listaResultado.isEmpty()){
            log.warn("La lista no contiene valvulas");
            return null;
        }else{
            return listaResultado;
        }
    }

    /*public String uploadArchivo(ArchivosEquipoEntityReq archivosEquipoEntityReq){

    }*/
}
