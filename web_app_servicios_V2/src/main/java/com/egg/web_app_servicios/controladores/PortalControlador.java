/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;

import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.service.ProveedorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private ProveedorService proveedorService;
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
     @GetMapping("/login")
    public String login(){
          return "login.html";  
    }
    
    
    
    
    
    
    
    @GetMapping("/inicio_cliente")
    public String mostrarPaginaInicioCliente() {
        
       
        return "inicio_cliente.html";
    }
    
  
}

