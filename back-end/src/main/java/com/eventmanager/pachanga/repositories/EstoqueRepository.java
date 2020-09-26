package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Estoque;

@Repository
public interface EstoqueRepository extends CrudRepository<Estoque, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_estoque');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT e FROM Estoque e JOIN e.festa f WHERE f.codFesta = :codFesta")
	public List<Estoque> findEstoqueByCodFesta(int codFesta);
	
	@Query(value = "SELECT e FROM Estoque e WHERE e.codEstoque = :codEstoque")
	public Estoque findByEstoqueCodEstoque(int codEstoque);

	@Query(value = "SELECT e FROM Estoque e WHERE e.nomeEstoque = :nomeEstoque")
	public Estoque findByNomeEstoque(String nomeEstoque);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM estoque WHERE cod_festa = :codFesta", nativeQuery = true)
	public void deleteEstoque(int codFesta);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM produto_x_estoque WHERE cod_festa = :codFesta AND cod_estoque = :codEstoque", nativeQuery = true)
	public void deleteProdEstoque(int codFesta, int codEstoque);
	
	@Query(value = "SELECT e FROM Estoque e JOIN e.festa f WHERE e.nomeEstoque = :nomeEstoque AND f.codFesta = :codFesta")
	public Estoque findByNomeEstoque(String nomeEstoque, Integer codFesta);
}
