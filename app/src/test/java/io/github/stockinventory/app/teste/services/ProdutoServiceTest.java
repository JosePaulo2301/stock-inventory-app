package io.github.stockinventory.app.teste.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.stockinventory.app.mapper.MapperConverterClass;
import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.model.records.ProdutoRecordDTO;
import io.github.stockinventory.app.repository.ProdutoRepository;
import io.github.stockinventory.app.services.ProdutoService;

@TestInstance(Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da classe Service")
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;
    
    @Mock
    private ProdutoRepository repository;

    @Mock
    private MapperConverterClass mapper;

    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new ProdutoService(repository, mapper);
    }

    
    
    @Test
    void deveSalvarProduto() {
        // Dados simulados
        ProdutoRecordDTO recordDTO = new ProdutoRecordDTO(null, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));
        Produto produto = new Produto(null, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));


        Produto produtoSalvo = new Produto(1L, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));
        ProdutoRecordDTO recordSalvo = new ProdutoRecordDTO(1L, "Teclado", "Teclado Mecânico", 10, new BigDecimal("200.00"));

        Mockito.when(repository.save(produto)).thenReturn(produtoSalvo);

        // Simulando comportamento dos mocks

        Mockito.when(mapper.toProduto(recordDTO)).thenReturn(produto);
        Mockito.when(repository.save(Mockito.any(Produto.class))).thenReturn(produtoSalvo);
        Mockito.when(mapper.toProdutoRecordDTO(produtoSalvo)).thenReturn(recordSalvo);
        
        // Chamando o serviço para testar
        ProdutoRecordDTO resultado = service.salvar(recordDTO);

        // chacagens
        assertEquals(recordSalvo, resultado);


        // Verificando se os mocks foram chamados corretamente

        Mockito.verify(mapper).toProduto(recordDTO);
        Mockito.when(mapper.toProduto(recordDTO)).thenReturn(produtoSalvo);
        Mockito.when(repository.save(Mockito.any(Produto.class))).thenReturn(produtoSalvo);
        Mockito.when(mapper.toProdutoRecordDTO(produtoSalvo)).thenReturn(recordSalvo);
        
        ProdutoRecordDTO produtoRecordDTO =  service.salvar(recordDTO);

        assertEquals("Teclado" , produtoRecordDTO.name());

        Produto resultado = repository.save(produto);
        BooleanSupplier  booleanSupplier = () -> true;

        assertNotNull(resultado);
    	assertEquals(recordSalvo.id(), resultado.getId());	
        assertEquals(1L, resultado.getId());
        assertTrue(booleanSupplier.getAsBoolean(), "This is may supplerClass");


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


        // Criando um produto com a entidade Produto e também ProdutoRecordDTO
        List<Produto> produtos = List.of(new Produto(1L, "Monitor", "Monitor sem fio", 4, new BigDecimal("241.1")));
        List<ProdutoRecordDTO> produtoDTO = List.of(new ProdutoRecordDTO(1L, "Monitor", "Monitor sem fio", 4, new BigDecimal("241.1")));

        // Mockando a busca dos produtos
        Mockito.when(repository.findAll()).thenReturn(produtos);

        // Mockando a conversão da entidade para DTO, garantindo conversão correta para cada produto
        Mockito.when(mapper.toProdutoRecordDTO(Mockito.any(Produto.class)))
                .thenAnswer(invocation -> {
                    Produto produto = invocation.getArgument(0);
                    return new ProdutoRecordDTO(produto.getId(),
                                produto.getName(),
                                produto.getDescricao(),
                                produto.getQuantidade(),
                                produto.getPreco());
                });

        // Chamando o service
        List<ProdutoRecordDTO> resultado = service.listarTodos();

        // Verificações
        //assertFalse(resultado.isEmpty(), "A lista de produtos não deveria estar vazia");
        assertEquals(1, resultado.size(), "A lista deveria conter exatamente 1 produto");
        assertEquals("Monitor", resultado.get(0).name());

        // Verificando interações com os mocks
        Mockito.verify(repository).findAll();
        Mockito.verify(mapper, Mockito.times(produtos.size())).toProdutoRecordDTO(Mockito.any(Produto.class));

    	List<Produto> produtos = List.of(new Produto(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));
    	List<ProdutoRecordDTO> produtoDTO = List.of(new ProdutoRecordDTO(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));
    	
    	Mockito.when(repository.findAll()).thenReturn(produtos);

    	Mockito.when(mapper.toProdutoRecordDTO(Mockito.any(Produto.class))).thenReturn(produtoDTO.get(0));
    	
    	// Chama o método
    	List<ProdutoRecordDTO> resultado = service.listarTodos();
    	
    	
    	assertFalse(resultado.isEmpty(), "Objeto não pode retornar vazio");
    	assertEquals(1, resultado.size());
        List<ProdutoRecordDTO> dtos = List.of(new ProdutoRecordDTO(1L, "Monitor", "Monitor sem fio", 4 , new BigDecimal("241.1")));


        Mockito.when(repository.findAll()).thenReturn(produtos);

        Mockito.when(mapper.toProdutoRecordDTO(Mockito.any(Produto.class))).thenReturn(dtos.get(0));

        List<ProdutoRecordDTO> resultado = service.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Monitor", resultado.get(0).name());

    }


    @Test
    public void deveDeletarUmProdutoPorId() {
        Long id = 1L;
        ProdutoRecordDTO produto = new ProdutoRecordDTO(id, "Mouse", "Mouse sem fio", 5, new BigDecimal("150.00"));

        // Simula que o produto existe no banco
        Mockito.when(repository.existsById(id)).thenReturn(true);

        // Adicione um assert para garantir que o mock realmente retorna true
        assertTrue(repository.existsById(id), "existsById deveria retornar true, mas está retornando false");

        // Simula a exclusão sem erro
        Mockito.doNothing().when(repository).deleteById(id);

        // Executa a exclusão
        service.excluir(id);

        // Verifica se o método deleteById foi chamado
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

}
