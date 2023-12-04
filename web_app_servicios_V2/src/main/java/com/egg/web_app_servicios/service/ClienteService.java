/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.entidades.Usuario;
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
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service
public class ClienteService implements UserDetailsService {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    
    
    @Autowired
    private UsuarioService usuarioService;
    
   
    
    @Transactional
    public void crearCliente(String id, String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
        
        
        validar(nombre, telefono, email, barrio, direccion, password, password2);
        
        
        Cliente cliente = new Cliente();
        
        cliente.setId(id);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        cliente.setBarrio(barrio);
        cliente.setPassword(password);
        cliente.setRol(Rol.USER);
        
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
            
        Cliente cliente = new Cliente();
        
        cliente.setId(id);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        cliente.setBarrio(barrio);
        cliente.setPassword(password);
        
        
        clienteRepositorio.save(cliente);
        }       
    }
    
    
    public void eliminarClientePorEmail(String email) throws MiException {
    // Buscar el cliente por su email
    List<Cliente> clientes = clienteRepositorio.buscarPorEmail(email);

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
        
       
        
        if (barrio.isEmpty() || barrio == null){
            throw new MiException ("El barrio no puede ser nulo o estar vacio");
        }
        
        if (direccion.isEmpty() || direccion == null) {
             throw new MiException ("La direccion no puede ser nula o estar vacia");
        }
     
    }
    
 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = (Cliente) clienteRepositorio.buscarPorEmail(email);

        if (cliente != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + cliente.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", cliente);

            return new User(cliente.getEmail(), cliente.getPassword(), permisos);
        } else {
            return null;
        }
    }

    
    
}
