/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servix3.servicios;

import com.egg.servix3.entidades.Usuario;
import com.egg.servix3.excepciones.MiException;
import com.egg.servix3.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuario crearUsuario(String nombre, String telefono, String password, String password2) throws MiException {
        
        
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setPassword(password);

        usuarioRepositorio.save(usuario);
        return usuario;
    }

    public void validar (String email) throws MiException{  
        
        if (usuarioRepositorio.existsByEmail(email)) {
        throw new MiException("El correo electrónico ya está registrado.");
    }
        
    }
    
}
