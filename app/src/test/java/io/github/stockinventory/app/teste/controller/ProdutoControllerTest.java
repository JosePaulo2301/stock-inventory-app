package io.github.stockinventory.app.teste.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import io.github.stockinventory.app.model.records.ProdutoRecordDTO;
import io.github.stockinventory.app.repository.ProdutoRepository;
import io.github.stockinventory.app.services.ProdutoService;


@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ProdutoService service;
	
	@Autowired
	private ProdutoRepository repository;
	
	
	
	@BeforeEach
	void setUp() {
		repository.deleteAll();
	}
	
    @Test
    void deveCriarProduto() throws Exception {
        String json = """
                {
                    "name": "Cadeira Gamer",
                    "descricao": "Cadeira ergonômica", 
                    "quantidade": 2,
                    "preco": 899.99
                }
                """;

        mockMvc.perform(post("/produtos")
        				.contentType(MediaType.APPLICATION_JSON)
        				.content(json))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.name").value("Cadeira Gamer"));
        		
    }

    @Test
    void deveListarProdutos() throws Exception {
        service.salvar(new ProdutoRecordDTO(null, "Monitor", "Monitor 24 polegadas", 2, new BigDecimal("600.00")));

        mockMvc.perform(get("/produtos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1));
    }

	@Test
	void deveAtualizarUmProduto() throws Exception {
	    service.salvar(new ProdutoRecordDTO(null, "Cadeira", "Cadeira simples", 1, new BigDecimal("500.00")));
		
	    String json = """
	            {
		            "id": 1,
		            "nome": "Cadeira Gamer",
		            "descricao": "Cadeira ergonômica",
		            "quantidade": 2,
		            "preco": 899.99
	             }
	            """;
	
	    mockMvc.perform(put("/produtos/{id}", 1)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(json))
	           .andExpect(status().isOk()) 
	           .andExpect(jsonPath("$.id").value("1"));
	}
	
	
	@Test
	void deveRetornarNotFoundAoExcluirProdutoInexistente() throws Exception {
	    MvcResult result = mockMvc.perform(delete("/produtos/{id}", 99))
	                              .andExpect(status().isNotFound())
	                              .andReturn(); // Captura o resultado da requisição

	    String responseBody = result.getResponse().getContentAsString();
	    System.out.println("Corpo da resposta: " + responseBody);
	}


	
}
