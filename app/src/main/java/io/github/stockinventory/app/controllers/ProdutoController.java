package io.github.stockinventory.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.stockinventory.app.model.ProdutoRecord;
import io.github.stockinventory.app.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
	private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }
    

    @PostMapping
    public ProdutoRecord criar(@RequestBody ProdutoRecord produto) {
        return service.salvar(produto);
    }

    @GetMapping
    public List<ProdutoRecord> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRecord> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
