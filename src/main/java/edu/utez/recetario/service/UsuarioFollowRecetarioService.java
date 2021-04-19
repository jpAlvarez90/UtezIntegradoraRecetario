package edu.utez.recetario.service;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.model.UsuarioFollowRecetario;
import edu.utez.recetario.repository.UsuarioFollowRecetarioRepository;
import edu.utez.recetario.serviceInterface.UsuarioFollowRecetarioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioFollowRecetarioService implements UsuarioFollowRecetarioInterface {

    private UsuarioFollowRecetarioRepository usuarioFollowRecetarioRepository;
    private UsuarioService userService;

    @Autowired
    public UsuarioFollowRecetarioService(UsuarioFollowRecetarioRepository usuarioFollowRecetarioRepository) {
        try {
            this.usuarioFollowRecetarioRepository = usuarioFollowRecetarioRepository;
        }catch (Exception e){
            userService.codigosError(e.toString());
        }
    }

    @Override
    public List<UsuarioFollowRecetario> getAllFollowingRecetarios(Usuario usuario) {
        try {
            return usuarioFollowRecetarioRepository.findAllByUsuario(usuario);
        }catch (Exception e){
            userService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public boolean saveUsuarioFollowRecetario(Recetario recetario, Usuario usuario) {

        try {
            boolean isRegistered = false;

            List<UsuarioFollowRecetario> usuarioFollowRecetarioList = usuarioFollowRecetarioRepository.findAll();

            // Primer paso: Verificar que el usuario no siga su propio recetario
            if (!recetario.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
                for (UsuarioFollowRecetario ufr: usuarioFollowRecetarioList) {
                    // Segundo paso: Verificar que el usuario no siga un recetario que ya esta siguiendo
                    if ((ufr.getRecetario().getIdRecetario().equals(recetario.getIdRecetario()))
                            &&
                            (ufr.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()))){
                        isRegistered = true;
                        break;
                    }
                }
            } else {
                isRegistered = true;
            }

            if (!isRegistered) {
                UsuarioFollowRecetario ufr = new UsuarioFollowRecetario(recetario,usuario);
                usuarioFollowRecetarioRepository.save(ufr);
            }
            return isRegistered;
        }catch (Exception e){
            userService.codigosError(e.toString());
            return false;
        }
    }

    @Override
    public UsuarioFollowRecetario getUsuarioFollowingRecetario(Usuario usuario, Recetario recetario) {
        try {
            return usuarioFollowRecetarioRepository.findByUsuarioAndRecetario(usuario, recetario);
        }catch (Exception e){
            userService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public void deleteUsuarioFollowRecetario(UsuarioFollowRecetario ufr) {
        try{
            usuarioFollowRecetarioRepository.delete(ufr);
        }catch (Exception e){
            userService.codigosError(e.toString());
        }
    }

}
