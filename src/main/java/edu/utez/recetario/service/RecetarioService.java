package edu.utez.recetario.service;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.RecetarioRepository;
import edu.utez.recetario.serviceInterface.RecetarioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetarioService implements RecetarioInterface {

    @Autowired
    private RecetarioRepository recetarioRepository;

    @Override
    public List<Recetario> getAllRecetarios() {
        return recetarioRepository.findAll();
    }

    @Override
    public Recetario saveRecetario(Recetario recetario) {
        return recetarioRepository.save(recetario);
    }

    @Override
    public Recetario getRecetarioById(long id) {
        Optional<Recetario> optional = recetarioRepository.findById(id);
        Recetario recetario = null;
        if (optional.isPresent()) {
            recetario = optional.get();
        } else {
            throw new RuntimeException("Recetario not found for id :: "+id);
        }
        return recetario;
    }

    @Override
    public void deleteRecetarioById(long id) {
        recetarioRepository.deleteById(id);
    }

    @Override
    public List<Recetario> getRecetariosByUserId(Usuario usuario) {
        return recetarioRepository.findAllByUsuario(usuario);
    }
}
