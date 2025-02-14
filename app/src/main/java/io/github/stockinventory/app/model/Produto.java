package io.github.stockinventory.app.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String descricao;
    private int quantidade;
    private BigDecimal preco;

    public Produto () {

    }
    public Produto(Long id, String name, String descricao, int quantidade, BigDecimal preco) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }
    public Produto(String name, String descricao, int quantidade, BigDecimal preco) {
        this.name = name;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescricao() {
        return descricao;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    
}
