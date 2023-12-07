/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;

import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.enumeraciones.Rol;
import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioService implements  UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public Usuario crearUsuario(String nombre, String telefono, String email, String password, String pasword2)throws MiException {
        
        validar(nombre, telefono, email, password, password);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        
        
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
       
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
    }
    
     public void validar (String nombre, String telefono, String email, String password, String password2) throws MiException{
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vac�o");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException ("El telefono no puede ser nulo o estar vacio");
        }else if (telefono.length() < 3) {
            throw new MiException ("El telefono debe tenes mas de 9 digitos");
        }
        
        if (email.isEmpty()|| email == null){
            throw new MiException ("El email no puede ser nulo o estar vacio");
        }
              
               
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (usuarioRepositorio.existsByEmail(email)) {
        throw new MiException("El correo electrónico ya está registrado.");
    }
        
    }
     
     
     public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }
    
    @Transactional(readOnly=true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }
    
}
