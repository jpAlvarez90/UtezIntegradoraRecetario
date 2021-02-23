package edu.utez.recetario.service;

import edu.utez.recetario.model.SubCategoria;
import edu.utez.recetario.repository.SubCategoriaRepository;
import edu.utez.recetario.serviceInterface.SubCategoriaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoriaService implements SubCategoriaInterface {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Override
    public List<SubCategoria> getAllSubCategorias() {
        return subCategoriaRepository.findAll();
    }

    @Override
    public void saveSubCategoria(SubCategoria subCategoria) {
        subCategoriaRepository.save(subCategoria);
    }

    @Override
    public SubCategoria getSubCategoriaById(long id) {
        Optional<SubCategoria> optional = subCategoriaRepository.findById(id);
        SubCategoria subCategoria = null;
        if (optional.isPresent()) {
            subCategoria = optional.get();
        } else {
            throw new RuntimeException("SubCategoria not found for id :: " +id);
        }
        return subCategoria;
    }

    @Override
    public void deleteSubCategoriaById(long id) {
        subCategoriaRepository.deleteById(id);
    }
}
