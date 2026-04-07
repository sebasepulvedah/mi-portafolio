package com.edu.manager.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notas")
public class Nota  {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor; 

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

	public Nota() {
		super();
	}

	public Nota(Long id, Double valor, Estudiante estudiante, Curso curso) {
		super();
		this.id = id;
		this.valor = valor;
		this.estudiante = estudiante;
		this.curso = curso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

    
    
}