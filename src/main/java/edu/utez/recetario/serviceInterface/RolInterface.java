package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Rol;

import java.util.List;

public interface RolInterface {

    List<Rol> getAllRoles();

    Rol saveRole(Rol rol);

    Rol getRole(Long id);

    void deleteRole(Long id);

}
