package com.example.GerenciadorLivro.service;

import com.example.GerenciadorLivro.entity.Livro;
import com.example.GerenciadorLivro.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroService {
    // Injeção de dependência do repositório
    private final LivroRepository repository;

    // Get all livros
    public List<Livro> getAll() {
        return repository.findAll();
    }

    // Get livro by id
    public Optional<Livro> getById(Long id) {
        return repository.findById(id);
    }

    // Create new livro
    public Livro create(Livro livro) {
        return repository.save(livro);
    }

    // Update existing livro
    public Livro update(Long id, Livro livro) {
        if (repository.existsById(id)) {
            livro.setId(id);
            return repository.save(livro);
        }
        return null;
    }

    // Delete livro by id
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
