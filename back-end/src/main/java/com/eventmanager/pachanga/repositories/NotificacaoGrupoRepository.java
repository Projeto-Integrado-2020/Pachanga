package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.NotificacaoGrupo;

@Repository
public interface NotificacaoGrupoRepository extends JpaRepository<NotificacaoGrupo, Integer>{
	
	@Query(value = "SELECT n FROM NotificacaoGrupo n JOIN n.grupo g JOIN n.notificacao t WHERE g.codGrupo = :codGrupo AND t.codNotificacao = :codNotificacao")
	public NotificacaoGrupo findNotificacaoGrupo(int codGrupo, int codNotificacao);
	
}
