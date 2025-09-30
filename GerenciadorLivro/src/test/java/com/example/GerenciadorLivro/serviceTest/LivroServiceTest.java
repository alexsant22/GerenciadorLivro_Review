package com.example.GerenciadorLivro.serviceTest;

import com.example.GerenciadorLivro.entity.Livro;
import com.example.GerenciadorLivro.repository.LivroRepository;
import com.example.GerenciadorLivro.service.LivroService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para LivroService utilizando JUnit 4 e Mockito
 * Testa todos os métodos de CRUD do serviço de livros
 */
@RunWith(MockitoJUnitRunner.class) // Configura o Mockito para injetar mocks
public class LivroServiceTest {

    @Mock // Cria um mock do LivroRepository para simular o acesso ao banco
    private LivroRepository livroRepository;

    @InjectMocks // Injeta os mocks no LivroService que será testado
    private LivroService livroService;

    // Livros de exemplo para usar nos testes
    private Livro livro;
    private Livro livro2;

    /**
     * Método executado antes de cada teste
     * Inicializa os objetos de teste com dados consistentes
     */
    @Before
    public void setUp() {
        // Configura o primeiro livro de exemplo
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Dom Casmurro");
        livro.setAutor("Machado de Assis");
        livro.setEditora("Editora A");
        livro.setAnoPublicacao(1899);
        livro.setPreco(BigDecimal.valueOf(29.90));

        // Configura o segundo livro de exemplo
        livro2 = new Livro();
        livro2.setId(2L);
        livro2.setTitulo("O Cortiço");
        livro2.setAutor("Aluísio Azevedo");
        livro2.setEditora("Editora B");
        livro2.setAnoPublicacao(1890);
        livro2.setPreco(BigDecimal.valueOf(34.90));
    }

    /**
     * Testa o método getAll() quando existem livros no repositório
     * Verifica se retorna uma lista com todos os livros
     */
    @Test
    public void testGetAll_DeveRetornarListaDeLivros() {
        // Arrange (Preparação): Configura o comportamento do mock
        List<Livro> livros = Arrays.asList(livro, livro2);
        when(livroRepository.findAll()).thenReturn(livros); // Simula retorno do repository

        // Act (Ação): Executa o método a ser testado
        List<Livro> resultado = livroService.getAll();

        // Assert (Verificação): Verifica os resultados esperados
        assertNotNull("A lista retornada não deve ser nula", resultado);
        assertEquals("Deve retornar 2 livros", 2, resultado.size());
        assertEquals("Primeiro livro deve ser Dom Casmurro", "Dom Casmurro", resultado.get(0).getTitulo());
        assertEquals("Segundo livro deve ser O Cortiço", "O Cortiço", resultado.get(1).getTitulo());
        verify(livroRepository, times(1)).findAll(); // Verifica se o método foi chamado uma vez
    }

    /**
     * Testa o método getById() quando o livro existe
     * Verifica se retorna o livro correto encapsulado em Optional
     */
    @Test
    public void testGetById_QuandoLivroExiste_DeveRetornarLivro() {
        // Arrange: Configura o mock para retornar um livro existente
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        // Act: Busca o livro por ID
        Optional<Livro> resultado = livroService.getById(1L);

        // Assert: Verifica se o livro foi encontrado e tem os dados corretos
        assertTrue("Optional deve conter um livro", resultado.isPresent());
        assertEquals("Título deve ser Dom Casmurro", "Dom Casmurro", resultado.get().getTitulo());
        assertEquals("Autor deve ser Machado de Assis", "Machado de Assis", resultado.get().getAutor());
        verify(livroRepository, times(1)).findById(1L); // Verifica chamada ao repository
    }

    /**
     * Testa o método getById() quando o livro não existe
     * Verifica se retorna um Optional vazio
     */
    @Test
    public void testGetById_QuandoLivroNaoExiste_DeveRetornarOptionalVazio() {
        // Arrange: Configura o mock para retornar Optional vazio (livro não encontrado)
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act: Busca um livro com ID inexistente
        Optional<Livro> resultado = livroService.getById(99L);

        // Assert: Verifica que nenhum livro foi encontrado
        assertFalse("Optional deve estar vazio para ID inexistente", resultado.isPresent());
        verify(livroRepository, times(1)).findById(99L); // Verifica chamada ao repository
    }

