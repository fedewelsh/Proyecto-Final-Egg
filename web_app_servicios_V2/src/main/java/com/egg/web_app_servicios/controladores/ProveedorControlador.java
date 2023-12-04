/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;

import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.service.ProveedorService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {
    
    
    @Autowired
    private ProveedorService proveedorService;
    
//    
//    @GetMapping("/registrar")
//    public String registrar(){
//        return "proveedor_formulario.html";
//    }
    
    @GetMapping("/proveedor_formulario")
    public String registrarProveedor(){
        return "proveedor_formulario.html";
    }
    
     @PostMapping("/registro")
public String registro(@RequestParam String nombre, 
                       @RequestParam String password, 
                       @RequestParam String password2,
                       @RequestParam String email, 
                       @RequestParam String telefono, 
                       @RequestParam String tipo_servicio,
                       String descripcion,
                       ModelMap modelo) {
    
        System.out.println("nombre: " + nombre + "password: " + password + "email: " + email + "telefono: " + telefono + "tipo servicio: " + tipo_servicio);
        
        try {
            proveedorService.crearProveedor(email, nombre, telefono, email, password, password2, tipo_servicio, descripcion);
            modelo.put("exito", "El proveedor fue cargado exitosamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_formulario.html";
        }
        
    return "index.html";
}
    
    @GetMapping("/proveedores_list")
public String listarPorTipoServicio(@RequestParam(name = "tipo_servicio", required = false) String tipo_servicio, ModelMap modelo) {
    List<Proveedor> proveedores;

    if (tipo_servicio != null) {
        tipo_servicio = tipo_servicio.trim(); // Aplicar trim al valor
    }
    
    if (tipo_servicio != null && !tipo_servicio.isEmpty()) {
        proveedores = proveedorService.listarProveedorPorTipoServicio(tipo_servicio);
        modelo.addAttribute("tipoServicioSeleccionado", tipo_servicio);
    } else {
        proveedores = proveedorService.listarProveedor();
    }

    modelo.addAttribute("proveedores", proveedores);

    return "proveedores_list.html";
}
}
