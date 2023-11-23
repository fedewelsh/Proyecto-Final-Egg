/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;

import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.excepciones.MiException;
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
    public void crearUsuario(String nombre, Integer telefono, String direccion) throws MiException{
        
        validar(nombre, telefono, direccion);
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
    
    public void modificarUsuario(String id, String nombre, Integer telefono, String direccion) throws MiException{
        
        validar(nombre, telefono, direccion);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
         
        if(respuesta.isPresent()){
            
            Usuario usuario = respuesta.get();
            
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuarioRepositorio.save(usuario);
        }
       
    }
    
    private void validar(String nombre, Integer telefono, String direccion) throws MiException{
        
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");   
        }
        
        if(telefono == null){
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }
        
        if(direccion.isEmpty() || direccion == null){
            throw new MiException("La direccion no puede ser nulo o estar vacio");
        }
    }
    
    
}
