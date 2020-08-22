package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_grupo');", nativeQuery = true)
	public int getNextValMySequence();
	
    public Grupo findById(int codGrupo);
	
	@Query(value = "SELECT g FROM Grupo g JOIN g.festa f WHERE g.codGrupo = :codGrupo")
	public Grupo findByCod(@Param("codGrupo") int codGrupo);
	
	@Query(value = "SELECT g FROM usuario_x_grupo u JOIN u.grupo g WHERE u.codUsuario = :codUsuario", nativeQuery = true)
	public List<Grupo> findGruposUsuario(@Param("codUsuario") int codUsuario);
	
	@Query(value = "SELECT g FROM Grupo g JOIN g.festa f WHERE f.codFesta = :codFesta")
	public List<Grupo> findGruposFesta(@Param("codFesta") int codFesta);
	
	@Query(value = "SELECT * FROM Grupo WHERE cod_festa = :codFesta AND nome_grupo = :nomeGrupo", nativeQuery = true)
	public List<Grupo> findGruposDuplicados(@Param("codFesta") int codFesta, @Param("nomeGrupo") String nomeGrupo);

	@Modifying
	@Query(value = "DELETE FROM Grupo WHERE codGrupo = :codGrupo")
	public void deleteGrupo(@Param("codGrupo") int codGrupo);
	
	@Modifying
	@Query(value = "DELETE FROM usuario_x_grupo WHERE cod_grupo = :codGrupo AND cod_usuario = :codUsuario", nativeQuery = true)
	public void deleteUsuarioGrupo(@Param("codUsuario") int codUsuario, @Param("codGrupo") int codGrupo);

	@Query(value = "SELECT g.nomeGrupo FROM Grupo g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario AND g.festa.codFesta = :codFesta")
	public List<String> findFuncionalidade(@Param("codFesta")int codFesta,@Param("codUsuario") int codUsuario);
	
	@Query(value = "SELECT g FROM Grupo g JOIN g.usuarios u JOIN g.permissoes p JOIN g.festa f WHERE u.codUsuario = :codUsuario AND f.codFesta = :codFesta AND p.codPermissao = :codPermissao")
	public Grupo findGrupoPermissaoUsuario(int codFesta, int codUsuario, int codPermissao);
	
	@Modifying
	@Query(value = "INSERT INTO permissao_x_grupo(cod_grupo, cod_permissao) VALUES(:codGrupo, :codPermissao)", nativeQuery = true)
	public void saveGrupoPermissao(@Param("codGrupo")int codGrupo, @Param("codPermissao")int codPermissao);
	
	@Modifying
	@Query(value = "DELETE FROM permissao_x_grupo(cod_grupo, cod_permissao) WHERE cod_grupo = :codGrupo AND cod_permissao = :codPermissao", nativeQuery = true)
	public void deleteGrupoPermissao(@Param("codGrupo")int codGrupo, @Param("codPermissao")int codPermissao);

	@Query(value = "SELECT g FROM Grupo g JOIN g.permissoes p WHERE p.codPermissao = :codPermissao")
	public List<Grupo> findGruposPorPermissao(int codPermissao);
	
	@Query(value = "SELECT p FROM Grupo g JOIN g.permissoes p WHERE g.codGrupo = :codGrupo")
	public List<Permissao> findPermissoesPorGrupo(int codGrupo);
	
	@Query(value = "SELECT u FROM Grupo g JOIN g.usuarios u WHERE g.codGrupo = :codGrupo")
	public List<Usuario> findUsuariosPorGrupo(@Param("codGrupo") int codGrupo);
	
	@Modifying
	@Query(value = "INSERT INTO usuario_x_grupo(cod_usuario, cod_grupo) VALUES(:codUsuario, :codGrupo)", nativeQuery = true)
	public void saveUsuarioGrupo(@Param("codUsuario") int codUsuario, @Param("codGrupo") int codGrupo);
	
	@Query(value = "SELECT c FROM Convidado c JOIN c.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta")
	public List<Convidado> findConvidadosByCodFesta(int codFesta);
	
	@Query(value = "SELECT u FROM Grupo g JOIN g.convidados u WHERE g.codGrupo = :codGrupo")
	public List<Convidado> findConvidadosNoGrupo(@Param("codGrupo") int codGrupo);
	
	@Modifying
	@Query(value = "DELETE FROM convidado_x_grupo WHERE cod_grupo = :codGrupo AND cod_convidado = :codConvidado", nativeQuery = true)
	public void deleteConvidadoGrupo(Integer codConvidado, Integer codGrupo);
	
	@Query(value= "SELECT cod_convidado FROM convidado_x_grupo WHERE cod_convidado = :codConvidado", nativeQuery = true)
	public Integer existsConvidadoGrupo(Integer codConvidado);
	
	@Modifying
	@Query(value = "DELETE FROM usuario_x_grupo WHERE cod_grupo = :codGrupo AND cod_usuario = :codUsuario", nativeQuery = true)
	public void deleteMembroGrupo(Integer codGrupo, Integer codUsuario);
	
	@Modifying
	@Query(value = "DELETE FROM usuario_x_grupo WHERE cod_grupo = :codGrupo", nativeQuery = true)
	public void deleteAllMembrosGrupo(Integer codGrupo);
	
	@Query(value = "SELECT g.codGrupo FROM Grupo g JOIN g.festa f WHERE f.codFesta = :codFesta AND g.codGrupo IN :codGrupos")
	public List<Integer> findGruposFestaIn(List<Integer> codGrupos, int codFesta);
	
	@Query(value = "SELECT g.codGrupo FROM Grupo g JOIN g.usuarios u WHERE g.codGrupo IN :codGrupos AND u.codUsuario = :codUsuario")
	public List<Integer> findGruposUsuarioByGrupos(List<Integer> codGrupos, int codUsuario);
}
