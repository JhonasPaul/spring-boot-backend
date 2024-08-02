package com.jortiz.model.repository;


import com.jortiz.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*1.- CREAR FACTURA autocomplete --> iClienteService*/
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("select p from Producto p  where p.nombre like %?1%")/*doble % buscar en cualquier parte de la cadena*/
    public List<Producto> findByName(String term);

    /*busca cualquier letra de la palabra*/
    public List<Producto> findByNombreContainingIgnoreCase(String term);


}
