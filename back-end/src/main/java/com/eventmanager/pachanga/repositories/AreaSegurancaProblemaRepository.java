package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.AreaSegurancaProblema;

@Repository
public interface AreaSegurancaProblemaRepository extends JpaRepository<AreaSegurancaProblema, Integer> {

	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.area a JOIN asp.problema p WHERE a.codArea= :codArea")
	List<AreaSegurancaProblema> findProblemasArea(int codArea);

	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.problema p JOIN asp.area a WHERE a.codArea = :codArea AND p.codProblema = :codProblema")
	public AreaSegurancaProblema findAreaSegurancaProblema(@Param("codArea") int codArea, @Param("codProblema") int codProblema);

	
	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.problema p JOIN asp.area a JOIN asp.festa f WHERE f.codFesta = :codFesta")
	public List<AreaSegurancaProblema> findAllAreaSegurancaProblemaFesta(@Param("codFesta") int codFesta);

	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.problema p JOIN asp.area a JOIN asp.festa f WHERE a.codArea = :codArea AND f.codFesta = :codFesta")
	public List<AreaSegurancaProblema> findAllAreaSegurancaProblemaArea(@Param("codArea") int codArea, @Param("codFesta") int codFesta);

	@Query(value = "SELECT NEXTVAL('seq_area_x_problema');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.problema p JOIN asp.area a JOIN asp.festa f WHERE asp.codAreaProblema = :codProblemaSeguranca")
	public AreaSegurancaProblema findByCodProblema(int codProblemaSeguranca);
	
	@Query(value = "SELECT COUNT(*) from AreaSegurancaProblema asp JOIN asp.area a WHERE a.codArea = :codArea")
	public int findQuantidadeProblemasByCodArea(int codArea);
	
	@Query(value = "SELECT COUNT(*) from AreaSegurancaProblema asp JOIN asp.codUsuarioEmissor u JOIN asp.festa f WHERE u.codUsuario = :codUsuario AND asp.statusProblema = :statusProblema AND f.codFesta = :codFesta")
	public int findQuantidadeProblemasEmitidosByUsuario(int codUsuario, String statusProblema, int codFesta);

	@Query(value = "SELECT COUNT(*) from AreaSegurancaProblema asp JOIN asp.festa f WHERE f.codFesta = :codFesta")
	public int countProblemasFesta(int codFesta);

	@Query(value = "SELECT COUNT(*) from AreaSegurancaProblema asp JOIN asp.codUsuarioResolv u WHERE u.codUsuario = :codUsuario")
	int findQuantidadeChamadasResolvidasByUsuario(int codUsuario);
	
}
