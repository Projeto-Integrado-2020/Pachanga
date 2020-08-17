package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer>{

	@Query(value = "SELECT n FROM Notificacao n JOIN n.notificacaoGrupo ng JOIN ng.grupo g JOIN g.usuarios u WHERE u.codUsuario = :idUser")
	public List<Notificacao> findNotificacaoGrupoByUserId(int idUser);
	
	@Query(value = "SELECT n FROM Notificacao n JOIN n.convidados c JOIN c.grupos g JOIN g.usuarios u WHERE u.codUsuario = :idUser")
	public List<Notificacao> findConvidadoNotificacaoByUserId(int idUser);
	
	@Modifying
	@Query(value = "DELETE FROM convidado_x_notificacao WHERE cod_convidado = :codConvidado AND cod_notificacao = :codNotificacao", nativeQuery = true)
	public void deleteConvidadoNotificacao(int codConvidado, int codNotificacao);

	@Modifying
	@Query(value = "DELETE FROM notificacao_x_grupo WHERE cod_notificacao = :codNotificacao AND cod_grupo = :codGrupo", nativeQuery = true)
	public void deleteNotificacaoGrupo(int codGrupo, int codNotificacao);
	
	@Modifying
	@Query(value = "INSERT INTO notificacao_x_grupo (cod_grupo, cod_notificacao) VALUES(:codGrupo, :codNotificacao)", nativeQuery = true)
	public void insertNotificacaoGrupo(int codGrupo, int codNotificacao);
	
	@Modifying
	@Query(value = "INSERT INTO convidado_x_notificacao (cod_convidado, cod_notificacao) VALUES(:codConvidado, :codNotificacao)", nativeQuery = true)
	public void insertConvidadoNotificacao(int codConvidado, int codNotificacao);
	
	public Notificacao findByCodNotificacao(int codNotificacao);

}
