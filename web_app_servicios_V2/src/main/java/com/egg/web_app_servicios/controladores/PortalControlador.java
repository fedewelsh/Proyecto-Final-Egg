/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;


import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import com.egg.web_app_servicios.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class PortalControlador {
    
   @Autowired
    private UsuarioService usuarioService;
   
   @Autowired
   private UsuarioRepositorio usuarioRepositorio;

    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
     @GetMapping("/login")
    public String login(){
          return "login.html";  
    }
    
    

    
//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String mostrarPaginaInicioCliente(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        System.out.println("logueado: " + logueado);

       
        return "inicio.html";
    }
   
 
    
    @GetMapping("/perfil")
    public String mostrarPerfilCliente(ModelMap modelo, HttpSession session) {
    Usuario logueado = (Usuario) session.getAttribute("usuariosession");
    modelo.addAttribute("usuariosession", logueado);
    return "perfil.html";
}
  


}


    