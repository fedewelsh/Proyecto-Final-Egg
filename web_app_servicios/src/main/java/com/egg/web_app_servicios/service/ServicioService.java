/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;

import com.egg.web_app_servicios.entidades.Imagen;
import com.egg.web_app_servicios.entidades.Servicio;
import com.egg.web_app_servicios.excepciones.MiException;

import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;
import com.egg.web_app_servicios.repositorios.ServicioRepositorio;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioService {
    
    @Autowired
    private ServicioRepositorio servicioRepositorio;
       
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public void crearServicio(String tipo_servicio, MultipartFile archivo) throws MiException{
        
        Servicio servicio = new Servicio();
        
       
        servicio.setTipo_servicio(tipo_servicio);
        
        Imagen imagen = imagenService.guardar(archivo);
        
        servicio.setImagen(imagen);
        
        servicioRepositorio.save(servicio);
    }
    
    public void listarServicio(String id, String tipo_servicio, MultipartFile archivo){
        
        List<Servicio> servicios = new ArrayList();
        
        servicios = servicioRepositorio.findAll();
        
    }
    
    @Transactional
    public void modificarServicio(String id, String tipo_servicio, MultipartFile archivo) throws MiException{
        
        validar(tipo_servicio);
        Optional<Servicio> respuesta = servicioRepositorio.findById(id);
        
        if(respuesta.isPresent()){
           Servicio servicio = respuesta.get();
           
           servicio.setTipo_servicio(tipo_servicio);
           servicio.setImagen((Imagen) archivo);
           
           servicioRepositorio.save(servicio);           
        }        
    }
    
    private void validar(String tipo_servicio) throws MiException{
        
        if(tipo_servicio.isEmpty() || tipo_servicio == null){
            throw new MiException("El tipo de servicio no puede ser nulo");
            
        }
    }
}
