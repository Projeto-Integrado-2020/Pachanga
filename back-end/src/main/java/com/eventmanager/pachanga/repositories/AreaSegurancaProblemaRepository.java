package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.AreaSegurancaProblema;

@Repository
public interface AreaSegurancaProblemaRepository extends JpaRepository<AreaSegurancaProblema, Integer> {

	@Query(value = "SELECT a FROM AreaSegurancaProblema asp JOIN asp.area a JOIN asp.problema p WHERE a.codArea= :codArea")
	List<AreaSegurancaProblema> findProblemasArea(int codArea);

	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.problema p JOIN asp.area a WHERE a.codArea = :codArea AND p.codProblema = :codProblema")
	public AreaSegurancaProblema findAreaSegurancaProblema(@Param("codArea") int codArea, @Param("codProblema") int codProblema);

	
	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.problema p JOIN asp.area a JOIN asp.festa f WHERE f.codFesta = :codFesta")
	public List<AreaSegurancaProblema> findAllAreaSegurancaProblemaFesta(@Param("codFesta") int codFesta);

	@Query(value = "SELECT asp FROM AreaSegurancaProblema asp JOIN asp.problema p JOIN asp.area a JOIN asp.festa f WHERE a.codArea = :codAreaProblema AND f.codFesta = :codFesta")
	public List<AreaSegurancaProblema> findAllAreaSegurancaProblemaArea(@Param("codArea") int codArea, @Param("codFesta") int codFesta);
	
