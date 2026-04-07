package com.edu.manager.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //

    @Column(unique = true)
    private String email;
    private String username;
    public List<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	private String password;

    @Transient
    private String passwordConfirmation;

    private String role;

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Estudiante estudiante;

    // MappedBy hace referencia al nombre del atributo en la clase Curso
    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private List<Curso> cursos;

    // Constructores y Getters/Setters ...

	public Estudiante getEstudiante() {
		return estudiante;
		
	}
	public Usuario(Long id, String email, String username, String password, String passwordConfirmation, String role,
			Estudiante estudiante, List<Curso> cursos) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.role = role;
		this.estudiante = estudiante;
		this.cursos = cursos;
	}
	public Usuario() {
		super();
	}
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}



	
}