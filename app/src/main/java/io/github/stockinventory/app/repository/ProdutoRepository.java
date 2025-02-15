package io.github.stockinventory.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.stockinventory.app.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>  {
    Optional<Produto> findByName (String name);
}