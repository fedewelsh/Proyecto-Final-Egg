/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servix2.servicios;

import com.egg.servix2.entidades.Administrador;
import com.egg.servix2.entidades.Usuario;
import com.egg.servix2.excepciones.MiException;
import com.egg.servix2.repositorios.AdministradorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorServicio {
    
    @Autowired 
    private AdministradorRepositorio administradorRepositorio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Transactional
    public void crearAdministrador(String nombre, String telefono, String email, String password, String password2) throws MiException{
        
        validar(email);
        Usuario usuario = usuarioServicio.crearUsuario(nombre, telefono, password, password2);
        Administrador administrador = new Administrador();
        
        administrador.setEmail(email);
        administrador.setUsuario(usuario);
        
        administradorRepositorio.save(administrador);
    }
    
    
     public List<Administrador> listarProveedor(){
        
        List<Administrador> administradores = new ArrayList();
        
        administradores = administradorRepositorio.findAll();
        
        return administradores;
    }
    
    @Transactional
    public void modificarAdministrador(String nombre, String telefono, String email, String password, String password2) throws MiException {
        
        validar(email);
        Optional<Administrador> respuesta = administradorRepositorio.findById(email);
        
        if (respuesta.isPresent()) {

            Administrador administrador = respuesta.get();

            Usuario usuario = administrador.getUsuario();

            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setPassword(password);
            
            administrador.setEmail(email);
            
            
            administradorRepositorio.save(administrador);
        }
    }
    
    public void validar(String email) throws MiException {

        if (administradorRepositorio.existsByEmail(email)) {
            throw new MiException("El correo electrónico ya está registrado.");
        }

    }
    
    
}
