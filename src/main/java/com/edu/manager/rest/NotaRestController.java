package com.edu.manager.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edu.manager.models.Nota;
import com.edu.manager.services.NotaService;

@RestController
@RequestMapping("/api/notas")
public class NotaRestController {

    @Autowired
    private NotaService notaService;

    // Obtener notas de un alumno en un curso específico vía JSON
    @GetMapping("/{estudianteId}/{cursoId}")
    public ResponseEntity<List<Nota>> obtenerNotas(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        List<Nota> notas = notaService.buscarPorEstudianteYCurso(estudianteId, cursoId);
        return ResponseEntity.ok(notas);
    }

    // Eliminar una nota de forma asíncrona
    // se agrega para prueba, buena forma.
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notaService.eliminarNota(id);
        return ResponseEntity.noContent().build();
    }
}