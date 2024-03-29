package edu.utez.recetario.service;

import edu.utez.recetario.model.Rol;
import edu.utez.recetario.repository.RolRepository;
import edu.utez.recetario.serviceInterface.RolInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService implements RolInterface {

    @Autowired
    private RolRepository rolRepository;

    private UsuarioService userService;

    @Override
    public List<Rol> getAllRoles() {
        try{
            return rolRepository.findAll();
        }catch (Exception e){
            userService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Rol saveRole(Rol rol) {
        try{
            return rolRepository.save(rol);
        }catch (Exception e){
            userService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Rol getRole(Long id) {
        try{
            Optional<Rol> optional = rolRepository.findById(id);
            Rol rol = null;
            if (optional.isPresent()){
                rol = optional.get();
            } else {
                throw new RuntimeException("Rol not found for id: "+id);
            }

            return rol;
        }catch (Exception e){
            userService.codigosError(e.toString());
            return null;
        }

    }

    @Override
    public void deleteRole(Long id) {
        try{
            rolRepository.deleteById(id);
        }catch (Exception e){
            userService.codigosError(e.toString());
        }

    }

}
