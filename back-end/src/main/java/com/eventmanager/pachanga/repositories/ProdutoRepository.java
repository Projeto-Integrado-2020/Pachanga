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
	
	public Produto findById(int codProduto);
	
	@Query(value = "SELECT NEXTVAL('seq_produto');", nativeQuery = true)
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

	@Modifying
	@Query(value = "INSERT INTO produto_x_estoque(cod_produto, cod_estoque, cod_festa, quantidade_max, quantiadde_atual, porcentagem_min) VALUES(:codProduto ,:codEstoque, :codFesta, :quantidadeMax, :quantiaddeAtual, :porcentagemMin)", nativeQuery = true)
	public void saveProdutoEstoque(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque, @Param("codFesta") int codFesta, @Param("quantidadeMax") int quantidadeMax, @Param("quantiaddeAtual") int quantiaddeAtual, @Param("porcentagemMin") int porcentagemMin);

	@Modifying
	@Query(value = "UPDATE produto_x_estoque x SET x.quantidade_max = :quantidadeMax, x.quantidade_atual = :quantidadeAtual, x.porcentagem_min = :porcentagemMin WHERE x.cod_produto = :codProduto AND x.cod_estoque = :codEstoque", nativeQuery = true)
	public void updateProdutoEstoque(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque, @Param("quantidadeMax") int quantidadeMax, @Param("quantidadeAtual") int quantidadeAtual, @Param("porcentagemMin") int porcentagemMin);
	
	@Modifying
	@Query(value = "UPDATE produto_x_estoque x SET x.quantidade_atual = :quantidadeAtual WHERE x.cod_produto = :codProduto AND x.cod_estoque = :codEstoque", nativeQuery = true)
	public void updateProdutoEstoqueQuantidade(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque, @Param("quantidadeAtual") int quantidadeAtual);
	
	@Modifying
	@Query(value = "DELETE FROM produto WHERE cod_festa = :codFesta", nativeQuery = true)
	public void deleteProdFesta(int codFesta);
}


