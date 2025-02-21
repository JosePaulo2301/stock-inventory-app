package io.github.stockinventory.app.teste.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.stockinventory.app.mapper.MapperConverterClass;
import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.model.records.ProdutoRecordDTO;
import io.github.stockinventory.app.repository.ProdutoRepository;
import io.github.stockinventory.app.services.ProdutoService;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    
    @Mock
    private ProdutoRepository repository;
    @Mock
    private MapperConverterClass converterClass;
    @InjectMocks
    private ProdutoService service;


    
    @Test
    void deveSalvarProduto() {
        ProdutoRecordDTO dto = new ProdutoRecordDTO(1L, "Teclado", "Mecânico", 10, new BigDecimal("300.00"));
        Produto produtoExistente = new Produto(1L, "Teclado", "Gamer", 10, new BigDecimal("200.00"));
        Produto produtoAtualizado = new Produto(1L, "Teclado", "Mecânico", 10, new BigDecimal("300.00"));
        ProdutoRecordDTO dtoAtualizado = new ProdutoRecordDTO(1L, "Teclado", "Mecânico", 10, new BigDecimal("300.00"));

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        Mockito.when(repository.save(produtoExistente)).thenReturn(produtoAtualizado);
        Mockito.when(converterClass.toProdutoRecordDTO(produtoAtualizado)).thenReturn(dtoAtualizado);

        ProdutoRecordDTO resultado = service.updateProductById(dto);

        assertNotNull(resultado);
        assertEquals("Teclado", resultado.name());
        assertEquals("Mecânico", resultado.descricao());
        assertEquals(new BigDecimal("300.00"), resultado.preco());    		
    }
    
    @Test
    void deveBuscarProdutoPorId() {

        Produto produto = new Produto(1L, "Teclado", "Mecânico", 10, new BigDecimal("300.00"));
        ProdutoRecordDTO produtoDTO = new ProdutoRecordDTO(1L, "Teclado", "Mecânico", 10, new BigDecimal("300.00"));
    	
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(produto));
        Mockito.when(converterClass.toProdutoRecordDTO(produto)).thenReturn(produtoDTO);
    	
        ProdutoRecordDTO encontrado = service.buscarPorId(1L);
    

        assertNotNull(encontrado);
        assertEquals("Teclado", encontrado.name());
        assertEquals("Mecânico", encontrado.descricao());
        assertEquals(new BigDecimal("300.00"), encontrado.preco());
    }
    
    @Test
    public void deveListarTodosOsProdutos() {
    	List<Produto> produtos = List.of(new Produto(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));

        List<ProdutoRecordDTO> produtosDTO = List.of(new ProdutoRecordDTO(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));


        Mockito.when(repository.findAll()).thenReturn(produtos);

        Mockito.when(converterClass.toProdutoRecordDTO(Mockito.any(Produto.class))).thenReturn(produtosDTO.get(0));

        List<ProdutoRecordDTO> resultado = service.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Monitor", resultado.get(0).name());



    }
    
    @Test
    public void deveDeletarUmProdutoPorIdV2() {
        // Arrange - Criando um produto fictício
        Long id = 1L;
        Produto produto = new Produto(id, "Mouse", "Mouse sem fio", 5, new BigDecimal("150.00"));

        // Definir o comportamento do mock do repositório
        Mockito.doNothing().when(repository).deleteById(id);

        // Act - Chamar o método da service
        service.excluir(id);

        // Assert - Verificar se o método foi chamado corretamente
        Mockito.verify(repository).deleteById(id);
    }

    
}
