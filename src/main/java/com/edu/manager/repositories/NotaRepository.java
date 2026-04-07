package com.edu.manager.repositories;

import com.edu.manager.models.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
}