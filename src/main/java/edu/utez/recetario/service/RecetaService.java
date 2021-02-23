package edu.utez.recetario.service;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.repository.RecetaRepository;
import edu.utez.recetario.serviceInterface.RecetaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService implements RecetaInterface {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public List<Receta> getAllRecetas() {
        return recetaRepository.findAll();
    }

    @Override
    public void saveReceta(Receta receta) {
        recetaRepository.save(receta);
    }

    @Override
    public Receta getRecetaById(Long id) {
        Optional<Receta> optional = recetaRepository.findById(id);
        Receta receta = null;
        if (optional.isPresent()) {
            receta = optional.get();
        } else {
            throw new RuntimeException("Receta not found for id :: "+id);
        }
        return receta;
    }

    @Override
    public void deleteRecetaById(Long id) {
        recetaRepository.deleteById(id);
    }
}