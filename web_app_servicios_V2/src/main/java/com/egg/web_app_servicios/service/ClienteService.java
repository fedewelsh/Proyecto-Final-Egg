/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.enumeraciones.Rol;
import com.egg.web_app_servicios.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.egg.web_app_servicios.repositorios.ClienteRepositorio;


@Service
public class ClienteService implements UserDetailsService {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public void crearUsuario(String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
        validar(nombre, telefono, email, barrio, direccion, password, password2);
        Cliente usuario = new Cliente();
        
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);
        usuario.setDireccion(direccion);
        usuario.setBarrio(barrio);
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRol(Rol.USER);
        
        //Imagen imagen = imagenService.guardar(archivo);

        //usuario.setImagen(imagen);
        
        clienteRepositorio.save(usuario);
    }
    
    public List<Cliente> listarUsuario(){
        
        List<Cliente> usuarios = new ArrayList();
        
        usuarios = clienteRepositorio.findAll();
        
        return usuarios;
    }
    
    public void modificarUsuario(String id, String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
        validar(nombre, telefono, email, barrio, direccion, password, password2);
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
         
        if(respuesta.isPresent()){
            
            Cliente usuario = respuesta.get();
            
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setEmail(email);
            usuario.setBarrio(barrio);
            usuario.setDireccion(direccion);
            usuario.setPassword(password);
            clienteRepositorio.save(usuario);
        }       
    }
    
    
    public void eliminarClientePorEmail(String email) throws MiException {
    // Buscar el cliente por su email
    List<Cliente> clientes = clienteRepositorio.findByEmail(email);

    if (!clientes.isEmpty()) {
        // Si se encontraron clientes con ese email, eliminar el primero (o manejar según tus necesidades)
        Cliente cliente = clientes.get(0);
        clienteRepositorio.delete(cliente);
    } else {
        // Si la lista está vacía, el cliente no existe, lanzar una excepción o manejar de acuerdo a tus necesidades
        throw new MiException("Cliente con email " + email + " no encontrado");
    }

}
    
    
    public void validar (String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vac�o");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException ("El telefono no puede ser nulo o estar vacio");
        }else if (telefono.length() < 3) {
            throw new MiException ("El telefono debe tenes mas de 9 digitos");
        }
        
        if (email.isEmpty()|| email == null){
            throw new MiException ("El email no puede ser nulo o estar vacio");
        }
        
        if (barrio.isEmpty() || barrio == null){
            throw new MiException ("El barrio no puede ser nulo o estar vacio");
        }
        
        if (direccion.isEmpty() || direccion == null) {
             throw new MiException ("La direccion no puede ser nula o estar vacia");
        }
        
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (clienteRepositorio.existsByEmail(email)) {
        throw new MiException("El correo electrónico ya está registrado.");
    }
        
    }
    
 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
