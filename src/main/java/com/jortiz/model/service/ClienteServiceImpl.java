package com.jortiz.model.service;


import com.jortiz.model.entity.Cliente;
import com.jortiz.model.entity.Factura;
import com.jortiz.model.entity.Producto;
import com.jortiz.model.entity.Region;
import com.jortiz.model.repository.ProductoRepository;
import com.jortiz.model.repository.iClienteRepository;
import com.jortiz.model.repository.iFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements iClienteService{
    @Autowired
    iClienteRepository repository;

    /*3.- CREAR FACTURA autocomplete*/
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    private iFacturaRepository facturaRepository;

    @Override
    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    @Override
    public Page<Cliente> listarClientes(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Cliente agregarCliente(Cliente idcliente) {
        return repository.save(idcliente);
    }

    @Override
    public Cliente listarClientePorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminarCliente(Long id) {
        repository.deleteById(id);
    }

    /*listar Region*/
    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegiones() {
        return repository.findAllRegiones();
    }




    /*@autowrite iFacturaRepository*/
    /*detalle de la factura*/
    /*CRUD FACTURA detalle factura*/
    @Override
    @Transactional(readOnly = true)
    public Factura listarFacturaPorId(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    /*3 FACATURA autocoomplete, CRUD FACTURA agregar*/
    @Transactional
    @Override
    public Factura agregarFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    /*2.-CRUD FACTURA eliminar factura -->FacturaRestController*/
    @Transactional
    @Override
    public void eliminarFactura(Long id) {
        facturaRepository.deleteById(id);
    }


    /*3.1.- CRUD FACTURA CREAR FACTURA autocomplete -->FacturaRescontroller*/
    @Transactional
    @Override
    public List<Producto> findProductoByName(String term) {
        return this.productoRepository.findByName(term);
    }
}
