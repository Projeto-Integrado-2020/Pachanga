package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	
//selects______________________________________________________________________________
	public Produto findById(int codProduto);
	
	@Query(value = "SELECT NEXTVAL('seq_usuario');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT p FROM Estoque e JOIN e.itemEstoque ie JOIN ie.produto p WHERE e.codEstoque = :codEstoque")
	public List<Produto> findProdutosPorEstoque(@Param("codEstoque") int codEstoque);
	
	@Query(value = "SELECT p FROM Estoque e JOIN e.itemEstoque ie JOIN ie.produto p WHERE e.codEstoque = :codEstoque AND p.codProduto = :codProduto")
	public Produto findProdutoEstoque(@Param("codEstoque") int codEstoque, @Param("codProduto") int codProduto);

	@Query(value = "SELECT p FROM Produto p JOIN p.itemEstoque ie WHERE p.codProduto = :codProduto")
	public List<Produto> findEstoquesComProduto(@Param("codProduto") int codProduto);
	
//delete________________________________________________________________________________________
	@Modifying
	@Query(value = "DELETE FROM produto_x_estoque WHERE cod_produto = :codProduto AND cod_estoque = :codEstoque", nativeQuery = true)
	public void deleteProdutoEstoque(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque);

	@Query(value = "SELECT p FROM Produto p WHERE p.marca = :marca AND p.codFesta = :codFesta")
	public Produto findByMarca(String marca, Integer codFesta);

	@Query(value = "SELECT p FROM Produto p WHERE p.codFesta = :codFesta")
	public List<Produto> findProdutoByCodFesta(Integer codFesta);


}


