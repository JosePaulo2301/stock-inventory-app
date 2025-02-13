package io.github.stockinventory.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.stockinventory.app.model.Produto;
import io.github.stockinventory.app.model.records.ProdutoRecordDTO;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>  {
    Optional<Produto> findByName (String name);
//    List<ProdutoRecordDTO> findAll(Produto produto);   
}