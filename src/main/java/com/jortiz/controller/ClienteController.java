package com.jortiz.controller;


import com.jortiz.model.entity.Cliente;
import com.jortiz.model.entity.Region;
import com.jortiz.model.service.iClienteService;
import com.jortiz.model.service.iUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200/")
public class ClienteController {

    /*para ver la ruta dee la foto(opcional)*/
    private final Logger log = LoggerFactory.getLogger(ClienteController.class);
    @Autowired
    iClienteService service;
    @Autowired
    private iUploadFileService uploadService;


    @GetMapping("/clientes")
    private List<Cliente> index() {
        return service.listarClientes();
    }

    /*paginacion*/
    /*importar Page y PageRequest de dorg.springframework.data.domain*/
    @GetMapping("/clientes/page/{page}")
    private Page<Cliente> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 6);
        return service.listarClientes(pageable);
    }

    /*validacion: solo valida si se ingresa un id inexistente y valida campos, no valida si no se ingresa un id*/
    @GetMapping("/clientes/{id}")
    /*ResponseEntity(cuerpo de la respuessta) permite manejar mensajes de error y pasar clase entitty a al responsbody(la respuesta) */
    /* se pone ? para madnar un tipo de dato generico, un cliente, un mapa de respuestas(mensajes de error)*/
    public ResponseEntity<?> show(@PathVariable Long id) {

        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        /*try para manejarerrores en la base de datos como error de sintaxis o erro de conexion, datos duplicados etc*/
        try {
            cliente = service.listarClientePorId(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            /*mensaje de error de la excepcion*/
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            /*mandamos el tipo delmapa (<Map<String, Object>>)*/
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /*validar si el cliente es = a null*/
        /*Map alamcena objetos o valores asociados a un nombre y asigna el mensaje error*/
        /*String, Object son los pares de claves con sus valores(tipo objet)*/

        if (cliente == null) {
            response.put("mensaje", "El cliente con el ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            /*mandamos el tipo delmapa (<Map<String, Object>>)*/
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        /*ResponseEntity(el cuerpo de la respuesta de la entidad)que es de  tipo Cliente con el cliente(informacion, respuesta)*/
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }


    /*validaciones: solo valida campos*/
    @PostMapping("/clientes")  /*agregar @Valid*/                                 /*BindingResult contiene los mensjes de error*/
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {

        Cliente clienteNuevo = null;
        Map<String, Object> response = new HashMap<>();
        /*valid = validar campos= @NotNull, @Email, @Size*/
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    /*stream() y map() convierten la lista de errores(getFieldErrors()) en un string*/
                    .stream()
                    /*luego se convierte a*/         /*getField(), es el nombre del campo que s evalida*/
                    .map(err -> "El campo '".concat(err.getField()).concat( "' ").concat(err.getDefaultMessage()))
                    /*nos devuelve una lista de coleccionde errores*/
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            clienteNuevo = service.agregarCliente(cliente);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ".concat(clienteNuevo.getNombre()).concat(" ha sido creado con éxito"));
        response.put("cliente", clienteNuevo);
        /*en este caso response viene con los mensajes y la entidad cliente*/
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }



    /*validacion:  valida si se ingresa un id inexistente siempre y cuando haya dato sen el json.
     Valida campos, no valida si no se ingresa un id*/
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable("id") Long id) {
        Cliente clienteActual = service.listarClientePorId(id);
//        Cliente clienteActualizado = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '".concat(err.getField()).concat( "' ").concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());
            /*manda el mensaje cuando hay error a len los campos*/
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        /*valida si el cliente con el id ingresdo existe en la base de dato*/
        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo editar,  el cliiente con el id: ".concat(id.toString()).concat("  no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            /*clienteActual viene de la base de datos*/
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());
            clienteActual.setRegion(cliente.getRegion());
            /*actualziar region*/
            clienteActual= service.agregarCliente(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente  la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "Cliente " + clienteActual.getNombre() + " actualizado con éxito");
        response.put("cliente: ", clienteActual);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            /*borrar foto*/
            Cliente cliente = service.listarClientePorId(id);
            String nombreFotoAnterior = cliente.getFoto();
            uploadService.eliminar(nombreFotoAnterior);

            service.eliminarCliente(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente  la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "Cliente eliminado con éxito!");
        /*no es necesario concatenar con el nombre poque id ya viene con el nombre*/
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    /*METODO PARA AGREGAR IMAGEN AL CLIENTE EN LA BASE DE DATOS*/
    /*RequestParam es para enviar parametros mediante una url*/
    @PostMapping("/clientes/upload")
    public ResponseEntity<?>upload(@RequestParam("archivo")MultipartFile archivo, @RequestParam("id")Long id){
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = service.listarClientePorId(id);
        /*validar si hay imagen*/
        if(!archivo.isEmpty()){
            String nombreArchivo = null;
            /*copea el archivo que se suve al servidor a la ruta escogida*/
            try {
                nombreArchivo =  uploadService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje", "error al subir la imagen del cliente");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = cliente.getFoto();
            /*si se sube una foto nueva, eliminara la anterior*/
            uploadService.eliminar(nombreFotoAnterior);

            cliente.setFoto(nombreArchivo);
            service.agregarCliente(cliente);
            response.put("cliente", cliente);
            response.put("mensaje", "has subido correctamente la imagen " + nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }



    /*metodo para mostrar foto al cliente*/
    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Path rutaArvhivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;
        try {
            /*uploadService.cargar() PARA DESCARFAR LA FOTO*/
            recurso = uploadService.cargar(nombreFoto);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        /*forzar la imagen para que se pueda descargar*/
        HttpHeaders cabecera  = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() +"\"" );
        /*recurso contiene la imagen, cebecera para forzar la descarga*/
        return new ResponseEntity<Resource>((Resource) recurso, cabecera, HttpStatus.OK);
    }

    /*listar regiones*/
    @GetMapping("/clientes/regiones")
    private List<Region> listarRegiones() {
        return service.findAllRegiones();
    }

}
