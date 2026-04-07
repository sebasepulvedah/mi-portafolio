package com.edu.manager.dto;

public class UsuarioDTO { // front-end - no usar la entidad para pasar la informacion.

	
	private Long id;
	private String email;
	private String username;
	private String password;
	private String passwordConfirmation; // agregar comparativa, comprueba o repite tu pw
	private String role;
	
	public UsuarioDTO() {
		super();
	}
	public UsuarioDTO(Long id, String email, String username, String password, String passwordConfirmation,
			String role) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.role = role;
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
