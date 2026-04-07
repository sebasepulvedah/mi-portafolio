package com.edu.manager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.manager.dto.UsuarioDTO;
import com.edu.manager.models.Estudiante;
import com.edu.manager.models.Usuario;
import com.edu.manager.repositories.EstudianteRepository;
import com.edu.manager.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

	private final PasswordEncoder passwordEncoder; //inicializar valor. inyeccion de pw encoder 
													//y lo tratamos como atributo
	
    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    public UsuarioServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveUser(UsuarioDTO usuarioDTO) {

        // Crear usuario
        Usuario user = mapeoDTOaUser(usuarioDTO);

        // GUARDAR Y CAPTURAR RESULTADO
        Usuario savedUser = userRepository.save(user);

        // Crear estudiante asociado
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(user.getUsername());
        estudiante.setUsuario(savedUser);

        estudianteRepository.save(estudiante);
    }

    private Usuario mapeoDTOaUser(UsuarioDTO userDTO) {
        Usuario user = new Usuario();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        
        // REGLA DE NEGOCIO: Si no viene rol, sea asigna ROLE_USER por defecto
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
    
        }else {
        	//rol guardado con prefijo estandar
        	
        	String role = userDTO.getRole().toUpperCase();
            user.setRole(role.startsWith("ROLE_") ? role : "ROLE_" + role);
        }

        String newPass = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(newPass);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByEmail(email);
    
        if (usuario == null) {
            throw new UsernameNotFoundException("Email no registrado: " + email);
        }
        
        
        /*Usuario user  = usuarios.stream().filter(x->email.equals(x.getEmail())).findFirst().orElse(null);
        if(user!= null) {
        	throw new UsernameNotFoundException("Ocupa otro correo " + email);
        	}*/
        
        	
        // 1. Limpiamos el rol para Spring Security (ej: "ROLE_USER" -> "USER")
        // 2. Manejamos el caso de que sea null en la DB para evitar NullPointerException
        String nombreRol = (usuario.getRole() != null) 
                           ? usuario.getRole().replace("ROLE_", "") 
                           : "USER";
        
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(nombreRol) // .roles() agregará el prefijo ROLE_ internamente
                .build();
    }

    // --- Métodos de búsqueda ---
    @Override
    public Usuario findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Usuario findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void actualizarPerfil(Usuario usuarioExistente, String username, String email, String nuevaPassword, 
                                 String nombreReal, String apellido, String dni) {
        
        // 1. Actualizar datos de la cuenta (Usuario)
        usuarioExistente.setUsername(username);
        usuarioExistente.setEmail(email);

        if (nuevaPassword != null && !nuevaPassword.trim().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(nuevaPassword));
        }

        // 2. Actualizar datos personales (Estudiante)
        // Obtenemos el estudiante asociado al usuario
        // esto es lo que mas costo entender, la relacion estudiante, usuario.
        Estudiante est = usuarioExistente.getEstudiante();
        if (est != null) {
            est.setNombre(nombreReal);
            est.setApellido(apellido);
            est.setDni(dni);
            // Al estar en una transacción y ser una relación gestionada, 
            // se guardará automáticamente:
            estudianteRepository.save(est);
        }

        userRepository.save(usuarioExistente);
    }

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public Usuario findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void updateUser(Usuario user) {
	    Usuario existente = userRepository.findById(user.getId()).orElse(null);
	    if (existente != null) {
	        existente.setUsername(user.getUsername());
	        existente.setEmail(user.getEmail());
	        existente.setRole(user.getRole());

	        // Actualizar datos del estudiante 
	        if (user.getEstudiante() != null && existente.getEstudiante() != null) {
	            existente.getEstudiante().setNombre(user.getEstudiante().getNombre());
	            existente.getEstudiante().setApellido(user.getEstudiante().getApellido());
	            existente.getEstudiante().setDni(user.getEstudiante().getDni());
	        }
	        
	        userRepository.save(existente);
	    }
	}
}