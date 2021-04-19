package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Bitacora;

import java.util.List;

public interface BitacoraInterface {

    Bitacora saveBitacora(Bitacora bitacora);

    List<Bitacora> getAllBitacora();
}
