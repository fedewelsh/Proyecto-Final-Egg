/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servix2.repositorios;

import com.egg.servix2.entidades.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {
    
    @Query("SELECT p FROM Proveedor p WHERE p.tipo_servicio = :tipo_servicio")
    List<Proveedor> buscarPorTipoServicio(@Param("tipo_servicio") String tipo_servicio);
    
    @Query("SELECT e FROM Proveedor e WHERE e.email = :email")    //HAY QUE VER SI NO SE DEBE PONER e.usuario.email
    public Proveedor buscarPorEmail(@Param("email")String email);

    public boolean existsByEmail(String email);
    
}
