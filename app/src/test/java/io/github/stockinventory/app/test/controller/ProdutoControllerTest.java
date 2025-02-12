package io.github.stockinventory.app.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.github.stockinventory.app.model.ProdutoRecord;
import io.github.stockinventory.app.services.ProdutoService;


@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {
	
	private MockMvc mockMvc;
	private ProdutoService service;
	
	public ProdutoControllerTest(MockMvc mockMvc, ProdutoService service) {
		this.mockMvc = mockMvc;
		this.service = service;
	}
	
    @Test
    void deveCriarProduto() throws Exception {
        String json = """
                {
                    "nome": "Cadeira Gamer",
                    "descricao": "Cadeira ergonômica",
                    "quantidade": 2,
                    "preco": 899.99
                }
                """;

        mockMvc.perform(post("/produtos")
        				.contentType(MediaType.APPLICATION_JSON)
        				.content(json))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.nome").value("Cadeira Gamer"));
        		
    }

    void deveListarProdutos() throws Exception {
        service.salvar(new ProdutoRecord(null,
                                         "Monitor",
                                         "Monitor 24 polegadas",
                                         2,
                                         new BigDecimal("600.00")));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    
    }
    

}
