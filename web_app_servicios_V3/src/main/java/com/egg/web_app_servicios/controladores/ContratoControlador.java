/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;

import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Solicitud;
import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;
import com.egg.web_app_servicios.repositorios.SolicitudRepositorio;
import com.egg.web_app_servicios.service.SolicitudService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/contrato")
public class ContratoControlador {
    
    
    @Autowired
    private SolicitudRepositorio solicitudRepositorio;
    
    @Autowired
    private SolicitudService solicitudService;
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    
    
    @GetMapping("/solicitud/{clienteId}/{proveedorId}")
    public String solicitud(@PathVariable String clienteId, @PathVariable String proveedorId, ModelMap modelo){
        modelo.addAttribute("clienteId", clienteId);
        modelo.addAttribute("proveedorId", proveedorId);
        
        return "solicitud.html";
    }
    
    
    @PostMapping("/registro")
public String registro(
        @RequestParam String proveedorId,
        @RequestParam String clienteId,
        @RequestParam String mensaje,
        @RequestParam("fecha") String fecha,
        @RequestParam("hora") String hora) {
    
     String fechaHoraString = fecha + " " + hora;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    Date fechaHora;
    try {
        fechaHora = formatter.parse(fechaHoraString);
    } catch (ParseException e) {
        // Maneja la excepción si hay un problema con el formato
        e.printStackTrace();
        return "error.html";
    }
    
    solicitudService.crearSolicitud(proveedorId, clienteId, mensaje, fechaHora);
   
    return "inicio.html";
}
    
@GetMapping("/lista_solicitud/{proveedorId}")
    public String listarSolicitudes(@PathVariable String proveedorId, ModelMap modelo) {
        
       
       
        
        List<Solicitud> solicitudes = solicitudRepositorio.findAll();
       
        modelo.addAttribute("solicitudes", solicitudes);
        return "solicitudes_list.html"; 
    }
    

    
    @GetMapping("/solicitud_respuesta/{solicitudId}")
    public String solicitudRespuesta(@PathVariable String solicitudId, ModelMap modelo){
        
        List<Solicitud> solicitudes = solicitudRepositorio.findAll();
        modelo.addAttribute("solicitudId", solicitudes);
       
        return "solicitud_respuesta.html";
    }
    
    
    
    
    
}
