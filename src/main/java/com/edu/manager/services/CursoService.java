package com.edu.manager.services;

import java.util.List;
import java.util.Optional;
import com.edu.manager.models.Curso;
import com.edu.manager.models.Usuario;

public interface CursoService {
    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Curso save(Curso curso);
    void deleteById(Long id);
    void inscribirUsuario(Long cursoId, Usuario usuario);
}