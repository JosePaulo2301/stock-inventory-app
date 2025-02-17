package io.github.stockinventory.app.services;

import java.util.List;
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

    public ProdutoRecordDTO salvar(ProdutoRecordDTO produtoRecordDTO) {
        Produto produto = mapper.toProduto(produtoRecordDTO);
        Produto salvarProduto = repository.save(produto);
        return mapper.toProdutoRecordDTO(salvarProduto);
    }

    public List<ProdutoRecordDTO> listarTodos() {
        List<Produto> protudos = repository.findAll();
        return protudos.stream().map(mapper::toProdutoRecordDTO).collect(Collectors.toList());
    }

    public ProdutoRecordDTO buscarPorId(Long id) {
        Produto produto = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id não encontrado" + id));
        return mapper.toProdutoRecordDTO(produto);
    }

    public ProdutoRecordDTO updateProductById(ProdutoRecordDTO dto) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Id do produção não pode ser encontrado");
        }
        Produto entity = repository.findById(dto.id()).orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + dto.id() + "Não encontrado"));
        
        if (dto.descricao() != null && !dto.descricao().equals(entity.getDescricao())) {
            entity.setDescricao(dto.descricao());
        }
        if (dto.name() != null && !dto.name().equals(entity.getName())) {
            entity.setName(dto.name());
        }
        if (dto.preco() != null && !dto.preco().equals(entity.getPreco())) {
            entity.setPreco(dto.preco());
        }
        Produto updatedEntity = repository.save(entity);
        return mapper.toProdutoRecordDTO(updatedEntity);
    }

    public void excluir(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id do produto não pode ser encontrado");
        }
        repository.deleteById(id);
    }
    
}
