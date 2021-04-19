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

    private  UsuarioService usuarioService;

    @Override
    public List<Categoria> getAllCategorias() {
        try {
            return categoriaRepository.findAll();
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        try {
            return categoriaRepository.save(categoria);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Categoria getCategoriaById(long id) {
        try {
            Optional<Categoria> optional = categoriaRepository.findById(id);
            Categoria categoria = null;
            if (optional.isPresent()) {
                categoria = optional.get();
            } else {
                throw new RuntimeException("Categoria not found for id :: "+id);
            }
            return categoria;
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public void deleteCategoriaById(long id) {
        try {
            categoriaRepository.deleteById(id);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
        }
    }

    @Override
    public Categoria findByNombre(String nombreCategoria) {
        try {
            return categoriaRepository.findByNombre(nombreCategoria);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }
}
