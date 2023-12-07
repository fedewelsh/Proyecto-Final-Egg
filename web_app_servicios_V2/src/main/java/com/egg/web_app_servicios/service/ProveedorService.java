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


@Service
public class ProveedorService{
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
   
    
    @Autowired
    private UsuarioService usuarioService;
        
    
    
    @Transactional
    public void crearProveedor(String id, String nombre, String telefono, String email, String password, String password2, String tipo_servicio, String descripcion) throws MiException{
        
        validar(nombre, telefono, email, tipo_servicio);
        
      Usuario usuario = usuarioService.crearUsuario(nombre, telefono, email, password, password2);
      Proveedor proveedor = new Proveedor();
        
        
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        
        proveedor.setUsuario(usuario);
        
        
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
         Usuario usuario = usuarioService.crearUsuario(nombre, telefono, email, password, password2);
      Proveedor proveedor = new Proveedor();
        
        
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        
        proveedor.setUsuario(usuario);
        
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
    
    
    
    
}