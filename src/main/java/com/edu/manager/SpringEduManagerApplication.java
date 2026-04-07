package com.edu.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringEduManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEduManagerApplication.class, args);
		 
		
	}
	/*
	 * El equipo técnico debe desarrollar una aplicación web interna que permita a los estudiantes
	 * registrarse, visualizar sus cursos, ver sus prácticas y consultar sus evaluaciones.
	 * relaciones entre entidades
	 * 
	 * */
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
 
}
