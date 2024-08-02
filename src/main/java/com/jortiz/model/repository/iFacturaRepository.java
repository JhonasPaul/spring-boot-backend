package com.jortiz.model.repository;


import com.jortiz.model.entity.Factura;
import org.springframework.data.repository.CrudRepository;

/*1.- CRUD FACTURA --> iClienteService*/
public interface iFacturaRepository extends CrudRepository<Factura, Long> {
}
