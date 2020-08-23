package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.NotificacaoUsuario;

public interface NotificacaoUsuarioRepository extends JpaRepository<NotificacaoUsuario, Integer>{
	
	@Query(value = "SELECT nu FROM NotificacaoUsuario nu JOIN nu.usuario u JOIN nu.notificacao n WHERE u.codUsuario = :idUsuario AND n.codNotificacao = :codNotificacao")
	public NotificacaoUsuario getNotificacaoUsuario(int idUsuario, int codNotificacao);
	
	@Query(value = "SELECT nu FROM NotificacaoUsuario nu JOIN nu.usuario u WHERE u.codUsuario = :codUsuario")
	public List<NotificacaoUsuario> getNotificacoesUsuario(int codUsuario);

	@Modifying
	@Query(value = "INSERT INTO notificacao_x_usuario (cod_usuario, cod_notificacao, destaque, status,mensagem) VALUES(:codUsuario, :codNotificacao, :destaque, :status, :mensagem)", nativeQuery = true)
	public void insertNotificacaoUsuario(Integer codUsuario, Integer codNotificacao, boolean destaque, String status, String mensagem);

	@Query(value = "SELECT nu FROM NotificacaoUsuario nu JOIN nu.usuario u JOIN nu.notificacao n WHERE u.codUsuario = :idUser AND n.codNotificacao = :codNotificacao AND nu.mensagem = :mensagem")
	public NotificacaoUsuario getNotificacaoUsuarioByMensagem(int idUser, int codNotificacao, String mensagem);

}
