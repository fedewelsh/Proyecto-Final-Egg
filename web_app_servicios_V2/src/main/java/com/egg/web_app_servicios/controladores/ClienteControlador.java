/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;

import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.service.ClienteService;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteService clienteService;
    
   
        
//    @GetMapping("/registrar")
//    public String registrar(){
//        return "usuario_formulario.html";
//    }
    
    @GetMapping("/cliente_formulario")
    public String registrarUusario(){
        return "cliente_formulario.html";
    }
    
    
   
    
    @PostMapping("/registro")
public String registro(@RequestParam String nombre, 
                       @RequestParam String password, 
                       @RequestParam String password2,
                       @RequestParam String direccion, 
                       @RequestParam String barrio, 
                       @RequestParam String email, 
                       @RequestParam String telefono,
                       ModelMap modelo) {
    
        System.out.println("nombre: " + nombre + "password: " + password + "direccion: " + direccion + "barrio: " + barrio + "email: " + email);
        
        try {
            clienteService.crearUsuario(nombre, telefono, email, barrio, direccion, password, password2);
            modelo.put("exito", "El usuario fue cargado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "cliente_formulario.html";
        }
        
    return "index.html";
}
    
 
    
}
