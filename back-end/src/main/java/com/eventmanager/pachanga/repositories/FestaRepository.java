package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;

@Repository
@Transactional
public interface FestaRepository extends JpaRepository<Festa, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_festa');", nativeQuery = true)
	public int getNextValMySequence();

	@Query(value = "SELECT f FROM Festa f JOIN f.grupos g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario")
	public List<Festa> findByUsuarios(int codUsuario);
	
	@Query(value = "SELECT f FROM Festa f JOIN f.grupos g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario AND g.nomeGrupo='ORGANIZADOR' AND f.codFesta = :codFesta")
	public Festa findFestaByUsuarioResponsavel(@Param("codUsuario")int codUsuario,@Param("codFesta") int codFesta);
	
	public Festa findByNomeFesta(String nomeFesta);
	
	public Festa findById(int codFesta);
	
	@Modifying
	@Query(value = "delete from Festa f where f.codFesta = :#{#festa.codFesta}")
	public void deleteFesta(Festa festa);

	public Festa findByCodFesta(int codFesta);
	
	@Query(value = "SELECT f FROM Festa f JOIN f.grupos g JOIN g.usuarios u WHERE f.codFesta = :codFesta ")
	public String findByFuncionalidade(int codFesta);
	
	@Modifying
	@Query(value = "UPDATE Festa f SET statusFesta = :statusFesta where codFesta = :codFesta")
	public void updateStatusFesta(String statusFesta, int codFesta);
	
}
