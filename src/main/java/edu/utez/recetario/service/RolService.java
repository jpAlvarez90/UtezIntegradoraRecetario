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

    @Override
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    @Override
    public void saveRole(Rol rol) {
        rolRepository.save(rol);
    }

    @Override
    public Rol getRole(Long id) {
        Optional<Rol> optional = rolRepository.findById(id);
        Rol rol = null;
        if (optional.isPresent()){
            rol = optional.get();
        } else {
            throw new RuntimeException("Rol not found for id: "+id);
        }

        return rol;
    }

    @Override
    public void deleteRole(Long id) {
        rolRepository.deleteById(id);
    }

}
