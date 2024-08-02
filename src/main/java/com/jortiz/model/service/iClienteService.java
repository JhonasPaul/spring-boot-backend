package com.jortiz.model.service;


import com.jortiz.model.entity.Cliente;
import com.jortiz.model.entity.Factura;
import com.jortiz.model.entity.Producto;
import com.jortiz.model.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iClienteService {
    public List<Cliente>listarClientes();
    /*paginacion*/
    public Page<Cliente> listarClientes(Pageable pageable);


    public Cliente agregarCliente(Cliente cliente);
    public Cliente listarClientePorId(Long  id);
    public void eliminarCliente(Long id_cliente);


    public List<Region>findAllRegiones();



    /*se puede implementar estos metodos en iClienteService porque un service es un dao manager, puede tener varios atributos de firentes daos
     * pero tienen que estar relacionados*/
    /*2.-CRUD FACTURA, detalle de la factura[2]*/
    public Factura listarFacturaPorId(Long id);

    /*2.1.- CRUD FACTURA autocomplete agregar */
    public Factura agregarFactura(Factura factura);
    /*2.2.- CRUD FACTURA eliminar factura --> ClienteServiceImpl*/
    public void eliminarFactura(Long id);


    /*2.- CRAR FACTURA autocomplete -->*/
    public List<Producto> findProductoByName(String term);

}
