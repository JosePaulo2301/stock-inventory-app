package io.github.stockinventory.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import io.github.stockinventory.app.exceptions.ResourceNotFoundException;
import io.github.stockinventory.app.mapper.MapperConverterClass;
import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.model.records.ProdutoRecordDTO;
import io.github.stockinventory.app.repository.ProdutoRepository; 

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final MapperConverterClass mapper;

    public ProdutoService(ProdutoRepository repository, MapperConverterClass converterClass) {
        this.repository = repository;
        this.mapper = converterClass;
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public List<ProdutoRecordDTO> listarTodos() {
        List<Produto> protudos = repository.findAll();
        return protudos.stream().map(mapper::toProdutoRecordDTO).collect(Collectors.toList());
    }

    public ProdutoRecordDTO buscarPorId(Long id) {
        return repository
        .findById(id)
        .map(mapper::toProdutoRecordDTO)
        .orElseThrow(() -> new ResourceNotFoundException("id não encontrado" + id));
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

    public void excluir(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id do produto não pode ser encontrado");
        }
        repository.deleteById(id);
    }
    
}
