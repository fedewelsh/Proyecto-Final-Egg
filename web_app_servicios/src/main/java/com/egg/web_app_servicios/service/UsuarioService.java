/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;

import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public void crearUsuario(String nombre, Integer telefono, String direccion){
        
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        
        usuarioRepositorio.save(usuario);
    }
    
    public List<Usuario> listarUsuario(){
        
        List<Usuario> usuarios = new ArrayList();
        
        usuarios = usuarioRepositorio.findAll();
        
        return usuarios;
    }
    
    public void modificarUsuario(String id, String nombre, Integer telefono, String direccion){
        
       Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
         
        if(respuesta.isPresent()){
            
            Usuario usuario = respuesta.get();
            
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);            
        }
        
        
        
    }
    
    
    
    
}
