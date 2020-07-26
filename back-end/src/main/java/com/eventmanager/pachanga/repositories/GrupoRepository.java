package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Grupo;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_grupo');", nativeQuery = true)
	public int getNextValMySequence();
	
	public Grupo findById(int codGrupo);
	
	//@Query(value = "SELECT g FROM xz g JOIN g.festa f WHERE f.codFesta = :codFesta")
	//public List<Grupo> findGruposFesta(int codFesta);
	
	@Modifying
	@Query(value = "SELECT g FROM usuario_x_grupo u JOIN u.grupo g WHERE u.cod_usuario = :codUsuario")
	public List<Grupo> findGruposUsuario(int codUsuario);
	
	@Modifying
	@Query(value = "SELECT g FROM Grupo g JOIN g.festa f WHERE f.cod_festa = :codFesta")
	public List<Grupo> findGruposFesta(int codFesta);

	@Modifying
	@Query(value = "DELETE FROM usuario_x_grupo WHERE cod_grupo = :codGrupo", nativeQuery = true)
	public void deleteUsuarioGrupo(@Param("codGrupo") int codGrupo);
	
	@Modifying
	@Query(value = "DELETE FROM usuario_x_grupo WHERE cod_grupo = :codGrupo AND cod_usuario = :codUsuario", nativeQuery = true)
	public void deleteUsuarioGrupo(@Param("codUsuario") int codUsuario, @Param("codGrupo") int codGrupo);
	
	@Modifying
	@Query(value = "INSERT INTO usuario_x_grupo(cod_usuario, cod_grupo) VALUES(:codUsuario, :codGrupo)", nativeQuery = true)
	public void saveUsuarioGrupo(@Param("codUsuario") int codUsuario, @Param("codGrupo") int codGrupo);

	@Query(value = "SELECT g.nomeGrupo FROM Grupo g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario AND g.festa.codFesta = :codFesta")
	public String findFuncionalidade(@Param("codFesta")int codFesta,@Param("codUsuario") int codUsuario);
	
	@Query(value = "SELECT g FROM Grupo g JOIN g.usuarios u JOIN g.permissoes p JOIN g.festa f WHERE u.codUsuario = :codUsuario AND f.codFesta = :codFesta AND p.codPermissao = :codPermissao")
	public Grupo findGrupoPermissaoUsuario(int codFesta, int codUsuario, int codPermissao);
	
	@Modifying
	@Query(value = "INSERT INTO permissao_x_grupo(cod_grupo, cod_permissao) VALUES(:codGrupo, :codPermissao)", nativeQuery = true)
	public void saveGrupoPermissao(@Param("codGrupo")int codGrupo, @Param("codPermissao")int codPermissao);
}
