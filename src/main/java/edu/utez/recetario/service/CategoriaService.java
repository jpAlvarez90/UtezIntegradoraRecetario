package edu.utez.recetario.service;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.repository.CategoriaRepository;
import edu.utez.recetario.serviceInterface.CategoriaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements CategoriaInterface {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria getCategoriaById(long id) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        Categoria categoria = null;
        if (optional.isPresent()) {
            categoria = optional.get();
        } else {
            throw new RuntimeException("Categoria not found for id :: "+id);
        }
        return categoria;
    }

    @Override
    public void deleteCategoriaById(long id) {
        categoriaRepository.deleteById(id);
    }
}
