/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.repositorios;

import com.egg.web_app_servicios.entidades.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String>{
 
//    @Query("SELECT p FROM Proveedor p WHERE p.nombre = :nombre")
//    public List<Proveedor> buscarPorNombre(@Param("nombre")String nombre);
       
    @Query("SELECT p FROM Proveedor p WHERE p.tipo_servicio = :tipo_servicio")
    List<Proveedor> findByTipoServicio(@Param("tipo_servicio") String tipo_servicio);

    public boolean existsByEmail(String email);
}
