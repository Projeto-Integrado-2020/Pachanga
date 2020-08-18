package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	
//selects______________________________________________________________________________
	public Produto findById(int codProduto);
	
	@Query(value = "SELECT NEXTVAL('seq_usuario');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT p FROM Estoque e JOIN e.produtos p WHERE e.codEstoque = :codEstoque")
	public List<Produto> findProdutosPorEstoque(@Param("codEstoque") int codEstoque);
	
	@Query(value = "SELECT p FROM Estoque e JOIN e.produtos p WHERE e.codEstoque = :codEstoque AND p.codProduto = :codProduto")
	public Produto findProdutoEstoque(@Param("codEstoque") int codEstoque, @Param("codProduto") int codProduto);

	@Query(value = "SELECT e FROM Estoque e JOIN e.produtos p WHERE p.codProduto = :codProduto")
	public List<Estoque> findEstoquesComProduto(@Param("codProduto") int codProduto);
	
	//@Query(value = "SELECT g FROM usuario_x_grupo u JOIN u.grupo g WHERE u.codUsuario = :codUsuario", nativeQuery = true)
	//public List<Grupo> findGruposUsuario(@Param("codUsuario") int codUsuario);

	
	@Query(value = "SELECT i FROM ItemEstoque i WHERE i.codEstoque = :codEstoque AND codProduto = :codProduto")
	public ItemEstoque findItemEstoque(@Param("codEstoque") int codEstoque, @Param("codProduto") int codProduto);

	@Query(value = "SELECT i FROM ItemEstoque i WHERE i.codProduto = :codProduto")
	public List<ItemEstoque> findItemEstoquePorProduto(@Param("codProduto") int codProduto);
	
	//@Query(value = "SELECT x.quantidade_atual FROM produto_x_estoque x WHERE x.cod_produto = :codProduto", nativeQuery = true)
	//public List<int> findProdutoEstoqueX(@Param("codProduto") int codProduto);
	

//inserts____________________________________________________________________________________	
	@Modifying
	@Query(value = "INSERT INTO produto_x_estoque(cod_produto, cod_estoque, cod_festa, quantidade_max, quantidade_atual, porcentagem_min) VALUES(:codProduto ,:codEstoque, :codFesta, :quantidadeMax, :quantidadeAtual, :porcentagemMin)", nativeQuery = true)
	public void saveProdutoEstoque(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque, @Param("codFesta") int codFesta, @Param("quantidadeMax") int quantidadeMax, @Param("quantidadeAtual") int quantidadeAtual, @Param("porcentagemMin") int porcentagemMin);
	
//updates_____________________________________________________________________________________	
	@Modifying
	@Query(value = "UPDATE produto_x_estoque x SET x.quantidade_max = :quantidadeMax, x.quantidade_atual = :quantidadeAtual, x.porcentagem_min = :porcentagemMin WHERE x.cod_produto = :codProduto AND x.cod_estoque = :codEstoque", nativeQuery = true)
	public void updateProdutoEstoque(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque, @Param("quantidadeMax") int quantidadeMax, @Param("quantidadeAtual") int quantidadeAtual, @Param("porcentagemMin") int porcentagemMin);
	
	@Modifying
	@Query(value = "UPDATE produto_x_estoque x SET x.quantidade_atual = :quantidadeAtual WHERE x.cod_produto = :codProduto AND x.cod_estoque = :codEstoque", nativeQuery = true)
	public void updateProdutoEstoqueQuantidade(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque, @Param("quantidadeAtual") int quantidadeAtual);
	
//delete________________________________________________________________________________________
	@Modifying
	@Query(value = "DELETE FROM produto_x_estoque WHERE cod_produto = :codProduto AND cod_estoque = :codEstoque", nativeQuery = true)
	public void deleteProdutoEstoque(@Param("codProduto") int codProduto, @Param("codEstoque") int codEstoque);


}


