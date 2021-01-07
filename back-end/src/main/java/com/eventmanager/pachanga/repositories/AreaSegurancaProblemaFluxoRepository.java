package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.AreaSegurancaProblemaFluxo;

@Repository
public interface AreaSegurancaProblemaFluxoRepository extends JpaRepository<AreaSegurancaProblemaFluxo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_area_seguranca_x_problema_fluxo');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM area_seguranca_x_problema_fluxo WHERE cod_Festa = :idFesta", nativeQuery = true)
	public void deleteByCodFesta(int idFesta);
	
	@Query(value = "SELECT COUNT(asp) FROM AreaSegurancaProblemaFluxo asp WHERE asp.codUsuarioEmissor = :codUsuario AND asp.statusProblema = :statusProblema AND asp.codFesta = :codFesta")
	public int findQuantidadeProblemasEmitidosByUsuario(Integer codUsuario, String statusProblema, int codFesta);
	
	@Query(value = "SELECT COUNT(DISTINCT asp.codAreaProblema) FROM AreaSegurancaProblemaFluxo asp WHERE asp.codArea = :codArea")
	public int findQuantidadeProblemasByCodArea(Integer codArea);
	
	@Query(value = "SELECT DISTINCT asp.codUsuarioEmissor, asp.nomeUsuarioEmissor FROM AreaSegurancaProblemaFluxo asp WHERE asp.codFesta = :codFesta")
	public List<Object[]> findUsuariosByIdFesta(int codFesta);
	
	@Query(value = "SELECT DISTINCT asp.codArea, asp.nomeArea FROM AreaSegurancaProblemaFluxo asp WHERE asp.codFesta = :codFesta")
	public List<Object[]> findAreasByIdFesta(int codFesta);

	@Query(value = "SELECT COUNT(DISTINCT asp.codAreaProblema) from AreaSegurancaProblemaFluxo asp WHERE asp.codUsuarioResolv = :codUsuario AND asp.codFesta = :codFesta")
	public int findQuantidadeChamadasResolvidasByUsuario(Integer codUsuario, int codFesta);
	
	@Query(value = "SELECT COUNT(DISTINCT asp.codAreaProblema) from AreaSegurancaProblemaFluxo asp WHERE asp.codFesta = :codFesta")
	public Float countProblemasFesta(int codFesta);
}
