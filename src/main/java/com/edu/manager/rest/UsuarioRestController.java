package com.edu.manager.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.manager.models.Usuario;
import com.edu.manager.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios") // Ruta base para la API
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios (GET)
    @GetMapping
    public List<Usuario> getAll() {
        return usuarioService.findAll();
    }

    // Obtener un usuario por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Actualizar un usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario userDetails) {
        Usuario existente = usuarioService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Seteamos el ID del path al objeto que viene del body
        userDetails.setId(id);
        usuarioService.updateUser(userDetails);
        return ResponseEntity.ok(userDetails);
    }

    // Eliminar un usuario (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Usuario existente = usuarioService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}