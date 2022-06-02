package com.backendCore.gRPCcore.controllers;

import com.backendCore.gRPCcore.dao.CoreDao;
import com.backendCore.gRPCcore.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class mainRESTcontroller {

    @Autowired
    CoreDao coreDao;

    @RequestMapping(value="api/usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable int id){
        Usuario usuario = new Usuario();
        usuario.setUsuario_id(id);
        usuario.setNombre("PEPE");
        usuario.setApellido("POLLITO");
        return usuario;
    }

    @RequestMapping(value="api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(){
        return coreDao.getUsuarios();
    }

    @RequestMapping(value="api/usuario/{id}", method = RequestMethod.DELETE)
    public void toggleUsuario(@PathVariable int id){
        coreDao.inhabilitarUsuario(id);
    }

    @RequestMapping(value="api/usuario", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario) {
        System.out.println(usuario);
        coreDao.registrarUsuario(usuario);
    }
}
