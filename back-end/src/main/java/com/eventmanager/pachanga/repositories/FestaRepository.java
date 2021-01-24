package com.eventmanager.pachanga.repositories;

import java.time.LocalDateTime;
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
	
	@Query(value = "SELECT f FROM Festa f JOIN f.grupos g JOIN g.convidados c WHERE g.codGrupo = :codGrupo AND c.codConvidado = :codConvidado")
	public Festa findFestaByCodConvidadoAndCodGrupo(Integer codConvidado, Integer codGrupo);
	
	@Query(value = "SELECT f FROM Lote l JOIN l.festa f WHERE l.horarioFim > :horarioFim AND l.quantidade > ( SELECT count(i) FROM Ingresso i JOIN i.lote lt JOIN lt.festa fe WHERE fe.codFesta = f.codFesta AND lt.codLote = l.codLote ) ")
	public List<Festa> findAllTeste(LocalDateTime horarioFim);
	
	@Query(value = "SELECT DISTINCT f FROM Lote l JOIN l.festa f WHERE l.horarioFim > CURRENT_TIMESTAMP AND l.quantidade > ( SELECT count(i) FROM Ingresso i JOIN i.lote lt JOIN lt.festa fe WHERE fe.codFesta = f.codFesta AND lt.codLote = l.codLote ) ")
	public List<Festa> findAllComLotesCompraveis();

	@Query(value = "SELECT f FROM Festa f WHERE f.horarioFimFestaReal IS NOT NULL AND f.horarioFimFestaReal = (CURRENT_DATE - 3)")
	public List<Festa> findFestasAcabadas();
	
}
