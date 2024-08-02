package com.jortiz.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements iUploadFileService{

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private final static String DIRECTORIO_UPLOAD = "uploads";



    /*[subir la foto al servdor] la foto]se usa en el metodo upload*/
    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        /*obtener el nombre original del archivo  se guarda nombreArchivoen*/
        /*UUID para concatenar un identificador unico y aleatoreo al nombre de la archvo |                       emmplaza los espacios enblanaco por nada*/
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ","");/*--> */
        /*obtener la ruta que contiene la image | carpeta uploads| resolver el nombreArchivo para concatenar dentro del upload como unica ruta*/
        Path rutaArvhivo = getPath(nombreArchivo);/*obtenemos la ruta caompleta con la ubicacion*/
        log.info(rutaArvhivo.toString());
        /*copea el archivo que se suve al servidor a la ruta escogida*/

        Files.copy(archivo.getInputStream(),rutaArvhivo);
        return nombreArchivo;
    }
    /*se usa para ver foto(descargarla)*/
    @Override
    public Resource cargar(String nombrefoto) throws MalformedURLException {
        /*obtener la ruta que contiene la image*/
        Path rutaArvhivo = getPath(nombrefoto);
        log.info(rutaArvhivo.toString());
        /*importar Resource de core.io*/
        /*importar de import org.springframework.core.io.UrlResource;*/
        /*UrlResource carga el recurso(espacio virtual) desde el directorio del sistema*/
        Resource recurso = new UrlResource(rutaArvhivo.toUri());

        if(!recurso.exists() && !recurso.isReadable()){
            /*si se borra la iamgen del backend mostrara no-usuario.png*/
            rutaArvhivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
            recurso =  new UrlResource(rutaArvhivo.toUri());
            log.error("Error, no se pudo cargar la iamgen: " + nombrefoto);
        }
        return recurso;
    }


    @Override
    public boolean eliminar(String nombreFoto) {
        if (nombreFoto != null && nombreFoto.length() > 0){
            Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
            File archivoFotoAnterior = rutaFotoAnterior.toFile();
            if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                archivoFotoAnterior.delete();
                return true;
            }
        }
        return false;
    }


    /*obtenemos la ruta caompleta de "uploads" con la ubicacion y la retornamos*/
    @Override
    public Path getPath(String nombreFoto) {      /*resolve contiene el nombre y extencion de la foto*/
        return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
    }
}
