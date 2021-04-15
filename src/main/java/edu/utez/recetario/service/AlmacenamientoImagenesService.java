package edu.utez.recetario.service;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.serviceInterface.AlmacenamientoImagenesInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AlmacenamientoImagenesService implements AlmacenamientoImagenesInterface {

    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            if (!Files.exists(root))
                Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inizializar la carpeta de las imagenes");
        }
    }

    @Override
    public void save(MultipartFile multipartFile) {
        try {
            Files.copy(multipartFile.getInputStream(), this.root.resolve("receta_"+multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el archivo. Error :: "+e.getMessage());
        }
    }

    public void aSave(MultipartFile multipartFile, long idRecetario, int cont) {
        try {
            Files.copy(multipartFile.getInputStream(), this.root.resolve("receta_"+idRecetario+"_"+cont+"_"+multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el archivo. Error :: "+e.getMessage());
        }
    }

    public void deleteImage(String imageName) {
        try {
            if (Files.exists(Paths.get("uploads", imageName))) {
                Files.delete(Paths.get("uploads", imageName));
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo eliminar el archivo. Error :: "+e.getMessage());
        }

    }













}
