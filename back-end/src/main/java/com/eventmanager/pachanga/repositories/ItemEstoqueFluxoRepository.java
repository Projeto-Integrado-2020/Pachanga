package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.ItemEstoqueFluxo;

public interface ItemEstoqueFluxoRepository extends JpaRepository<ItemEstoqueFluxo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_historico');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM ItemEstoqueFluxo i WHERE i.codFesta = :idFesta")
	public void deleteByCodFesta(int idFesta);

}
