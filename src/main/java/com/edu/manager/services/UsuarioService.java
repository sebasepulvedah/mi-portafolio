package com.edu.manager.services;

import java.util.List;
import com.edu.manager.dto.UsuarioDTO;
import com.edu.manager.models.Usuario;

public interface UsuarioService {

    Usuario findByEmail(String email);
    
    Usuario findByUserName(String username);
    
    void saveUser(UsuarioDTO usuarioDTO);

   //Retornamos una Lista de Usuarios real en lugar de Object
    List<Usuario> findAll();

	void actualizarPerfil(Usuario usuarioExistente, String username, String email, String nuevaPassword,
			String nombreReal, String apellido, String dni);

	void deleteById(Long id);

	Usuario findById(Long id);

	void updateUser(Usuario usuario);
}