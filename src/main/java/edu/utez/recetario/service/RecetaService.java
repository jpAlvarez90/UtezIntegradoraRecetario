package edu.utez.recetario.service;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
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

    private UsuarioService usuarioService;
    @Override
    public List<Receta> getAllRecetas() {
        try {
            return recetaRepository.findAll();
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }

    }

    @Override
    public Receta saveReceta(Receta receta) {
        try {
            return recetaRepository.save(receta);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Receta getRecetaById(Long id) {
        try {
            Optional<Receta> optional = recetaRepository.findById(id);
            Receta receta = null;
            if (optional.isPresent()) {
                receta = optional.get();
            } else {
                throw new RuntimeException("Receta not found for id :: "+id);
            }
            return receta;
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public void deleteRecetaById(Long id) {
        try {
            recetaRepository.deleteById(id);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
        }
    }

    @Override
    public List<Receta> getAllRecetasByRecetario(Recetario recetario) {
        try {
            return recetaRepository.findAllByRecetario(recetario);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public List<Receta> getAllRecetasByOrderADesc(int limit) {
        try {
            return recetaRepository.findByOrderByIdRecetaDesc(limit);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public List<Receta> getAllRecetasByVistasDesc(int limit) {
        try {
            return recetaRepository.findByOrderByVistasDesc(limit);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public int saveVistasReceta(long idReceta) {
        try {
            Optional<Receta> receta = recetaRepository.findById(idReceta);
            int views = 0;

            if (receta.isPresent()) {

                Receta temp = receta.get();
                views = temp.getVistas();
                views = views+1;

                recetaRepository.saveVistasRecetas(idReceta, views);

            }
            return views;
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return 0;
        }
    }


}
