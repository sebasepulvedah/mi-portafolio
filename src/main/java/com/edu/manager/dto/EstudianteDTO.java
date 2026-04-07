package com.edu.manager.dto;

public class EstudianteDTO { // front-end - no usar la entidad para pasar la informacion.

	
	private Long id;
	private String email;
	private String nombre;
	private String apellido;
	private String dni;
	private String curso;
	private UsuarioDTO usuarioDTO;
	
	
	public EstudianteDTO() {
		super();
	}


	public EstudianteDTO(Long id, String email, String nombre, String apellido, String dni, String curso,
			UsuarioDTO usuarioDTO) {
		super();
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.curso = curso;
		this.usuarioDTO = usuarioDTO;
	}


	public Long getId() {
		return id;
	}


	public void setIdEstudiante(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getCurso() {
		return curso;
	}


	public void setCurso(String curso) {
		this.curso = curso;
	}


	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}


	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}
	
}



	