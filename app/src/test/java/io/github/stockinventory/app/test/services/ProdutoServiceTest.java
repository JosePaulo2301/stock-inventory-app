package io.github.stockinventory.app.test.services;

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

import io.github.stockinventory.app.model.ProdutoRecord;
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
    	ProdutoRecord produto = new ProdutoRecord(null, "Teclado", "Teclado Mecânico",10, new BigDecimal("200.00"));
    	Mockito.when(repository.save(produto)).thenReturn(produto);
    	
    	ProdutoRecord salvo = service.salvar(produto);
    	assertNotNull(salvo);
    	assertEquals("Teclado", salvo.getName());
    		
    }
    
    @Test
    void deveBuscarProdutoPorId() {
    	ProdutoRecord produto = new ProdutoRecord(1L, "Mouse", "Mouse sem fio", 5, new BigDecimal("150.00"));
    	Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(produto));
    	
    	Optional<ProdutoRecord> encontrado = service.buscarPorId(1L);
    	assertTrue(encontrado.isPresent());
    	assertEquals("Mouse", encontrado.get().getName());
    }
    
    @Test
    public void deveListarTodosOsProdutos() {
    	List<ProdutoRecord> produtos = List.of(new ProdutoRecord(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));
    	Mockito.when(repository.findAll()).thenReturn(produtos);
    	
    	List<ProdutoRecord> resultado = service.listarTodos();
    	assertFalse(resultado.isEmpty());
    	assertEquals(1, resultado.size());
    }
    
    
    
}
