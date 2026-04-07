package com.edu.manager.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.manager.models.Usuario;
import com.edu.manager.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. MÉTODO PARA MOSTRAR LA VISTA (GET)
    @GetMapping("/editar")
    public String mostrarFormulario(Model model, Principal principal) {
        // Obtenemos el usuario por email (que es el username en Spring Security)
        String email = principal.getName();
        Usuario usuario = usuarioService.findByEmail(email);
        
        model.addAttribute("usuario", usuario);
        return "usuario/editar"; 
    }

    // 2. MÉTODO PARA PROCESAR LA ACTUALIZACIÓN (POST)
    // aca se pueden pasar username y name (usuario y estudiante)
    @PostMapping("/actualizar")
    public String actualizarPerfil(
            @RequestParam("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("nombre") String nombre, 
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam(value = "nuevaPassword", required = false) String nuevaPassword,
            RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioService.findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Pasamos todos los datos al servicio
        usuarioService.actualizarPerfil(usuario, username, email, nuevaPassword, nombre, apellido, dni);

        redirectAttributes.addFlashAttribute("mensaje", "Perfil y datos de estudiante actualizados");
        return "redirect:/home";
    }
}