package com.edu.manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.manager.models.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	
	List<Estudiante> findByUsuarioEmail(String email);

}
