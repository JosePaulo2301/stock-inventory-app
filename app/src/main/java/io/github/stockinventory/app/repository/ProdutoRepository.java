package io.github.stockinventory.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.stockinventory.app.model.ProdutoRecord;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoRecord, Long>  {
    Optional<ProdutoRecord> findByNameOptional (String nome);  
}