package io.github.stockinventory.app.teste.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.github.stockinventory.app.model.records.ProdutoRecordDTO;
import io.github.stockinventory.app.services.ProdutoService;


@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ProdutoService service;
		
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
        service.salvar(new ProdutoRecordDTO(      null,
                                         "Monitor",
                                         "Monitor 24 polegadas",
                                         2,
                                         new BigDecimal("600.00")));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    
    }

@Test
void deveAtualizarUmProduto() throws Exception {
    String json = """
            {
            "id": 1,
            "name": "Cadeira Gamer",
            "descricao": "Cadeira ergonômica",
            "quantidade": 2,
            "preco": 899.99
             }
            """;

    mockMvc.perform(put("/produtos/{id}", 1) // Passa o ID aqui
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
           .andExpect(status().isOk()) 
           .andExpect(jsonPath("$.id").value("1")); // Espera o valor correto
}



}
