/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.grpcobjects.GrpcPlaca;
import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Pin;
import cl.ucn.fondef.sata.mini.model.Placa;
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
import java.util.HashMap;
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
                .setParameter("nombre", equipoEntityReq.getEquipo().getNombre()).getResultList();

        String mensaje;
        // Si aun no existe el nombre:
        if(listaResultado.isEmpty()){
            // GUARDAR EN LA TABLA EQUIPO
            Equipo equipo = new Equipo();
            equipo.setIdConfigurador(usuarioEncontrado.getId());
            equipo.setNombre(equipoEntityReq.getEquipo().getNombre());
            equipo.setDescripcion(equipoEntityReq.getEquipo().getDescripcion());
            equipo.setUrlRepositorio(equipoEntityReq.getEquipo().getUrlRepositorio());
            equipo.setEstado(equipoEntityReq.getEquipo().getEstado().name());
            entityManager.persist(equipo);

            String queryEquipo = "FROM Equipo WHERE nombre = :nombre";
            List<Equipo> equiposGuardados = entityManager.createQuery(sqlQuery)
                    .setParameter("nombre", equipoEntityReq.getEquipo().getNombre()).getResultList();
            long idEquipo = equiposGuardados.get(0).getId();
            String nombreEquipo = equiposGuardados.get(0).getNombre();

            // este hashmap se utiliza para guardar las id de las placas para
            // "eficiente" iteracion en el for loop y posterior guardado
            HashMap<String, Long> placasIdHashMap = new HashMap<String, Long>();

            // for loop para guardar las placas
            List<Domain.Placa> listaPlacas = equipoEntityReq.getEquipo().getPlacaList();
            for (int i = 0; i < listaPlacas.size(); i++) {
                Placa placaGuardar = new Placa();
                placaGuardar.setIdEquipo(idEquipo);
                placaGuardar.setNombre(        listaPlacas.get(i).getNombre());
                placaGuardar.setDescripcion(   listaPlacas.get(i).getDescripcion());
                placaGuardar.setTipo(          listaPlacas.get(i).getTipo().name());

                entityManager.persist(placaGuardar);

                // guardando la llave "tipoPlaca" para, posteriormente acceder a su valor
                // el valor long -1 es temporal
                placasIdHashMap.put(    listaPlacas.get(i).getTipo().name(), -1L);
            }

           // TODO: OPTIMIZAR PARA OBTENER LA ID SIN HACER OTRA QUERY
            // Obtenemos las id de las placas del equipo a guardado para reemplazar
            // el valor temporal que dejamos anteriormente: long -1
            String queryPlacas = "FROM Placa WHERE id_equipo = :id_equipo";
            List<Placa> placasGuardadas = entityManager.createQuery(queryPlacas)
                    .setParameter("id_equipo", idEquipo).getResultList();

            for (int i = 0; i < placasGuardadas.size(); i++) {
                Placa placaTurno = placasGuardadas.get(i);
                placasIdHashMap.replace(placaTurno.getTipo(), placaTurno.getId());
            }

            // for loop para guardar CADA componentes
            List<Domain.ComponenteFisico> listaComponentes = equipoEntityReq.getEquipo().getComponenteFisicoList();
            for (int i = 0; i < listaComponentes.size(); i++) {
                ComponenteFisico componenteFisico = new ComponenteFisico();
                componenteFisico.setNombre(     listaComponentes.get(i).getNombre() + "_" + nombreEquipo);
                componenteFisico.setDescripcion(listaComponentes.get(i).getDescripcion());
                componenteFisico.setUrl(        listaComponentes.get(i).getUrl());
                componenteFisico.setEstado(     listaComponentes.get(i).getEstado().name());
                componenteFisico.setTipo(       listaComponentes.get(i).getTipo().name());

                entityManager.persist(componenteFisico);

                String queryComponente = "FROM ComponenteFisico WHERE nombre = :nombre";
                List<ComponenteFisico> componentesGuardados = entityManager.createQuery(queryComponente)
                        .setParameter("nombre", componenteFisico.getNombre()).getResultList();

                long idComponente_pin = componentesGuardados.get(0).getId();
                long idPlaca_pin = placasIdHashMap.get(listaComponentes.get(i).getTipoPlaca().name());

                // for loop para guardar los pines
                List<Domain.Pin> listaPines = listaComponentes.get(i).getPinComponenteList();
                for (int j = 0; j < listaPines.size(); j++) {
                    Pin pin = new Pin();
                    pin.setNumero(      listaPines.get(j).getNumero());
                    pin.setNombre(      listaPines.get(j).getNombre());
                    pin.setDescripcion( listaPines.get(j).getDescripcion());
                    pin.setConexion(    listaPines.get(j).getConexion().name());
                    pin.setIdPlaca(     idPlaca_pin);
                    pin.setIdComponente(idComponente_pin);

                    entityManager.persist(pin);
                }
            }

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


    // ---- AUX PARA getEquipo ----
    public List<Placa> getPlacas(IdElementoReq idEquipo) {
        String sqlQuery = "FROM Placa WHERE id_equipo = :id_equipo";
        return entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idEquipo.getId()).getResultList();
    }

    public List<Pin> getPinesPorPlaca(long idPlaca) {
        String sqlQuery = "FROM Pin WHERE id_placa = :id_placa";
        return entityManager.createQuery(sqlQuery)
                .setParameter("id_placa", idPlaca).getResultList();
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
        String sqlQuery = "FROM ComponenteFisico WHERE id = :id";
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
    public ComponenteFisico getComponenteFisico(IdElementoReq idElementoReq){
        String sqlQuery = "FROM ComponenteFisico WHERE id = :id";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("id", idElementoReq.getId())
                .getResultList();
        if(listaResultado.isEmpty()){
            log.warn("no se ha encontrado ningun componente fisico");
            return null;
        }else{
            return (ComponenteFisico) listaResultado.get(0);
        }
    }

    @Override
    public List<ComponenteFisico> getValvulasEquipo(IdElementoReq idElementoReq){
        String mensaje;
        String sqlQuery = "FROM ComponenteFisico WHERE id = :id AND tipo = :tipo";
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
