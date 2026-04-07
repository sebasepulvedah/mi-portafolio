package com.edu.manager.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edu.manager.models.Curso;
import com.edu.manager.services.CursoService;

@RestController
@RequestMapping("/api/cursos")
public class CursoRestController {

    @Autowired
    private CursoService cursoService;

    // GET - listar cursos
    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.findAll();
    }

    // POST - crear curso
    @PostMapping
    public Curso crearCurso(@RequestBody Curso curso) {
        return cursoService.save(curso);
    }

    // GET by ID
    @GetMapping("/{id}")
    public Curso obtenerCurso(@PathVariable Long id) {
        return cursoService.findById(id).orElse(null);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminarCurso(@PathVariable Long id) {
        cursoService.deleteById(id);
    }
}