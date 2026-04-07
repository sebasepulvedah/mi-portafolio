package com.edu.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.manager.models.Usuario;
import com.edu.manager.services.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        //método findAll() UsuarioService
        model.addAttribute("usuarios", usuarioService.findAll());
        return "admin/usuarios"; 
    }
    
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        // verificar que no elimine admin.
        usuarioService.deleteById(id);
        return "redirect:/admin/usuarios?deleted";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormEditar(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        return "admin/editar_usuario"; //
    }

    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        // El objeto 'usuario' ya trae dentro el objeto 'estudiante' con sus datos editados
        // th:field="*{estudiante.nombre}" 
        usuarioService.updateUser(usuario);
        return "redirect:/admin/usuarios?success";
    }
}