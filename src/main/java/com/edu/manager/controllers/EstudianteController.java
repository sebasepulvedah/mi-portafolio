package com.edu.manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.edu.manager.models.Estudiante;
import com.edu.manager.models.Usuario;
import com.edu.manager.services.EstudianteService;
import com.edu.manager.services.UsuarioService;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

	private final EstudianteService es;
	@Autowired
	private UsuarioService usuarioService;
	
	public EstudianteController(EstudianteService estudianteService) {
		this.es= estudianteService;
	}
	
	@GetMapping("")
	public String inicio(Model model) {
	    model.addAttribute("estudiantes", es.findAll());
	    return "estudiantes/index";
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/registrar")
	public String registrar(Model model) {
	    model.addAttribute("estudiante", new Estudiante());
	    model.addAttribute("usuarios", usuarioService.findAll());
	    return "estudiantes/form";
	}
		
	
	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante, RedirectAttributes flash) {
        // Si el estudiante ya trae un usuario del form, se mantiene. 
        // Si no, se asocia al actual 
        es.save(estudiante);
        flash.addFlashAttribute("success", "Estudiante guardado con éxito");
        return "redirect:/estudiantes";
    }
}
