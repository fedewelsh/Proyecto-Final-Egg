/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Servicio;
import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;
import com.egg.web_app_servicios.repositorios.ServicioRepositorio;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
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
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public void crearProveedor(String idUsuario, String idServicio){
        
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        Servicio servicio = servicioRepositorio.findById(idServicio).get();
        
        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);
        proveedor.setServicio(servicio);
        proveedorRepositorio.save(proveedor);
        
    }
    
    public List<Proveedor> listarProveedor(){
        
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
    public void modificarProveedor(String id, String idUsuario, String idServicio){
        
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);
        Optional<Servicio> respuestaServicio = servicioRepositorio.findById(idServicio);
        
        Usuario usuario = new Usuario();
        Servicio servicio = new Servicio();
        
        if(respuestaUsuario.isPresent()){
            usuario = respuestaUsuario.get();
        }
        
        if(respuestaServicio.isPresent()){
            servicio = respuestaServicio.get();
        }
        
        if(respuesta.isPresent()){
            Proveedor proveedor = respuesta.get();
            
            proveedor.setServicio(servicio);
            proveedor.setUsuario(usuario);
            
            
        }
    }
    
}