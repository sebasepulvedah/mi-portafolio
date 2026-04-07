package com.edu.manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactivar CSRF solo para la API REST (necesario para Postman)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            )
            
            // 2. Configuración de autorizaciones
            .authorizeHttpRequests(auth -> auth
                // Recursos estáticos y páginas públicas
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/", "/login", "/registro/**").permitAll()
                
                // API REST (Acceso libre para pruebas iniciales en Postman)
                .requestMatchers("/api/**").permitAll() 
                
                // Rutas de administración
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/cursos/nuevo", "/cursos/guardar").hasRole("ADMIN")
                
                // Rutas de usuario/estudiante
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/cursos/inscribir/**").authenticated()
                .requestMatchers("/cursos/**").hasAnyRole("ADMIN", "USER")
                
                // Todo lo demás requiere login
                .anyRequest().authenticated()
            )
            
            // Configuración del Login
            .formLogin(login -> login
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            
            // Configuración del Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}