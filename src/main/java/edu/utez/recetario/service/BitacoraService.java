package edu.utez.recetario.service;

import edu.utez.recetario.model.Bitacora;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.BitacoraRepository;
import edu.utez.recetario.serviceInterface.BitacoraInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BitacoraService implements BitacoraInterface {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    BitacoraRepository bitacoraRepository;

    @Override
    public Bitacora saveBitacora(Bitacora bitacora) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
            Usuario usuario = tempUsuario.get();

            if (tempUsuario.isEmpty()) {
                bitacora.setUsuario("Usuario no loggeado");
            } else {
                bitacora.setUsuario(usuario.getUsername());
            }
            bitacora.setFecha(new Date());
            return bitacoraRepository.save(bitacora);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public List<Bitacora> getAllBitacoraByOrderADesc(int limit) {
        try {
           return bitacoraRepository.findByOrderByIdBitacoraDesc(limit);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }
}
