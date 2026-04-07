

package com.edu.manager.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.manager.models.Curso;
import com.edu.manager.models.Estudiante;
import com.edu.manager.models.Usuario;
import com.edu.manager.repositories.CursoRepository;
import com.edu.manager.repositories.EstudianteRepository;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Override
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void inscribirUsuario(Long cursoId, Usuario usuario) {
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        if (curso != null) {
            List<Usuario> inscritos = curso.getUsuarios();
            if (!inscritos.contains(usuario)) {
                inscritos.add(usuario); // Esto añade el registro a la tabla intermedia
                cursoRepository.save(curso);
                

            }
        }
    }
  }
