package io.github.stockinventory.app.teste.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da classe Service")
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;
    
    @Mock
    private ProdutoRepository repository;
    @Mock
    private MapperConverterClass mapper;

    
    @Test
    void deveSalvarProduto() {
        // Dados simulados
        ProdutoRecordDTO recordDTO = new ProdutoRecordDTO(null, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));
        Produto produto = new Produto(null, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));
        Produto produtoSalvo = new Produto(1L, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));
        ProdutoRecordDTO recordSalvo = new ProdutoRecordDTO(1L, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));

        // Simulando comportamento dos mocks
        Mockito.when(mapper.toProduto(recordDTO)).thenReturn(produtoSalvo);
        Mockito.when(repository.save(Mockito.any(Produto.class))).thenReturn(produtoSalvo);
        Mockito.when(mapper.toProdutoRecordDTO(produtoSalvo)).thenReturn(recordSalvo);
        
        ProdutoRecordDTO produtoRecordDTO =  service.salvar(recordDTO);

        assertEquals("Teclado" , produtoRecordDTO.name());


        Mockito.verify(mapper).toProduto(produtoRecordDTO);
        Mockito.verify(repository).save(Mockito.any(Produto.class));
        Mockito.verify(mapper).toProdutoRecordDTO(produtoSalvo);
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = new Produto(1L, "Teclado", "Mecânico", 10, new BigDecimal("300.00"));

        ProdutoRecordDTO produtoDTO = new ProdutoRecordDTO(1L, "Teclado", "Mecânico", 10, new BigDecimal("300.00"));

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(produto));

        Mockito.when(mapper.toProdutoRecordDTO(produto)).thenReturn(produtoDTO);

        ProdutoRecordDTO encontrado = service.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals("Teclado", encontrado.name());
        assertEquals("Mecânico", encontrado.descricao());
        assertEquals(new BigDecimal("300.00"), encontrado.preco());
    }


    
    @Test
    public void deveListarTodosOsProdutos() {
    	List<Produto> produtos = List.of(new Produto(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));
        List<ProdutoRecordDTO> dtos = List.of(new ProdutoRecordDTO(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));


        Mockito.when(repository.findAll()).thenReturn(produtos);

        Mockito.when(mapper.toProdutoRecordDTO(Mockito.any(Produto.class))).thenReturn(dtos.get(0));

        List<ProdutoRecordDTO> resultado = service.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Monitor", resultado.get(0).name());

    }
    

    @Test
    public void deveDeletarUmProdutoPorIdV2() {
        Long id = 1L;
        Produto produto = new Produto(id, "Mouse", "Mouse sem fio", 5, new BigDecimal("150.00"));

        Mockito.doNothing().when(repository).deleteById(id);

        service.excluir(id);

        Mockito.verify(repository).deleteById(id);
    }

    
}
