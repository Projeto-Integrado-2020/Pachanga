package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Usuario;

@Repository
public interface ConvidadoRepository extends CrudRepository<Convidado, Integer>{

	@Query(value = "SELECT u FROM Grupo g JOIN g.usuarios u WHERE g.codGrupo = :codGrupo AND u.codUsuario = :codUsuario ")
	public Usuario findUsuarioNoGrupo(@Param("codGrupo") int codGrupo, @Param("codUsuario") int codUsuario);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO usuario_x_grupo(cod_usuario, cod_grupo) VALUES(:codUsuario, :codGrupo)", nativeQuery = true)
	public void saveUsuarioGrupo(@Param("codUsuario") int codUsuario, @Param("codGrupo") int codGrupo);

	@Query(value = "SELECT c FROM Convidado c WHERE c.email = :emailConvidado")
	public Convidado findByEmail(String emailConvidado);
	
	@Query(value = "SELECT c FROM Convidado c WHERE c.codConvidado = :idConvidado")
	public Convidado findByIdConvidado(Integer idConvidado);
	
	@Query(value = "SELECT NEXTVAL('seq_convidado');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO convidado_x_grupo(cod_convidado, cod_grupo) VALUES(:codConvidado, :codGrupo)", nativeQuery = true)
	public void saveConvidadoGrupo(@Param("codConvidado") int codConvidado, @Param("codGrupo") int codGrupo);
	
	@Query(value = "SELECT c FROM Convidado c JOIN c.grupos g WHERE c.email = :email AND g.codGrupo = :codGrupo")
	public Convidado findByEmailByGrupo(String email, int codGrupo);

	@Query(value = "SELECT c FROM Convidado c JOIN c.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta")
	public List<Convidado> findConvidadosByCodFesta(int codFesta);
	
	@Query(value = "SELECT u FROM Grupo g JOIN g.convidados u WHERE g.codGrupo = :codGrupo")
	public List<Convidado> findConvidadosNoGrupo(@Param("codGrupo") int codGrupo);
	
	@Query(value = "SELECT u.codConvidado FROM Grupo g JOIN g.convidados u WHERE g.codGrupo = :codGrupo")
	public List<Integer> findCodConvidadosNoGrupo(@Param("codGrupo") int codGrupo);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM Convidado WHERE cod_convidado = :codConvidado", nativeQuery = true)
	public void deleteConvidado(Integer codConvidado);
	
	@Modifying
	@Query(value = "DELETE FROM convidado_x_grupo WHERE cod_convidado = :codConvidado AND cod_grupo = :codGrupo", nativeQuery = true)
	public void deleteConvidadoGrupo(Integer codConvidado, Integer codGrupo);

	@Query(value = "SELECT c FROM Convidado c JOIN c.grupos g JOIN g.festa f WHERE c.codConvidado = :codConvidado AND g.codGrupo = :idGrupo")
	public Convidado findConvidadoGrupo(int codConvidado, int idGrupo);

	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM convidado WHERE cod_convidado IN :codConvidados", nativeQuery = true)
	public void deleteConvidados(List<Integer> codConvidados);

	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM convidado_x_grupo WHERE cod_grupo = :codGrupo", nativeQuery = true)
	public void deleteAllConvidadosGrupo(Integer codGrupo);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM convidado_x_notificacao WHERE cod_convidado IN :codConvidados", nativeQuery = true)
	public void deleteAllConvidadosNotificacao(List<Integer> codConvidados);

	@Query(value = "SELECT c FROM Convidado c JOIN c.grupos g JOIN g.festa f WHERE c.email = :email AND f.codFesta = :codFesta")
	public Convidado findByEmailByFesta(String email, int codFesta);
	
}
