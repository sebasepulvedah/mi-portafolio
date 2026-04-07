package com.edu.manager.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.manager.models.Curso;
import com.edu.manager.models.Usuario;
import com.edu.manager.repositories.CursoRepository;
import com.edu.manager.services.CursoService;
import com.edu.manager.services.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursoRepository cursoRepository;

    // LISTAR CURSOS 
    @GetMapping
    public String listarCursos(Model model, Principal principal) {
        // Obtener el email del usuario logueado desde el objeto Principal
        String email = principal.getName();
        
        // Buscar al usuario completo en la BD para obtener su ID y Rol
        Usuario usuario = usuarioService.findByEmail(email);
        
        List<Curso> cursosMostrados;

        // Lógica de filtrado:
        // ADMIN, ve todos los cursos
        if (usuario.getRole().equals("ROLE_ADMIN")) {
            cursosMostrados = cursoService.findAll();
        } else {
            // Si es USER, solo ve los cursos donde está inscrito
            
            cursosMostrados = cursoRepository.findByUsuariosId(usuario.getId());
        }
        
        model.addAttribute("cursos", cursosMostrados);
        model.addAttribute("titulo", "Mis Cursos");
        return "cursos/lista";
    }

    // FORMULARIO NUEVO CURSO (Solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/nuevo")
    public String nuevoCurso(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("titulo", "Crear Nuevo Curso");
        return "cursos/formC";
    }

    // GUARDAR CURSO (Solo ADMIN)
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarCurso(@Valid @ModelAttribute Curso curso,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error en el formulario");
            return "cursos/formC";
        }

        cursoService.save(curso);
        return "redirect:/cursos";
    }
    
 // Ver todos los cursos disponibles para inscribirse
    @GetMapping("/disponibles")
    public String cursosDisponibles(Model model) {
        // Obtenemos todos los cursos de la base de datos a través del servicio
        model.addAttribute("cursos", cursoService.findAll());
        model.addAttribute("titulo", "Catálogo de Cursos Disponibles");
        return "cursos/disponibles"; // Nombre del archivo HTML en templates/cursos/
    }

    // Procesar la inscripción del usuario logueado
    @PostMapping("/inscribir/{id}")
    public String inscribir(@PathVariable("id") Long cursoId, Principal principal) {
        // Obtenemos el usuario que tiene la sesión iniciada
        String email = principal.getName();
        Usuario usuario = usuarioService.findByEmail(email);
        
        // Llamamos al método que creamos en el Service para realizar la asociación
        cursoService.inscribirUsuario(cursoId, usuario);
        
        // Redirigimos a la lista de "Mis Cursos" 
        return "redirect:/cursos";
    }
    
    
 // --- VER DETALLE ---
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Curso curso = cursoService.findById(id).orElse(null);
        if (curso == null) {
            return "redirect:/cursos";
        }
        model.addAttribute("curso", curso);
        model.addAttribute("titulo", "Detalle del Curso: " + curso.getNombre());
        return "cursos/detalle"; // 
    }

    // --- EDITAR CURSO (Solo ADMIN) ---
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable("id") Long id, Model model) {
        Curso curso = cursoService.findById(id).orElse(null);
        if (curso == null) {
            return "redirect:/cursos";
        }
        model.addAttribute("curso", curso);
        model.addAttribute("titulo", "Editar Curso");
        return "cursos/formC"; // 
    }
    
}