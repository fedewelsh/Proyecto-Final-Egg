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

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "login.html";
    }
    
    

    @GetMapping("/inicio")
    public String mostrarPaginaInicioCliente(@RequestParam String usuario, HttpSession session, ModelMap modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuariosession", logueado.getNombre());
        System.out.println(logueado.getEmail());
       
        return "inicio.html";
    }
   
 
    
    @GetMapping("/perfil")
    public String mostrarPerfilCliente(ModelMap modelo, HttpSession session) {
    Usuario logueado = (Usuario) session.getAttribute("usuariosession");
    modelo.addAttribute("usuariosession", logueado);
    return "perfil.html";
}
  


}


    