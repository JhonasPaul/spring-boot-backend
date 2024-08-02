package com.jortiz.model.repository;


import com.jortiz.model.entity.Cliente;
import com.jortiz.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface iClienteRepository extends JpaRepository<Cliente, Long> {
    /*listar Region*/
    @Query("from Region ")
    public List<Region>findAllRegiones();
}
