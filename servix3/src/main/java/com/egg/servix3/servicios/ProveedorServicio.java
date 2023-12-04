/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servix3.servicios;

import com.egg.servix3.entidades.Proveedor;
import com.egg.servix3.entidades.Usuario;
import com.egg.servix3.excepciones.MiException;
import com.egg.servix3.repositorios.ProveedorRepositorio;
import com.egg.servix3.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Transactional
    public void crearProveedor(String nombre, String telefono, String email, String password, String password2, String tipo_servicio, String descripcion) throws MiException {
        
        validar(email);
        Usuario usuario = usuarioServicio.crearUsuario(nombre, telefono, password, password2);

        Proveedor proveedor = new Proveedor();

        proveedor.setEmail(email);
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        proveedor.setUsuario(usuario);

        proveedorRepositorio.save(proveedor);
    }

    public List<Proveedor> listarProveedor() {

        List<Proveedor> proveedores = new ArrayList();

        proveedores = proveedorRepositorio.findAll();

        return proveedores;
    }

    public List<Proveedor> listarProveedorPorTipoServicio(String tipo_servicio) {

        List<Proveedor> proveedores = proveedorRepositorio.buscarPorTipoServicio(tipo_servicio);

        return proveedores;
    }

    @Transactional
    public void modificarProveedor(String nombre, String telefono, String email, String password, String password2, String tipo_servicio, String descripcion) throws MiException {

        validar(email);
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(email);

        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();

            Usuario usuario = proveedor.getUsuario();

            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setPassword(password);

            proveedor.setTipo_servicio(tipo_servicio);
            proveedor.setDescripcion(descripcion);

            proveedorRepositorio.save(proveedor);
        }

    }

    public void validar(String email) throws MiException {

        if (proveedorRepositorio.existsByEmail(email)) {
            throw new MiException("El correo electrónico ya está registrado.");
        }

    }

}
