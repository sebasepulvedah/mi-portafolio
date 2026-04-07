package com.edu.manager.services;

import java.util.List;
import com.edu.manager.models.Nota;

public interface NotaService {
    void guardarNota(Double valor, Long estudianteId, Long cursoId);
    List<Nota> buscarPorEstudianteYCurso(Long estudianteId, Long cursoId);
    void eliminarNota(Long id);
}