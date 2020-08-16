package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.NotificacaoUsuario;

public interface NotificacaoUsuarioRepository extends JpaRepository<NotificacaoUsuario, Integer>{
	
	@Query(value = "SELECT nu FROM NotificacaoUsuario nu JOIN nu.usuario u JOIN nu.notificacao n WHERE u.codUsuario = :idUsuario AND n.codNotificacao = :codNotificacao")
	public NotificacaoUsuario getNotificacaoUsuario(int idUsuario, int codNotificacao);

}
