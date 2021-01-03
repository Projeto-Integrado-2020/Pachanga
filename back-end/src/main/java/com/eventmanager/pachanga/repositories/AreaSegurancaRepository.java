package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.AreaSeguranca;

@Repository
public interface AreaSegurancaRepository extends JpaRepository<AreaSeguranca, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_area_seguranca');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT a FROM AreaSeguranca a WHERE codArea = :codArea")
	public AreaSeguranca findAreaCodArea(int codArea);

	@Query(value = "SELECT a FROM AreaSeguranca a WHERE codFesta = :codFesta")
	public List<AreaSeguranca> findAllAreasByCodFesta(int codFesta);
	
	@Query(value = "SELECT a FROM AreaSeguranca a WHERE codFesta = :codFesta AND codArea = :codArea")
	public AreaSeguranca findAreaByCodFestaAndCodArea(int codFesta, int codArea);

	@Query(value = "SELECT a FROM AreaSeguranca a WHERE nomeArea = :nomeArea AND codFesta =:codFesta")
	public AreaSeguranca findAreaByNomeArea(String nomeArea, int codFesta);
	
	@Query(value = "SELECT a FROM AreaSeguranca a WHERE codArea = :codArea")
	public AreaSeguranca findAreaByCodFesta(int codArea);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM AreaSeguranca WHERE cod_Festa = :idFesta")
	public void deleteByCodFesta(int idFesta);

}
