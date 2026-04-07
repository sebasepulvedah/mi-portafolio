package com.edu.manager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.manager.models.Nota;
import com.edu.manager.models.Estudiante;
import com.edu.manager.models.Curso;
import com.edu.manager.repositories.NotaRepository;
import com.edu.manager.repositories.EstudianteRepository;
import com.edu.manager.repositories.CursoRepository;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional
    public void guardarNota(Double valor, Long estudianteId, Long cursoId) {
        // Buscamos las entidades reales para crear la relación
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Creamos el objeto Nota
        Nota nuevaNota = new Nota();
        nuevaNota.setValor(valor);
        nuevaNota.setEstudiante(estudiante);
        nuevaNota.setCurso(curso);

        notaRepository.save(nuevaNota);
    }

    @Override
    public List<Nota> buscarPorEstudianteYCurso(Long estudianteId, Long cursoId) {
        return notaRepository.findByEstudianteIdAndCursoId(estudianteId, cursoId);
    }

    @Override
    @Transactional
    public void eliminarNota(Long id) {
        notaRepository.deleteById(id);
    }
}