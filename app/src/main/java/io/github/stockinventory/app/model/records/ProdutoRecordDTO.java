package io.github.stockinventory.app.model.records;

import java.math.BigDecimal;

public record ProdutoRecordDTO (
      Long id,
      String name ,
      String descricao ,
      int quantidade ,
      BigDecimal preco) {}
