package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Usuario;

@Repository
public interface FestaRepository extends CrudRepository<Festa, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_festa');", nativeQuery = true)
	public int getNextValMySequence();

	@Query(value = "SELECT f FROM Festa f JOIN f.festaGrupoUsuarios fgu WHERE fgu.usuario = :usuario")
	public List<Festa> findByUsuarios(Usuario usuario);
	
	@Query(value = "SELECT f FROM Festa f")
	public List<Festa> findFesta();
	
	public Festa findByNomeFesta(String nomeFesta);

}
