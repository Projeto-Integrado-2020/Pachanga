package com.eventmanager.pachanga.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.NotificacaoUsuario;

public interface NotificacaoUsuarioRepository extends JpaRepository<NotificacaoUsuario, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_notificacao_usuario');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT nu FROM NotificacaoUsuario nu JOIN nu.usuario u WHERE u.codUsuario = :idUsuario AND nu.codNotificacaoUsuario = :codNotificacao")
	public NotificacaoUsuario getNotificacaoUsuario(int idUsuario, int codNotificacao);
	
	@Query(value = "SELECT nu FROM NotificacaoUsuario nu JOIN nu.usuario u WHERE u.codUsuario = :codUsuario")
	public List<NotificacaoUsuario> getNotificacoesUsuario(int codUsuario);

	@Modifying
	@Query(value = "INSERT INTO notificacao_x_usuario (cod_notificacao_usuario, cod_usuario, cod_notificacao, destaque, status, mensagem, data_emissao) VALUES(:codNotificacaoUsuario, :codUsuario, :codNotificacao, :destaque, :status, :mensagem, :dataEmissao)", nativeQuery = true)
	public void insertNotificacaoUsuario(int codNotificacaoUsuario ,Integer codUsuario, Integer codNotificacao, boolean destaque, String status, String mensagem, LocalDateTime dataEmissao);

	@Query(value = "SELECT nu FROM NotificacaoUsuario nu JOIN nu.usuario u WHERE u.codUsuario = :idUser AND nu.mensagem = :mensagem")
	public List<NotificacaoUsuario> getNotificacaoUsuarioByMensagem(int idUser, String mensagem);

}
