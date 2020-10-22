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
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO area_seguranca VALUES (:#{#areaSeguranca.codArea}, :#{#areaSeguranca.codFesta}, :#{#areaSeguranca.nomeArea}, CAST(:#{#areaSeguranca.statusSeguranca} AS status_seguranca_t))", nativeQuery = true)
	public void salvarArea(AreaSeguranca areaSeguranca);

	@Query(value = "SELECT a FROM AreaSeguranca a WHERE codFesta = :codFesta")
	public List<AreaSeguranca> findAllAreasByCodFesta(int codFesta);
	
	@Query(value = "SELECT a FROM AreaSeguranca a WHERE codFesta = :codFesta AND codArea = :codArea")
	public AreaSeguranca findAreaByCodFestaAndCodArea(int codFesta, int codArea);

	@Query(value = "SELECT a FROM AreaSeguranca a WHERE nomeArea = :nomeArea AND codFesta =:codFesta")
	public AreaSeguranca findAreaByNomeArea(String nomeArea, int codFesta);
	
	@Query(value = "SELECT a FROM AreaSeguranca a WHERE codArea = :codArea")
	public AreaSeguranca findAreaByCodFesta(int codArea);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE area_seguranca SET nome_area =:#{#areaSeguranca.nomeArea}, cod_area = :#{#areaSeguranca.codArea}, cod_festa = :#{#areaSeguranca.codFesta} WHERE cod_area = :#{#areaSeguranca.codArea}", nativeQuery = true)
	public void updateArea(AreaSeguranca areaSeguranca);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE area_seguranca SET nome_area =:nomeArea WHERE cod_area = :codArea", nativeQuery = true)
	public void updateNomeArea(int codArea, String nomeArea);

}
