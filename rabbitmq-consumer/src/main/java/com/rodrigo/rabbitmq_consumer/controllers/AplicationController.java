package com.rodrigo.rabbitmq_consumer.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AplicationController {
    
    @GetMapping("/index")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public String index() {
        return "paciente/index"; // Verifique o caminho da página inicial
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "paciente/login";
    }
}
