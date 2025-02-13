package io.github.stockinventory.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.stockinventory.app.exceptions.ResourceNotFoundException;
import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.model.records.ProdutoRecordDTO;
import io.github.stockinventory.app.repository.ProdutoRepository; 

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id do produto não pode ser encontrado");
        }
        repository.deleteById(id);
    }
    public Produto updateProductById(ProdutoRecordDTO dto) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Id do produto não pode ser encontrado");
        }


        Produto entity  = repository.findById(dto.id())
                                    .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + dto.id() + " não encontrado"));


        boolean isUpdate = false;
        
        if (dto.descricao() != null && !dto.descricao().equals(entity.getDescricao())) {
            entity.setDescricao(dto.descricao());
            isUpdate = true;
        }
        if (dto.name() != null && !dto.name().equals(entity.getName())) {
            entity.setDescricao(dto.descricao());
            isUpdate = true;
        }
        if (dto.preco() != null && !dto.preco().equals(entity.getPreco())) {
            entity.setPreco(dto.preco());
        }

        if (isUpdate) {
            return repository.save(entity);
        }

        return entity;
    }
    
}
