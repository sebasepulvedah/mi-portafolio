package com.edu.manager.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.manager.models.Estudiante;
import com.edu.manager.repositories.EstudianteRepository;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    
    public Estudiante findByEmail(String email) {
        List<Estudiante> lista = estudianteRepository.findByUsuarioEmail(email);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    // devuelve Optional para que el RestController no falle
    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }

    public void delete(Long id) {
        estudianteRepository.deleteById(id);
    }
}