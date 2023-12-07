/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.entidades.Usuario;

import com.egg.web_app_servicios.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.egg.web_app_servicios.repositorios.ClienteRepositorio;



@Service
public class ClienteService{
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    
    
    @Autowired
    private UsuarioService usuarioService;
    
   
    
    @Transactional
    public void crearCliente(String id, String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
        
        
        validar(nombre, telefono, email, barrio, direccion, password, password2);
        
        Usuario usuario = usuarioService.crearUsuario(nombre, telefono, email, password, password2);
        Cliente cliente = new Cliente();
        
        
        cliente.setDireccion(direccion);
        cliente.setBarrio(barrio);
        cliente.setUsuario(usuario);
        
        clienteRepositorio.save(cliente);
    }
    
    public List<Cliente> listarUsuario(){
        
        List<Cliente> usuarios = new ArrayList();
        
        usuarios = clienteRepositorio.findAll();
        
        return usuarios;
    }
    
    @Transactional
    public void modificarUsuario(String id, String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
        validar(nombre, telefono, email, barrio, direccion, password, password2);
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
         
        if(respuesta.isPresent()){
            
        Usuario usuario = usuarioService.crearUsuario(nombre, telefono, email, password, password2);
        Cliente cliente = new Cliente();
        
        
        cliente.setDireccion(direccion);
        cliente.setBarrio(barrio);
        cliente.setUsuario(usuario);
        
        
        clienteRepositorio.save(cliente);
        }       
    }
    
    
//    public void eliminarClientePorEmail(String email) throws MiException {
//    // Buscar el cliente por su email
//    List<Cliente> clientes = clienteRepositorio.buscarPorEmail(email);
//
//    if (!clientes.isEmpty()) {
//        // Si se encontraron clientes con ese email, eliminar el primero (o manejar según tus necesidades)
//        Cliente cliente = clientes.get(0);
//        clienteRepositorio.delete(cliente);
//    } else {
//        // Si la lista está vacía, el cliente no existe, lanzar una excepción o manejar de acuerdo a tus necesidades
//        throw new MiException("Cliente con email " + email + " no encontrado");
//    }


    
    
    public void validar (String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
       
        
        if (barrio.isEmpty() || barrio == null){
            throw new MiException ("El barrio no puede ser nulo o estar vacio");
        }
        
        if (direccion.isEmpty() || direccion == null) {
             throw new MiException ("La direccion no puede ser nula o estar vacia");
        }
     
    }
    
 
    
    
}
