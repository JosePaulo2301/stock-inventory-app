package io.github.stockinventory.app.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record ProdutoRecord(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
    String name,
    String descricao,
    int quantidade,
    BigDecimal preco
) {}
