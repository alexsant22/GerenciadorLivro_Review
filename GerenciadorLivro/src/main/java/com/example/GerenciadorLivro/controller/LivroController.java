package com.example.GerenciadorLivro.controller;

import com.example.GerenciadorLivro.entity.Livro;
import com.example.GerenciadorLivro.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {
    // Injeção de dependência do serviço
    private final LivroService service;

    // Get all livros
    @GetMapping("/buscar")
    public ModelAndView buscarLivros() {
        return new ModelAndView("livros/list", Map.of("livros", service.getAll()));
    }

    // Formulario de criação de novo livro
    @GetMapping("/novo")
    public ModelAndView novoLivroForm() {
        return new ModelAndView("livros/novo", Map.of("livro", new Livro()));
    }

    // Salvar novo livro
    @PostMapping("/novo")
    public String salvarNovoLivro(@Valid Livro livro) {
        service.create(livro);
        return "redirect:/livros/buscar";
    }

    // Formulario de edição de livro
    @GetMapping("/editar/{id}")
    public ModelAndView editarLivroForm(@PathVariable Long id) {
        var livro = service.getById(id);
        if (livro.isPresent()) {
            return new ModelAndView("livros/editar", Map.of("livro", livro.get()));
        }
        return new ModelAndView("redirect:/livros/buscar");
    }

    // Salvar edição de livro
    @PostMapping("/editar/{id}")
    public String salvarEdicaoLivro(@PathVariable Long id, @Valid Livro livro) {
        service.update(id, livro);
        return "redirect:/livros/buscar";
    }

    // Deletar livro
    @GetMapping("/deletar/{id}")
    public String deletarLivro(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/livros/buscar";
    }
}
