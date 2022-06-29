/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.ComponenteFisico;
import cl.ucn.fondef.sata.mini.model.Evento;
import cl.ucn.fondef.sata.mini.model.Pin;
import cl.ucn.fondef.sata.mini.model.Placa;
import cl.ucn.fondef.sata.mini.model.Secuencia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    private List<Usuario> getListaUsuariosPorRut(EquipoEntityReq equipoEntityReq) {
        String usuarioQuery = "FROM Usuario WHERE rut = :rut";
        return (List<Usuario>) entityManager.createQuery(usuarioQuery)
                .setParameter("rut", equipoEntityReq.getRutConfigurador()).getResultList();

    }

    private String isUnableToSave(EquipoEntityReq equipoEntityReq) {
        // CHEQUEAR SI EL USUARIO EXISTE
        List<Usuario> listaUsuariosQuery = this.getListaUsuariosPorRut(equipoEntityReq);
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
        if(!listaResultado.isEmpty()){
            return "El equipo ingresado ya existe";
        }
        return null;
    }

    private  HashMap<String, String> getDatosEquipoHashMap (EquipoEntityReq equipoEntityReq) {
        String queryEquipo = "FROM Equipo WHERE nombre = :nombre";
        List<Equipo> equiposGuardados = entityManager.createQuery(queryEquipo)
                .setParameter("nombre", equipoEntityReq.getEquipo().getNombre()).getResultList();

        HashMap<String, String> datosEquipo = new HashMap<String, String>();
        datosEquipo.put("idEquipo", "" + equiposGuardados.get(0).getId());
        datosEquipo.put("nombreEquipo",  equiposGuardados.get(0).getNombre());
        return datosEquipo;
    }

    private void guardarEquipo(EquipoEntityReq equipoEntityReq) {
        Usuario usuarioConfigurador = this.getListaUsuariosPorRut(equipoEntityReq).get(0);
        Equipo equipo = new Equipo();
        equipo.setIdConfigurador(   usuarioConfigurador.getId());
        equipo.setNombre(           equipoEntityReq.getEquipo().getNombre());
        equipo.setDescripcion(      equipoEntityReq.getEquipo().getDescripcion());
        equipo.setUrlRepositorio(   equipoEntityReq.getEquipo().getUrlRepositorio());
        equipo.setEstado(           equipoEntityReq.getEquipo().getEstado().name());
        entityManager.persist(equipo);
    }

    private HashMap<String, Long> guardarPlacas(EquipoEntityReq equipoEntityReq, long idEquipo) {
        // LO QUE RETORNAREMOS
        HashMap<String, Long> placasIdHashMap = new HashMap<String, Long>();

        // for loop para guardar las placas
        List<Domain.Placa> listaPlacas = equipoEntityReq.getEquipo().getPlacaList();
        for (int i = 0; i < listaPlacas.size(); i++) {
            Placa placaGuardar = new Placa();
            placaGuardar.setIdEquipo(       idEquipo);
            placaGuardar.setNombre(         listaPlacas.get(i).getNombre());
            placaGuardar.setDescripcion(    listaPlacas.get(i).getDescripcion());
            placaGuardar.setTipo(           listaPlacas.get(i).getTipo().name());
            entityManager.persist(placaGuardar);
            // guardando la llave "tipoPlaca" para, posteriormente acceder a su valor. El valor long -1 es temporal
            placasIdHashMap.put(    listaPlacas.get(i).getTipo().name(), -1L);
        }
        return placasIdHashMap;
    }

    private HashMap<String, Long> actualizarPlacasIdHashMap (HashMap<String, Long> placasIdHashMap, long idEquipo){
        // Obtenemos las id de las placas del equipo a guardado para reemplazar
        // el valor temporal que dejamos anteriormente: long -1
        String queryPlacas = "FROM Placa WHERE id_equipo = :id_equipo";
        List<Placa> placasGuardadas = entityManager.createQuery(queryPlacas)
                .setParameter("id_equipo", idEquipo).getResultList();

        for (int i = 0; i < placasGuardadas.size(); i++) {
            Placa placaTurno = placasGuardadas.get(i);
            placasIdHashMap.replace(placaTurno.getTipo(), placaTurno.getId());
        }
        return placasIdHashMap;
    }

    private ComponenteFisico guardarComponente(Domain.ComponenteFisico componenteRecibido, long idEquipo, String nombreEquipo) {
        ComponenteFisico componenteFisico = new ComponenteFisico();
        componenteFisico.setNombre(     componenteRecibido.getNombre() + "_" + nombreEquipo);
        componenteFisico.setDescripcion(componenteRecibido.getDescripcion());
        componenteFisico.setUrl(        componenteRecibido.getUrl());
        componenteFisico.setEstado(     componenteRecibido.getEstado().name());
        componenteFisico.setTipo(       componenteRecibido.getTipo().name());
        componenteFisico.setTipoPlaca(  componenteRecibido.getTipoPlaca().name());
        componenteFisico.setIdEquipo(   idEquipo);

        entityManager.persist(componenteFisico);
        return componenteFisico;
    }
    private void guardarPines(Domain.ComponenteFisico componenteTurno, String nombreComponente, HashMap<String, Long> placasIdHashMap) {
        String queryComponente = "FROM ComponenteFisico WHERE nombre = :nombre";
        List<ComponenteFisico> componentesGuardados = entityManager.createQuery(queryComponente).setParameter("nombre", nombreComponente).getResultList();

        // for loop para guardar los pines
        List<Domain.Pin> listaPines = componenteTurno.getPinComponenteList();
        for (int j = 0; j < listaPines.size(); j++) {
            Pin pin = new Pin();
            pin.setNumero(          listaPines.get(j).getNumero());
            pin.setNombre(          listaPines.get(j).getNombre());
            pin.setDescripcion(     listaPines.get(j).getDescripcion());
            pin.setConexion(        listaPines.get(j).getConexion().name());
            pin.setIdPlaca(         placasIdHashMap.get(    componenteTurno.getTipoPlaca().name())  );
            pin.setIdComponente(    componentesGuardados.get(0).getId());
            entityManager.persist(pin);
        }
    }

    private void guardarComponentesYPines ( HashMap<String, Long> placasIdHashMap, EquipoEntityReq equipoEntityReq, long idEquipo, String nombreEquipo) {
        // for loop para guardar CADA componente
        List<Domain.ComponenteFisico> listaComponentes = equipoEntityReq.getEquipo().getComponenteFisicoList();
        for (int i = 0; i < listaComponentes.size(); i++) {
            ComponenteFisico componenteFisico = this.guardarComponente(listaComponentes.get(i), idEquipo, nombreEquipo);
            this.guardarPines(listaComponentes.get(i), componenteFisico.getNombre(), placasIdHashMap);
        }
    }

    @Override
    public String addEquipo(EquipoEntityReq equipoEntityReq) {
        if (this.isUnableToSave(equipoEntityReq) != null) { return this.isUnableToSave(equipoEntityReq); }

        // GUARDAR EN LA TABLA EQUIPO
        this.guardarEquipo(equipoEntityReq);

        HashMap<String, String> datosEquipo = this.getDatosEquipoHashMap(equipoEntityReq);
        long idEquipo = Long.parseLong(datosEquipo.get("idEquipo"));
        String nombreEquipo = datosEquipo.get("nombreEquipo");

        // GUARDAR EN LA TABLA PLACAS. lo del return utiliza para guardar las id de las placas para "eficiente" iteracion en el for loop y posterior guardado
        HashMap<String, Long> placasIdHashMapCRUDAS = this.guardarPlacas(equipoEntityReq, idEquipo);
        HashMap<String, Long> placasIdHashMap = this.actualizarPlacasIdHashMap(placasIdHashMapCRUDAS, idEquipo);

        // GUARDAR EN LA TABLA COMPONENTEFISICO Y PIN
        this.guardarComponentesYPines(placasIdHashMap, equipoEntityReq, idEquipo, nombreEquipo);

        return "El equipo se ha agregado exitosamente";
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
    @Override
    public List<Placa> getPlacas(IdElementoReq idEquipo) {
        String sqlQuery = "FROM Placa WHERE id_equipo = :id_equipo";
        return (List<Placa>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idEquipo.getId()).getResultList();
    }

    @Override
    public ComponenteFisico getComponenteFisico(IdElementoReq idElementoReq){
        String sqlQuery = "FROM ComponenteFisico WHERE id = :id";
        return (ComponenteFisico) entityManager.createQuery(sqlQuery)
                .setParameter("id", idElementoReq.getId()).getResultList().get(0);
    }
    @Override
    public List<ComponenteFisico> getComponentesFisicos(IdElementoReq idElementoReq){
        String sqlQuery = "FROM ComponenteFisico WHERE id_equipo = :id_equipo";
        return (List<ComponenteFisico>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId()).getResultList();
    }

    @Override
    public List<Pin> getPines(long IdComponente){
        String sqlQuery = "FROM Pin WHERE id_componente = :id_componente";
        return (List<Pin>) entityManager.createQuery(sqlQuery)
                .setParameter("id_componente", IdComponente).getResultList();
    }

    public List<Evento> getEventos(long idSecuencia){
        String sqlQuery = "FROM Evento WHERE id_secuencia = :id_secuencia";
        return (List<Evento>) entityManager.createQuery(sqlQuery)
                .setParameter("id_secuencia", idSecuencia).getResultList();
    }
    @Override
    public Equipo getEquipo(IdElementoReq idEquipo){
        String sqlQuery = "FROM Equipo WHERE id = :id";
        return (Equipo) entityManager.createQuery(sqlQuery)
                .setParameter("id", idEquipo.getId()).getResultList().get(0);
    }

    @Override
    public List<Equipo> getEquipos(){
        String sqlQuery = "FROM Equipo";
        return (List<Equipo>) entityManager.createQuery(sqlQuery).getResultList();
    }

    @Override
    public List<ComponenteFisico> getValvulasEquipo(IdElementoReq idElementoReq){
        String sqlQuery = "FROM ComponenteFisico WHERE id_equipo = :id_equipo AND tipo = :tipo";
        return (List<ComponenteFisico>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId())
                .setParameter("tipo", "VALVULA")
                .getResultList();
    }

    @Override
    public List<Secuencia> getSecuenciasComponente(IdElementoReq idElementoReq){
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "FROM Secuencia s,ComponenteFisico c WHERE c.id_equipo = :id_equipo AND s.id_componente = c.id";
        return (List<Secuencia>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId())
                .getResultList();
    }

    /*public String uploadArchivo(ArchivosEquipoEntityReq archivosEquipoEntityReq){

    }*/
}
