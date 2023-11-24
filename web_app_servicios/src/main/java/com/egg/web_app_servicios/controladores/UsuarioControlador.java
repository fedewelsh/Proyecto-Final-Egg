/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;

import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.service.UsuarioService;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioService usuarioService;
        
    @GetMapping("/registrar")
    public String registrar(){
        return "usuario_formulario.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String telefono, @RequestParam String email, @RequestParam String barrio, @RequestParam String direccion){
        
        try {
            usuarioService.crearUsuario(nombre, telefono, email, barrio, direccion);
        } catch (MiException ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "usuario_form.html";
        }
        
        return "index.html";
    }
    
    
 
    
}
