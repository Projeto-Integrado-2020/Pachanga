package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.AreaSegurancaProblemaFluxo;

public interface AreaSegurancaProblemaFluxoRepository extends JpaRepository<AreaSegurancaProblemaFluxo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_area_seguranca_x_problema_fluxo');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM area_seguranca_x_problema_fluxo WHERE cod_Festa = :idFesta", nativeQuery = true)
	public void deleteByCodFesta(int idFesta);

}
