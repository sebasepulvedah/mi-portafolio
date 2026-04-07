package com.edu.manager.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.manager.models.Usuario;
import com.edu.manager.services.NotaService;
import com.edu.manager.services.UsuarioService;

@Controller
@RequestMapping("/notas")
public class NotaController {

    @Autowired	
    private NotaService notaService;
    
    @Autowired //
    private UsuarioService usuarioService;
    
    @PostMapping("/guardar")
    public String procesarNota(@RequestParam Double valor, 
                               @RequestParam Long estudianteId, 
                               @RequestParam Long cursoId) {
        notaService.guardarNota(valor, estudianteId, cursoId);
        return "redirect:/cursos/detalle/" + cursoId;
    }
    
    // Al dejarlo vacío, la ruta es "/notas"
    @GetMapping("") 
    public String verMisNotas(Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioService.findByEmail(email);
        
        // Verificación de seguridad para evitar errores si el usuario no tiene perfil de estudiante
        if (usuario.getEstudiante() == null) {
            return "redirect:/home"; 
        }
        
        model.addAttribute("estudiante", usuario.getEstudiante());
        model.addAttribute("cursos", usuario.getCursos()); // Pasamos los cursos directamente
        
        return "notas/mis-notas";
    }
}