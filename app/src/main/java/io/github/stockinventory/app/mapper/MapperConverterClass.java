package io.github.stockinventory.app.mapper;

import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.model.records.ProdutoRecordDTO;

public class MapperConverterClass {
    // Convertendo PRoduto para ProdutoRecordDTO
    public ProdutoRecordDTO toProdutoRecordDTO (Produto produto) {
        return new ProdutoRecordDTO(
         produto.getId(),
         produto.getName(), 
         produto.getDescricao(),
         produto.getQuantidade(),
         produto.getPreco()
         );
    }

    // Converter ProdutoRecordDTO para produto
    public Produto toProduto (ProdutoRecordDTO dto) {
        return new Produto(
            dto.id(),
            dto.name(),
            dto.descricao(),
            dto.quantidade(),
            dto.preco()
            );
    }
}
