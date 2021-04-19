package edu.utez.recetario.service;

import edu.utez.recetario.model.Categoria;
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

    private  UsuarioService userService;

    @Override
    public List<SubCategoria> getAllSubCategorias() {

        try {
            return subCategoriaRepository.findAll();
        }catch (Exception e){
            userService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public SubCategoria saveSubCategoria(SubCategoria subCategoria) {
        return subCategoriaRepository.save(subCategoria);
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

    @Override
    public List<SubCategoria> getAllSubcategoriasByCategoria(Categoria categoria) {
        return subCategoriaRepository.findAllByCategoria(categoria);
    }
}
