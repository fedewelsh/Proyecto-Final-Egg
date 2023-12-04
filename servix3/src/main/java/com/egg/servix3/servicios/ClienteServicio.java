/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servix3.servicios;


import com.egg.servix3.entidades.Cliente;
import com.egg.servix3.entidades.Usuario;
import com.egg.servix3.excepciones.MiException;
import com.egg.servix3.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Transactional
    public void crearCliente(String nombre, String telefono, String email, String password, String password2, String barrio, String direccion) throws MiException{
        
        validar(email);
        Usuario usuario = usuarioServicio.crearUsuario(nombre, telefono, password, password2);
        Cliente cliente = new Cliente();
        
        cliente.setEmail(email);
        cliente.setBarrio(barrio);
        cliente.setDireccion(direccion);
        cliente.setUsuario(usuario);
        
        clienteRepositorio.save(cliente);      
    }
    
    public List<Cliente> listarCliente(){
        
        List<Cliente> clientes = new ArrayList();
        
        clientes = clienteRepositorio.findAll();
        
        return clientes;
    }
    
    @Transactional
    public void modificarCliente(String nombre, String telefono, String email, String password, String password2, String barrio, String direccion) throws MiException {
        
        validar(email);
        Optional<Cliente> respuesta = clienteRepositorio.findById(email);
        
        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();

            Usuario usuario = cliente.getUsuario();

            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setPassword(password);
            
            cliente.setEmail(email);
            cliente.setBarrio(barrio);
            cliente.setDireccion(direccion);
            
            clienteRepositorio.save(cliente);
        }
    }
    
    public void validar(String email) throws MiException {

        if (clienteRepositorio.existsByEmail(email)) {
            throw new MiException("El correo electrónico ya está registrado.");
        }

    }
    
    
    
    
    
}
