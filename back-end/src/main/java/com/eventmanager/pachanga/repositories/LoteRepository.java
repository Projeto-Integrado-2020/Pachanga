package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.Lote;

public interface LoteRepository extends JpaRepository<Lote, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_lote');", nativeQuery = true)
	public int getNextValMySequence();

	@Query(value = "SELECT l FROM Lote l JOIN l.festa f WHERE f.codFesta = :codFesta")
	List<Lote> listaLoteFesta(int codFesta);

	@Query(value = "SELECT l FROM Lote l JOIN l.festa f WHERE f.codFesta = :codFesta AND l.nomeLote = :nomeLote")
	public Lote findByNomeLote(String nomeLote, int codFesta);

	@Query(value = "SELECT l FROM Lote l JOIN l.festa f WHERE l.codLote = :codLote")
	public Lote findByCodLote(int codLote);
	
	@Query(value = "SELECT l FROM Lote l JOIN l.festa f WHERE f.codFesta = :codFesta AND l.horarioFim > CURRENT_TIMESTAMP AND l.quantidade > ( SELECT count(i) FROM Ingresso i JOIN i.lote lt JOIN lt.festa fe WHERE fe.codFesta = f.codFesta AND lt.codLote = l.codLote ) ")
	public List<Lote> findAllCompraveisFesta(int codFesta);

}