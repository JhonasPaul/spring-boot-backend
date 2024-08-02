package com.jortiz.model.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface iUploadFileService {
    /*subir foto*/
    public String copiar(MultipartFile archivo) throws IOException;
    /*ver foto*/
    public Resource cargar(String nombrefoto) throws MalformedURLException;
    public boolean eliminar (String nombreFoto);
    public Path getPath(String nombreFoto);
}
