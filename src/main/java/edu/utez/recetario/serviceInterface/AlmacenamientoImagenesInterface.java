package edu.utez.recetario.serviceInterface;

import org.springframework.web.multipart.MultipartFile;

public interface AlmacenamientoImagenesInterface {

    public void init();

    public void save(MultipartFile multipartFile);

}
