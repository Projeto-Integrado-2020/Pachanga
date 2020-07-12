package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Grupo;

@Repository
@Transactional
public interface GrupoRepository extends CrudRepository<Grupo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_grupo');", nativeQuery = true)
	public int getNextValMySequence();
	
	public Grupo findById(int codGrupo);
	
	@Query(value = "SELECT g FROM Grupo g JOIN g.festa f WHERE f.codFesta = :codFesta")
	public List<Grupo> findGruposFesta(int codFesta);
	
	@Modifying
	@Query(value = "DELETE FROM usuario_x_grupo WHERE cod_grupo = :codGrupo", nativeQuery = true)
	public void deleteUsuarioGrupo(@Param("codGrupo") int codGrupo);
	
	@Modifying
	@Query(value = "INSERT INTO usuario_x_grupo(cod_usuario, cod_grupo) VALUES(:codUsuario, :codGrupo)", nativeQuery = true)
	public void saveUsuarioGrupo(@Param("codUsuario") int codUsuario, @Param("codGrupo") int codGrupo);

}
