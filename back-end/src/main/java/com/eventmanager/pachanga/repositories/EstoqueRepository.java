package com.eventmanager.pachanga.repositories;

import java.util.List;

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

}
