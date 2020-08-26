package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.ItemEstoqueFluxo;

public interface ItemEstoqueFluxoRepository extends JpaRepository<ItemEstoqueFluxo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_historico');", nativeQuery = true)
	public int getNextValMySequence();

}
