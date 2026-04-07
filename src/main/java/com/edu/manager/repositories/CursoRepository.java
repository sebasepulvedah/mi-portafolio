package com.edu.manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.manager.models.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	
	// Busca cursos que contengan al usuario con ese ID
	List<Curso> findByUsuariosId(Long id);
	@Query("SELECT c FROM Curso c LEFT JOIN FETCH c.usuarios WHERE c.id = :id")
	Curso findCursoWithUsuarios(@Param("id") Long id);
}