    /**
     * Testa o método create() para salvar um novo livro
     * Verifica se o livro é salvo e retornado com ID gerado
     */
    @Test
    public void testCreate_DeveSalvarERetornarLivro() {
        // Arrange: Prepara um novo livro e configura o mock
        Livro novoLivro = new Livro();
        novoLivro.setTitulo("Novo Livro");
        novoLivro.setAutor("Novo Autor");

        // Livro mock que será retornado pelo repository após salvar
        Livro livroSalvo = new Livro();
        livroSalvo.setId(1L); // ID gerado pelo banco
        livroSalvo.setTitulo("Dom Casmurro");
        livroSalvo.setAutor("Machado de Assis");
        livroSalvo.setEditora("Editora A");
        livroSalvo.setAnoPublicacao(1899);
        livroSalvo.setPreco(BigDecimal.valueOf(29.90));

        when(livroRepository.save(any(Livro.class))).thenReturn(livroSalvo); // Simula salvamento

        // Act: Executa a criação do livro
        Livro resultado = livroService.create(novoLivro);

        // Assert: Verifica se o livro foi salvo e retornado corretamente
        assertNotNull("Livro salvo não deve ser nulo", resultado);
        assertEquals("ID do livro deve ser 1", 1L, resultado.getId().longValue());
        assertEquals("Título deve ser Dom Casmurro", "Dom Casmurro", resultado.getTitulo());
        assertEquals("Autor deve ser Machado de Assis", "Machado de Assis", resultado.getAutor());
        verify(livroRepository, times(1)).save(novoLivro); // Verifica se save foi chamado
    }

    /**
     * Testa o método update() quando o livro existe
     * Verifica se o livro é atualizado corretamente
     */
    @Test
    public void testUpdate_QuandoLivroExiste_DeveAtualizarERetornarLivro() {
        // Arrange: Prepara livro atualizado e configura mocks
        Livro livroAtualizado = new Livro();
        livroAtualizado.setTitulo("Dom Casmurro Atualizado");
        livroAtualizado.setAutor("Machado de Assis");
        livroAtualizado.setAnoPublicacao(1900);

        when(livroRepository.existsById(1L)).thenReturn(true); // Simula que livro existe
        when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado); // Simula atualização

        // Act: Executa a atualização do livro
        Livro resultado = livroService.update(1L, livroAtualizado);

        // Assert: Verifica se o livro foi atualizado
        assertNotNull("Livro atualizado não deve ser nulo", resultado);
        assertEquals("Título deve ser atualizado", "Dom Casmurro Atualizado", resultado.getTitulo());
        verify(livroRepository, times(1)).existsById(1L); // Verifica verificação de existência
        verify(livroRepository, times(1)).save(livroAtualizado); // Verifica salvamento
    }

    /**
     * Testa o método update() quando o livro não existe
     * Verifica se retorna null quando tentar atualizar livro inexistente
     */
    @Test
    public void testUpdate_QuandoLivroNaoExiste_DeveRetornarNull() {
        // Arrange: Configura mock para indicar que livro não existe
        when(livroRepository.existsById(99L)).thenReturn(false);

        // Act: Tenta atualizar livro com ID inexistente
        Livro resultado = livroService.update(99L, livro);

        // Assert: Verifica que retornou null (não atualizou)
        assertNull("Deve retornar null para livro inexistente", resultado);
        verify(livroRepository, times(1)).existsById(99L); // Verifica verificação de existência
        verify(livroRepository, never()).save(any(Livro.class)); // Verifica que NÃO salvou
    }

    /**
     * Testa o método delete() quando o livro existe
     * Verifica se o livro é deletado e retorna true
     */
    @Test
    public void testDelete_QuandoLivroExiste_DeveDeletarERetornarTrue() {
        // Arrange: Configura mock para indicar que livro existe
        when(livroRepository.existsById(1L)).thenReturn(true);

        // Act: Executa a exclusão do livro
        boolean resultado = livroService.delete(1L);

        // Assert: Verifica que exclusão foi bem-sucedida
        assertTrue("Deve retornar true para exclusão bem-sucedida", resultado);
        verify(livroRepository, times(1)).existsById(1L); // Verifica verificação de existência
        verify(livroRepository, times(1)).deleteById(1L); // Verifica chamada de exclusão
    }

    /**
     * Testa o método delete() quando o livro não existe
     * Verifica se retorna false quando tentar deletar livro inexistente
     */
    @Test
    public void testDelete_QuandoLivroNaoExiste_DeveRetornarFalse() {
        // Arrange: Configura mock para indicar que livro não existe
        when(livroRepository.existsById(99L)).thenReturn(false);

        // Act: Tenta excluir livro com ID inexistente
        boolean resultado = livroService.delete(99L);

        // Assert: Verifica que retornou false (não excluiu)
        assertFalse("Deve retornar false para livro inexistente", resultado);
        verify(livroRepository, times(1)).existsById(99L); // Verifica verificação de existência
        verify(livroRepository, never()).deleteById(any(Long.class)); // Verifica que NÃO excluiu
    }
}