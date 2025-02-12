package io.github.stockinventory.app.teste.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.repository.ProdutoRepository;
import io.github.stockinventory.app.services.ProdutoService;

public class ProdutoServiceTest {
    private ProdutoRepository repository;
    private ProdutoService service;

    @BeforeEach
    void setUp() {
    	repository = Mockito.mock(ProdutoRepository.class);
    	service = new ProdutoService(repository);
    }
    
    @Test
    void deveSalvarProduto() {
    	Produto produto = new Produto(null, "Teclado", "Teclado Mecânico",10, new BigDecimal("200.00"));
    	Mockito.when(repository.save(produto)).thenReturn(produto);
    	
    	Produto salvo = service.salvar(produto);
    	assertNotNull(salvo);
    	assertEquals("Teclado", salvo.getName());
    		
    }
    
    @Test
    void deveBuscarProdutoPorId() {
    	Produto produto = new Produto(1L, "Mouse", "Mouse sem fio", 5, new BigDecimal("150.00"));
    	Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(produto));
    	
    	Optional<Produto> encontrado = service.buscarPorId(1L);
    	assertTrue(encontrado.isPresent());
    	assertEquals("Mouse", encontrado.get().getName());
    }
    
    @Test
    public void deveListarTodosOsProdutos() {
    	List<Produto> produtos = List.of(new Produto(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));
    	Mockito.when(repository.findAll()).thenReturn(produtos);
    	
    	List<Produto> resultado = service.listarTodos();
    	assertFalse(resultado.isEmpty());
    	assertEquals(1, resultado.size());
    }
    
    
    
}
