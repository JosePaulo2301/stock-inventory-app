package io.github.stockinventory.app.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.model.records.ProdutoRecordDTO;
import io.github.stockinventory.app.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
	private final ProdutoService service;
    
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }
    
    @GetMapping (produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<ProdutoRecordDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping(value = "/{id}",
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ProdutoRecordDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping(
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ProdutoRecordDTO criar(@RequestBody ProdutoRecordDTO produtoRecordDTO) {
        return service.salvar(produtoRecordDTO);
    }

    @PutMapping(value = "/{id}",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ResponseEntity<Produto> updateProduct(@PathVariable Long id, @RequestBody ProdutoRecordDTO dto) {
        if (!id.equals(dto.id())) {
            return ResponseEntity.badRequest().build();
        }
        Produto update = service.updateProductById(dto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
