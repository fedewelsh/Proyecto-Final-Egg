/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("el nombre no debe estar vacio o ser nulo");
            
        }
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        
        editorialRepositorio.save(editorial);      
    }
    
     public List<Editorial> listarEditorial(){
           
       List<Editorial> editoriales = new ArrayList();
        
       editoriales = editorialRepositorio.findAll();
       
       return editoriales;
    }
    
     public void modificarEditorial(String id, String nombre) throws MiException{
         
         if(id.isEmpty() || id == null){
             throw new MiException("el id de la editorial no debe estar vacio o ser nulo");
         }
         
         if(nombre.isEmpty() || nombre == null){
            throw new MiException("el nombre no debe estar vacio o ser nulo");
            
        }
         
         Optional<Editorial> respuesta = editorialRepositorio.findById(id);
         
         if(respuesta.isPresent()){
             
             Editorial editorial = respuesta.get();
             
             editorial.setNombre(nombre);
             
             editorialRepositorio.save(editorial);
             
         }
         
     }
     
     
}