	//@Modifying(clearAutomatically = true)
	//@Query(value = "INSERT INTO area_seguranca VALUES          (:#{#areaSeguranca.codArea}, :#{#areaSeguranca.codFesta}, :#{#areaSeguranca.nomeArea}, CAST(:#{#areaSeguranca.statusSeguranca} AS status_seguranca_t))", nativeQuery = true)
	
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO area_seguranca_x_problema VALUES (:#{#areaSegurancaProblema.area}, :#{#areaSegurancaProblema.area.codFesta}, :#{#areaSegurancaProblema.problema}, :#{#areaSegurancaProblema.codUsuarioResolv}, CAST(:#{#areaSegurancaProblema.statusProblema} AS status_problema_t), :#{#areaSegurancaProblema.horarioInicio}, :#{#areaSegurancaProblema.horarioFim}, :#{#areaSegurancaProblema.codUsuarioEmissor}, :#{#areaSegurancaProblema.descProblema})", nativeQuery = true)
	public void salvarAreaSegurancaProblema(AreaSegurancaProblema areaSegurancaProblema);
	
	//@Modifying(clearAutomatically = true)
	//@Query(value = "UPDATE area_seguranca_x_problema SET cod_festa = :#{#areaSegurancaProblema.festa}, cod_usuario_resolv = :#{#areaSegurancaProblema.codUsuarioResolv}, status_problema = CAST( :#{#areaSegurancaProblema.statusProblema} AS status_problema_t), horario_inicio = :#{#areaSegurancaProblema.horarioInicio}, horario_fim = :#{#areaSegurancaProblema.horarioFim}, cod_usuario_emissor = :#{#areaSegurancaProblema.codUsuarioEmissor}, descricao_prob = :#{#areaSegurancaProblema.descProblema} WHERE cod_problema = :#{#areaSegurancaProblema.problema} AND cod_area_problema = :#{#areaSegurancaProblema.area}", nativeQuery = true)
	//public void atualizarAreaSegurancaProblema(AreaSegurancaProblema areaSegurancaProblema);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE area_seguranca_x_problema SET :#{#areaSegurancaProblema.area}, :#{#areaSegurancaProblema.area.codFesta}, :#{#areaSegurancaProblema.problema}, :#{#areaSegurancaProblema.codUsuarioResolv}, CAST(:#{#areaSegurancaProblema.statusProblema} AS status_problema_t), :#{#areaSegurancaProblema.horarioInicio}, :#{#areaSegurancaProblema.horarioFim}, :#{#areaSegurancaProblema.codUsuarioEmissor}, :#{#areaSegurancaProblema.descProblema} WHERE cod_problema = :#{#areaSegurancaProblema.problema} AND cod_area_problema = :#{#areaSegurancaProblema.area}", nativeQuery = true)
	public void  atualizarAreaSegurancaProblema(AreaSegurancaProblema areaSegurancaProblema);

	//@Modifying(clearAutomatically = true)
	//@Query(value = "UPDATE area_seguranca_x_problema SET cod_usuario_resolv = :#{#areaSegurancaProblema.codUsuarioResolv}, horario_inicio = :#{#areaSegurancaProblema.horarioInicio}, horario_fim = :#{#areaSegurancaProblema.horarioFim}, cod_usuario_emissor = :#{#areaSegurancaProblema.codUsuarioEmissor}, descricao_prob = :#{#areaSegurancaProblema.descProblema} WHERE cod_problema = :#{#areaSegurancaProblema.problema} AND cod_area_problema = :#{#areaSegurancaProblema.area}", nativeQuery = true)
	//public void atualizarAreaSegurancaProblema(AreaSegurancaProblema areaSegurancaProblema);                                                                           //status_festa    = CAST( :#{#festa.statusFesta}                    AS status_festa_t)
	
	
	//@Modifying(clearAutomatically = true)
	//@Query(value = "UPDATE area_seguranca_x_problema SET cod_usuario_resolv = :#{#areaSegurancaProblema.codUsuarioResolv}, status_problema = CAST( :#{#areaSegurancaProblema.statusProblema} AS status_problema_t), horario_inicio = :#{#areaSegurancaProblema.horarioInicio}, horario_fim = :#{#areaSegurancaProblema.horarioFim}, cod_usuario_emissor = :#{#areaSegurancaProblema.codUsuarioEmissor}, descricao_prob = :#{#areaSegurancaProblema.descProblema} WHERE cod_problema = :#{#areaSegurancaProblema.problema} AND cod_area_problema = :#{#areaSegurancaProblema.area}", nativeQuery = true)
	//public void atualizarAreaSegurancaProblema(AreaSegurancaProblema areaSegurancaProblema);                                                                           //status_festa    = CAST( :#{#festa.statusFesta}                    AS status_festa_t)
	
	
	//@Modifying(clearAutomatically = true)
	//@Query(value = "UPDATE area_seguranca_x_problema SET cod_festa = :#{#areaSegurancaProblema.festa}, cod_usuario_resolv = :#{#areaSegurancaProblema.codUsuarioResolv}, status_problema = CAST( :#{#areaSegurancaProblema.statusProblema} AS status_problema_t), horario_inicio = :#{#areaSegurancaProblema.horarioInicio}, horario_fim = :#{#areaSegurancaProblema.horarioFim}, cod_usuario_emissor = :#{#areaSegurancaProblema.codUsuarioEmissor}, descricao_prob = :#{#areaSegurancaProblema.descProblema} WHERE cod_problema = :#{#areaSegurancaProblema.problema} AND cod_area_problema = :#{#areaSegurancaProblema.area}", nativeQuery = true)
	//public void atualizarAreaSegurancaProblema(AreaSegurancaProblema areaSegurancaProblema);                                                                           //status_festa    = CAST( :#{#festa.statusFesta}                    AS status_festa_t)
	
	//@Query(value = "UPDATE area_seguranca SET nome_area =:#{#areaSeguranca.nomeArea}, cod_area = :#{#areaSeguranca.codArea}, cod_festa = :#{#areaSeguranca.codFesta} WHERE cod_area = :#{#areaSeguranca.codArea}", nativeQuery = true)
	//public void updateArea(AreaSeguranca areaSeguranca);
	
	/*
	@Query(value = "SELECT p FROM AreaSegurancaProblema p WHERE p.area = :area AND p.problema = :problema")
	public AreaSegurancaProblema findAreaSegurancaProblema(@Param("area") int area, @Param("problema") int problema);

	@Query(value = "SELECT p FROM AreaSegurancaProblema p WHERE p.festa = :festa")
	public List<AreaSegurancaProblema> findAllAreaSegurancaProblemaFesta(@Param("festa") int festa);

	@Query(value = "SELECT p FROM AreaSegurancaProblema p WHERE p.area = :area AND p.festa = :festa")
	public List<AreaSegurancaProblema> findAllAreaSegurancaProblemaArea(@Param("area") int area, @Param("festa") int festa);
*/
}
