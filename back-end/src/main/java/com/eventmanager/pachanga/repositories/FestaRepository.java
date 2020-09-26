package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Festa;

@Repository
public interface FestaRepository extends JpaRepository<Festa, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_festa');", nativeQuery = true)
	public int getNextValMySequence();

	@Query(value = "SELECT DISTINCT f FROM Festa f JOIN f.grupos g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario")
	public List<Festa> findByUsuarios(int codUsuario);
	
	@Query(value = "SELECT f FROM Festa f JOIN f.grupos g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario AND g.nomeGrupo='ORGANIZADOR' AND f.codFesta = :codFesta")
	public Festa findFestaByUsuarioResponsavel(@Param("codUsuario")int codUsuario,@Param("codFesta") int codFesta);
	
	@Query(value = "SELECT f FROM Festa f WHERE f.nomeFesta = :nomeFesta")
	public Festa findByNomeFesta(String nomeFesta);
	
	@Query(value = "SELECT f FROM Festa f WHERE f.codFesta = :codFesta")
	public Festa findById(int codFesta);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from Festa f where f.codFesta = :#{#festa.codFesta}")
	public void deleteFesta(Festa festa);

	public Festa findByCodFesta(int codFesta);
	
	@Query(value = "SELECT f FROM Festa f JOIN f.grupos g JOIN g.usuarios u WHERE f.codFesta = :codFesta ")
	public String findByFuncionalidade(int codFesta);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE festa SET status_festa = CAST(:statusFesta AS status_festa_t) where cod_festa = :codFesta", nativeQuery = true)
	public void updateStatusFesta(String statusFesta, int codFesta);

	@Query(value = "SELECT f FROM Festa f JOIN f.grupos g JOIN g.convidados c WHERE g.codGrupo = :codGrupo AND c.codConvidado = :codConvidado")
	public Festa findFestaByCodConvidadoAndCodGrupo(Integer codConvidado, Integer codGrupo);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE festa SET nome = :#{#festa.nomeFesta}, status_festa = CAST( :#{#festa.statusFesta} AS status_festa_t), organizador = :#{#festa.organizador}, horario_inicio = :#{#festa.horarioInicioFesta}, horario_fim = :#{#festa.horarioFimFesta}, descricao_festa = :#{#festa.descricaoFesta}, cod_endereco = :#{#festa.codEnderecoFesta}, desc_organizador = :#{#festa.descOrganizador} WHERE cod_festa = :#{#festa.codFesta}", nativeQuery = true)
	public void updateFesta(Festa festa);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO festa (cod_festa, nome, status_festa,organizador,horario_inicio,horario_fim, descricao_festa, cod_endereco, desc_organizador) VALUES(:#{#festa.codFesta}, :#{#festa.nomeFesta}, CAST( :#{#festa.statusFesta} AS status_festa_t),:#{#festa.organizador},:#{#festa.horarioInicioFesta},:#{#festa.horarioFimFesta}, :#{#festa.descricaoFesta}, :#{#festa.codEnderecoFesta}, :#{#festa.descOrganizador})", nativeQuery = true)
	public void saveFesta(Festa festa);
	
}
