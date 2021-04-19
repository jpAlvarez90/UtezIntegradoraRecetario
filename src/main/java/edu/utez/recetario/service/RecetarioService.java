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

    private UsuarioService usuarioService;

    @Override
    public List<Recetario> getAllRecetarios() {
        try{
            return recetarioRepository.findAll();
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Recetario saveRecetario(Recetario recetario) {
        try{
            return recetarioRepository.save(recetario);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Recetario getRecetarioById(long id) {
        try{
            Optional<Recetario> optional = recetarioRepository.findById(id);
            Recetario recetario = null;
            if (optional.isPresent()) {
                recetario = optional.get();
            } else {
                throw new RuntimeException("Recetario not found for id :: "+id);
            }
            return recetario;
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }

    }

    @Override
    public void deleteRecetarioById(long id) {
        try{
            recetarioRepository.deleteById(id);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
        }
    }

    @Override
    public List<Recetario> getRecetariosByUserId(Usuario usuario) {
        try{
            return recetarioRepository.findAllByUsuario(usuario);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }
}
