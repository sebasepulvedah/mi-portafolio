package com.edu.manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.manager.dto.UsuarioDTO;
import com.edu.manager.models.Usuario;
import com.edu.manager.services.UsuarioServiceImpl;

import jakarta.validation.Valid;

@Controller
public class WebController {
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "security/login";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
    @GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
    	// Enviamos un DTO vacío para vincularlo al formulario th:object
    	model.addAttribute("usuario", new UsuarioDTO());
    	model.addAttribute("titulo", "Registro de Nuevo Usuario");
        return "security/registro";
    }
	
    @PostMapping("/registro/guardar")
    public String registro_guardar(@Valid @ModelAttribute("usuario") UsuarioDTO userDto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes flash) {
        
        // 1. Validación: ¿El email ya existe?
        Usuario existe_usuario = usuarioServiceImpl.findByEmail(userDto.getEmail());
        if (existe_usuario != null) {
            result.rejectValue("email", "error.user",
                    "Por favor, ocupa otro correo electronico. ");
        }

        // 2. Validación: ¿Las contraseñas coinciden?
        if (userDto.getPassword() != null && !userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.user", 
                    "Las contraseñas ingresadas no coinciden");
        }

        // 3. Revisar si hubo errores (incluyendo los de anotaciones @NotNull, @Size en el DTO)
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Corrija los errores en el formulario");
           
            return "security/registro";
        }

        // 4. Guardar usuario y creación automática de Estudiante (lógica en Service)
        try {
            usuarioServiceImpl.saveUser(userDto);
            flash.addFlashAttribute("success", "¡Cuenta creada con éxito! Ahora puedes iniciar sesión.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al procesar el registro.");
            return "security/registro";
        }
    }
    
	@GetMapping("/admin/detalle")
	public String detalleAdmin(Model model) {
		return "admin";
	}
	
	@GetMapping("/user/detalle")
	public String detalleUser() {
		return "detalle_user";
	}
}