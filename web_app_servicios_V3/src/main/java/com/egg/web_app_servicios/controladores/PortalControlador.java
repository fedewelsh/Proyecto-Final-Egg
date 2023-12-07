/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;


import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.service.ClienteService;
import com.egg.web_app_servicios.service.ProveedorService;
import com.egg.web_app_servicios.service.UsuarioService;
import javax.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;



import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static org.springframework.web.servlet.function.RequestPredicates.headers;



@Controller
@RequestMapping("/")
public class PortalControlador {
    
  
   
   
   
  
   
   @Autowired
   private UsuarioService usuarioService;

    
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
    
    @GetMapping("/logout")
    public String salir(){
        return "index.html";
    }

    

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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


    