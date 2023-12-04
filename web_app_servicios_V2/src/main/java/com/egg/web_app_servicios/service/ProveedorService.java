/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.enumeraciones.Rol;



import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;

import com.egg.web_app_servicios.repositorios.ValoracionRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.egg.web_app_servicios.repositorios.ClienteRepositorio;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ProveedorService implements  UserDetailsService {
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
   
    
    @Autowired
    private UsuarioService usuarioService;
        
    
    
    @Transactional
    public void crearProveedor(String id, String nombre, String telefono, String email, String password, String password2, String tipo_servicio, String descripcion) throws MiException{
        
        validar(nombre, telefono, email, tipo_servicio);
        
       
      Proveedor proveedor = new Proveedor();
        
        proveedor.setId(id);
        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        proveedor.setRol(Rol.USER);
        proveedor.setPassword(password);
        
        
        //Imagen imagen = imagenService.guardar(archivo);

        //usuario.setImagen(imagen);
        
        proveedorRepositorio.save(proveedor);
        
    }
    
    public List<Proveedor> listarProveedor(){
        
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
    public List<Proveedor> listarProveedorPorTipoServicio(String tipo_servicio) {
        
       
        List<Proveedor> proveedores = proveedorRepositorio.findByTipoServicio(tipo_servicio);

        return proveedores;
    }
     
    
    
    @Transactional
    public void modificarProveedor(String id, String nombre, String telefono, String email, String tipo_servicio, String descripcion, String password, String password2) throws MiException{
        
        validar(nombre, telefono, email, tipo_servicio);
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
   
              
        if(respuesta.isPresent()){
           Proveedor proveedor = new Proveedor();
        
        proveedor.setId(id);
        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        proveedor.setRol(Rol.USER);
        proveedor.setPassword(password);
        
        //Imagen imagen = imagenService.guardar(archivo);

        //usuario.setImagen(imagen);
        
        proveedorRepositorio.save(proveedor);
        }
    }  
    
    private void validar(String nombre, String telefono, String email, String tipo_servicio) throws MiException{
        
       
        
        if(tipo_servicio.isEmpty() || tipo_servicio == null){
            throw new MiException("El tipo de servicio no puede ser nulo o estar vacio");
        }
       
    
    }
    
    
     @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Proveedor proveedor = (Proveedor) proveedorRepositorio.buscarPorEmail(email);

        if (proveedor != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", proveedor);

            return new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
        } else {
            return null;
        }
    }
}