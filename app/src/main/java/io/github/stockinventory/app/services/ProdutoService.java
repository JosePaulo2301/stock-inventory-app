package io.github.stockinventory.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.stockinventory.app.model.ProdutoRecord;
import io.github.stockinventory.app.repository.ProdutoRepository; 

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoRecord salvar(ProdutoRecord produto) {
        return repository.save(produto);
    }

    public List<ProdutoRecord> listarTodos() {
        return repository.findAll();
    }

    public Optional<ProdutoRecord> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
    
}
