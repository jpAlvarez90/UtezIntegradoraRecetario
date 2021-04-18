package edu.utez.recetario.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AlmacenamientoImagenesService {

    public void aSave(MultipartFile multipartFile, String uploadDir, String fileName) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            if (!multipartFile.isEmpty()) {
                try(InputStream inputStream = multipartFile.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new IOException("No se pudo guardar la imagen "+fileName, e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el archivo. Error :: "+e.getMessage());
        }
    }

    public void deleteImage(String imageName) {
        try {
            if (Files.exists(Paths.get("src/main/resources/static/uploads", imageName))) {
                Files.delete(Paths.get("src/main/resources/static/uploads", imageName));
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo eliminar el archivo. Error :: "+e.getMessage());
        }

    }













}
