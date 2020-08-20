package com.eventmanager.pachanga.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.ItemEstoque;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Integer>{

	@Query(value = "SELECT i FROM ItemEstoque i JOIN i.estoque e JOIN i.produto p WHERE e.codEstoque = :codEstoque AND p.codProduto = :codProduto")
	public ItemEstoque findItemEstoque(@Param("codEstoque") int codEstoque, @Param("codProduto") int codProduto);

	@Query(value = "SELECT i FROM ItemEstoque i JOIN i.produto p WHERE p.codProduto = :codProduto")
	public List<ItemEstoque> findItemEstoquePorProduto(@Param("codProduto") int codProduto);
	
	@Query(value = "SELECT i FROM ItemEstoque i JOIN i.produto p JOIN i.estoque e JOIN e.festa f WHERE f.codFesta = :codFesta AND e.codEstoque = :codEstoque")
	public Set<ItemEstoque> findItensEstoqueByFestaAndEstoque(int codFesta, int codEstoque);

}
