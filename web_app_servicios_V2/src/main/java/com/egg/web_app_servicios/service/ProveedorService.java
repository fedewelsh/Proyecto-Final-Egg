/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Proveedor;



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

@Service
public class ProveedorService {
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Autowired
    private ValoracionRepositorio valoracionRepositorio;
    
   
    
    @Autowired
    private ClienteRepositorio usuarioRepositorio;
    
        
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public void crearProveedor(String nombre, String telefono, String email, String password, String password2, String tipo_servicio) throws MiException{
        
        validar(nombre, telefono, email, tipo_servicio);
        
       
        Proveedor proveedor = new Proveedor();
        
        proveedor.setNombre(nombre);
        proveedor.setPassword(password);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setTipo_servicio(tipo_servicio);
        
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
    public void modificarProveedor(String id, String nombre, String telefono, String email, String tipo_servicio) throws MiException{
        
        validar(nombre, telefono, email, tipo_servicio);
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
   
              
        if(respuesta.isPresent()){
            Proveedor proveedor = respuesta.get();
            
           proveedor.setNombre(nombre);
          
           proveedor.setTelefono(telefono);
           proveedor.setEmail(email);
          proveedor.setTipo_servicio(tipo_servicio);
            
            proveedorRepositorio.save(proveedor);
        }
    }  
    
    private void validar(String nombre, String telefono, String email, String tipo_servicio) throws MiException{
        
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");   
        }
              
        if(telefono == null){
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }
        
        if(email.isEmpty() || email == null){
            throw new MiException("El email no puede ser nulo o estar vacio");
        }
        
        if(tipo_servicio.isEmpty() || tipo_servicio == null){
            throw new MiException("El tipo de servicio no puede ser nulo o estar vacio");
        }
        
         if (proveedorRepositorio.existsByEmail(email)) {
        throw new MiException("El correo electrónico ya está registrado.");
    }
    }
    
}