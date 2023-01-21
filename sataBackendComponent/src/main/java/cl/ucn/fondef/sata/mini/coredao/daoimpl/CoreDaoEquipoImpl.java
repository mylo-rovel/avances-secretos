/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daoimpl;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoEquipo;
import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoUsuario;
import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Componente;
import cl.ucn.fondef.sata.mini.model.Evento;
import cl.ucn.fondef.sata.mini.model.Pin;
import cl.ucn.fondef.sata.mini.model.Placa;
import cl.ucn.fondef.sata.mini.model.Secuencia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.ucn.fondef.sata.mini.model.*;
import cl.ucn.fondef.sata.mini.grpc.Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    /**
     * The Core dao usuario.
     */
    @Autowired
    CoreDaoUsuario coreDaoUsuario;

    private List<Usuario> getListaUsuariosPorRut(EquipoEntityReq equipoEntityReq) {
        String usuarioQuery = "SELECT usuario FROM Usuario as usuario WHERE usuario.rut = :rut AND usuario.estado = 'ACTIVO' ";
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
        String sqlQuery = "SELECT equipo FROM Equipo as equipo WHERE equipo.nombre = :nombre";
        List listaResultado = entityManager.createQuery(sqlQuery)
                .setParameter("nombre", equipoEntityReq.getEquipo().getNombre()).getResultList();
        if(!listaResultado.isEmpty()){
            return "El equipo ingresado ya existe";
        }
        return null;
    }

    private  HashMap<String, String> getDatosEquipoHashMap (EquipoEntityReq equipoEntityReq) {
        String queryEquipo = "SELECT equipo FROM Equipo as equipo WHERE equipo.nombre = :nombre";
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
        String queryPlacas = "SELECT placa FROM Placa as placa WHERE placa.idEquipo = :id_equipo";
        List<Placa> placasGuardadas = entityManager.createQuery(queryPlacas)
                .setParameter("id_equipo", idEquipo).getResultList();

        for (int i = 0; i < placasGuardadas.size(); i++) {
            Placa placaTurno = placasGuardadas.get(i);
            placasIdHashMap.replace(placaTurno.getTipo(), placaTurno.getId());
        }
        return placasIdHashMap;
    }

    private Componente guardarComponente(Domain.Componente componenteRecibido, long idEquipo, String nombreEquipo) {
        Componente componente = new Componente();
        componente.setNombre(     componenteRecibido.getNombre() + "_" + nombreEquipo);
        componente.setDescripcion(componenteRecibido.getDescripcion());
        componente.setUrl(        componenteRecibido.getUrl());
        componente.setEstado(     componenteRecibido.getEstado().name());
        componente.setTipo(       componenteRecibido.getTipo().name());
        componente.setTipoPlaca(  componenteRecibido.getTipoPlaca().name());
        componente.setId_equipo(   idEquipo);

        entityManager.persist(componente);
        return componente;
    }
    private void guardarPines(Domain.Componente componenteTurno, String nombreComponente, HashMap<String, Long> placasIdHashMap) {
        String queryComponente = "SELECT componente FROM Componente as componente WHERE componente.nombre = :nombre";
        List<Componente> componentesGuardados = entityManager.createQuery(queryComponente).setParameter("nombre", nombreComponente).getResultList();

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
        List<Domain.Componente> listaComponentes = equipoEntityReq.getEquipo().getComponenteList();
        for (int i = 0; i < listaComponentes.size(); i++) {
            Componente componente = this.guardarComponente(listaComponentes.get(i), idEquipo, nombreEquipo);
            this.guardarPines(listaComponentes.get(i), componente.getNombre(), placasIdHashMap);
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

        // GUARDAR EN LA TABLA Componente Y PIN
        this.guardarComponentesYPines(placasIdHashMap, equipoEntityReq, idEquipo, nombreEquipo);

        return "El equipo se ha agregado exitosamente";
    }

    @Override
    public String updateEquipo(EquipoEntityReq equipoEntityReq){
        /*Equipo equipoEditar = entityManager.find(Equipo.class, equipoEntityReq.getEquipo().getId());

        // TODO: equipo editar es null => hacer una query para obtener la id del equipo
        // todo: luego, usamos el .find()
        equipoEditar.setDescripcion(equipoEntityReq.getEquipo().getDescripcion());
        equipoEditar.setUrlRepositorio(equipoEntityReq.getEquipo().getUrlRepositorio());
        equipoEditar.setEstado(equipoEntityReq.getEquipo().getEstado().name());

        // TODO: OBTENER LAS PLACAS DEL CORE DAO USANDO LA ID DEL EQUIPO
        List<Domain.Placa> listaPlacas = equipoEntityReq.getEquipo().getPlacaList();
        for(Domain.Placa placa : listaPlacas){
            Placa placaGuardar = new Placa();
            placaGuardar.setNombre(placa.getNombre());
            placaGuardar.setDescripcion(placa.getDescripcion());
            placaGuardar.setTipo(placa.getTipo().toString());
            placaGuardar.setIdEquipo(equipoEditar.getId());
            entityManager.merge(placaGuardar);
        }

        // TODO: OBTENER LOS COMPONENTES DEL CORE DAO USANDO LA ID DEL EQUIPO
        List<Domain.Componente> listaComponentes = equipoEntityReq.getEquipo().getComponenteList();
        for(Domain.Componente componente : listaComponentes){
            Componente componenteGuardar = new Componente();
            componenteGuardar.setNombre(componente.getNombre());
            componenteGuardar.setDescripcion(componente.getDescripcion());
            componenteGuardar.setUrl(componente.getUrl());
            componenteGuardar.setEstado(componente.getEstado().toString());
            componenteGuardar.setTipo(componente.getTipo().toString());
            componenteGuardar.setTipoPlaca(componente.getTipoPlaca().toString());

            // TODO: OBTENER LOS PINES DEL CORE DAO USANDO LA ID DEL COMPONENTE
            List<Domain.Pin> listaPines = componente.getPinComponenteList();
            for(Domain.Pin pin : listaPines){
                Pin pinGuardar = new Pin();
                pinGuardar.setNombre(pin.getNombre());
                pinGuardar.setDescripcion(pin.getDescripcion());
                pinGuardar.setConexion(pin.getConexion().toString());
                entityManager.merge(pinGuardar);
            }

            entityManager.merge(componenteGuardar);
        }

        entityManager.merge(equipoEditar);*/

        String mensaje = "El equipo se ha actualizado exitosamente";

        return mensaje;
    }

    // ---- AUX PARA getEquipo ----
    @Override
    public List<Placa> getPlacasPorIdEquipo(IdElementoReq idEquipo) {
        String sqlQuery = "SELECT placa FROM Placa as placa WHERE placa.idEquipo = :id_equipo";
        return (List<Placa>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idEquipo.getId()).getResultList();
    }

    @Override
    public Componente getComponentePorId(IdElementoReq idElementoReq){
        String sqlQuery = "SELECT componente FROM Componente as componente WHERE componente.id = :id";
        return (Componente) entityManager.createQuery(sqlQuery)
                .setParameter("id", idElementoReq.getId()).getResultList().get(0);
    }

    @Override
    public List<Componente> getComponentesPorIdEquipo(IdElementoReq idElementoReq){
        String sqlQuery = "SELECT componente FROM Componente as componente WHERE componente.idEquipo = :id_equipo";
        return (List<Componente>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId()).getResultList();
    }

    @Override
    public List<Pin> getPinesPorIdComponente(long IdComponente){
        String sqlQuery = "SELECT pin FROM Pin as pin WHERE pin.idComponente = :id_componente";
        return (List<Pin>) entityManager.createQuery(sqlQuery)
                .setParameter("id_componente", IdComponente).getResultList();
    }

    private Query getEquipoQueryPorRolUsuario(IdElementoConRutReq idEquipoYrutUsuario) {
        String rutUsuario = idEquipoYrutUsuario.getRut();
        Usuario usuarioRequest = coreDaoUsuario.getUsuario(Domain.RutEntityReq.newBuilder().setRut(rutUsuario).build());
        String sqlQuery = "";
        if (usuarioRequest.getRol().equals(UsuarioEntity.RolUsuario.OPERADOR.name())){
            sqlQuery = "SELECT equipo FROM Equipo as equipo WHERE equipo.id = :id AND equipo.estado = 'PROTOTIPO'";
        }
        else {
            sqlQuery = "SELECT equipo FROM Equipo as equipo WHERE equipo.id = :id";
        }
        return entityManager.createQuery(sqlQuery).setParameter("id", idEquipoYrutUsuario.getId());
    }

    @Override
    public Equipo getEquipo(IdElementoConRutReq idEquipoYrutUsuario){
        Query sqlQueryEquipo = this.getEquipoQueryPorRolUsuario(idEquipoYrutUsuario);
        List listaResultado = sqlQueryEquipo.getResultList();

        if(listaResultado.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        return (Equipo) listaResultado.get(0);
    }

    @Override
    public Equipo getEquipoPorNombre(String nombreEquipo){
        String sqlQueryEquipo = "SELECT equipo FROM Equipo as equipo WHERE equipo.nombre = :nombre";
        List listaResultado = entityManager.createQuery(sqlQueryEquipo)
                .setParameter("nombre", nombreEquipo).getResultList();

        if(listaResultado.isEmpty()) {
            log.warn("La lista no contiene elementos");
            return null;
        }
        return (Equipo) listaResultado.get(0);
    }

    private Query getEquiposQueryPorRolUsuario (Domain.RutEntityReq rutEntityReq) {
        String rutUsuario = rutEntityReq.getRut();
        Usuario usuarioRequest = coreDaoUsuario.getUsuario(Domain.RutEntityReq.newBuilder().setRut(rutUsuario).build());
        String sqlQuery = "";
        if (usuarioRequest.getRol().equals(UsuarioEntity.RolUsuario.OPERADOR.name())){
            sqlQuery = "SELECT equipo FROM Equipo as equipo WHERE equipo.estado = 'PROTOTIPO'";
        }
        else {
            sqlQuery = "SELECT equipo FROM Equipo as equipo";
        }
        return entityManager.createQuery(sqlQuery);
    }

    @Override
    public List<Equipo> getEquipos(Domain.RutEntityReq rutEntityReq){
        Query sqlQueryEquipos = getEquiposQueryPorRolUsuario(rutEntityReq);
        return (List<Equipo>) sqlQueryEquipos.getResultList();
    }

    @Override
    public List<Componente> getValvulasEquipo(IdElementoReq idElementoReq){
        String sqlQuery = "SELECT componente FROM Componente as componente " +
                "WHERE componente.idEquipo = :id_equipo AND componente.tipo = :tipo";
        return (List<Componente>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId())
                .setParameter("tipo", "VALVULA")
                .getResultList();
    }

    @Override
    public List<Evento> getEventos(long idSecuencia){
        String sqlQuery = "SELECT evento FROM Evento as evento WHERE evento.idSecuencia = :id_secuencia";
        return (List<Evento>) entityManager.createQuery(sqlQuery)
                .setParameter("id_secuencia", idSecuencia).getResultList();
    }

    @Override
    public List<Secuencia> getSecuenciasComponente(IdElementoReq idElementoReq){
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "SELECT s, c FROM Secuencia s, Componente c WHERE c.idEquipo = :id_equipo AND s.idComponente = c.id";
        return (List<Secuencia>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idElementoReq.getId())
                .getResultList();
    }


    @Override
    public List<Componente> getSensoresDB(long idEquipo) {
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "SELECT c FROM Componente c WHERE " +
                "c.id_equipo = :id_equipo";
        return (List<Componente>) entityManager.createQuery(sqlQuery)
                .setParameter("id_equipo", idEquipo)
                .getResultList();
    }
    public List<Componente> getUmbralesPorSensorDB(long idSensor) {
        //se obtienen las secuencias de la id de un equipo dado utilizando la id_equipo de componenteFisico
        String sqlQuery = "SELECT s FROM Simulacion s WHERE " +
                "s.id_sensor = : id_sensor";
        return (List<Componente>) entityManager.createQuery(sqlQuery)
                .setParameter("id_sensor", idSensor)
                .getResultList();
    }
    /*public String uploadArchivo(ArchivosEntityReq archivosEntityReq){

    }*/
}
