package io.github.stockinventory.app.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.github.stockinventory.app.services.ProdutoService;


@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	private ProdutoService sevice;
	
	public ProdutoControllerTest(MockMvc mockMvc, ProdutoService sevice) {
		this.mockMvc = mockMvc;
		this.sevice = sevice;
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
    

}
