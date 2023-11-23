/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Servicio;
import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.entidades.Valoracion;
import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;
import com.egg.web_app_servicios.repositorios.ServicioRepositorio;
import com.egg.web_app_servicios.repositorios.ValoracionRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorService {
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Autowired
    private ValoracionRepositorio valoracionRepositorio;
    
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public void crearProveedor(String nombre, Integer telefono, String password, String password2, String idServicio, String idValoracion) throws MiException{
        
        validar(nombre, telefono, idServicio);
        Servicio servicio = servicioRepositorio.findById(idServicio).get();
        Valoracion valoracion = valoracionRepositorio.findById(idValoracion).get();
        
        Proveedor proveedor = new Proveedor();
        
        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setServicio(servicio);
        proveedor.setValoracion(valoracion);
        proveedorRepositorio.save(proveedor);
        
    }
    
    public List<Proveedor> listarProveedor(){
        
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
    @Transactional
    public void modificarProveedor(String id, String nombre, Integer telefono, String idServicio, String idValoracion) throws MiException{
        
        validar(nombre, telefono, idServicio);
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        Optional<Servicio> respuestaServicio = servicioRepositorio.findById(idServicio);
        Optional<Valoracion> respuestaValoracion = valoracionRepositorio.findById(idValoracion);
        
        
        Valoracion valoracion = new Valoracion();
        Servicio servicio = new Servicio();
        
        if(respuestaServicio.isPresent()){
            servicio = respuestaServicio.get();
        }
        
        if(respuestaValoracion.isPresent()){
            valoracion = respuestaValoracion.get();
        }
        
        if(respuesta.isPresent()){
            Proveedor proveedor = respuesta.get();
            
            proveedor.setNombre(nombre);
            proveedor.setTelefono(telefono);
            proveedor.setServicio(servicio);
            proveedor.setValoracion(valoracion);
            
            proveedorRepositorio.save(proveedor);
        }
    }  
    
    private void validar(String nombre, Integer telefono, String idServicio) throws MiException{
        
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");   
        }
        
        if(telefono == null){
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }
        
        if(idServicio.isEmpty() || idServicio == null){
            throw new MiException("El tipo de servicio no puede ser nulo o estar vacio");
        }
        
    }
    
}