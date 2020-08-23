package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.NotificacaoGrupo;

@Repository
public interface NotificacaoGrupoRepository extends JpaRepository<NotificacaoGrupo, Integer>{
	
	@Query(value = "SELECT n FROM NotificacaoGrupo n JOIN n.grupo g JOIN n.notificacao t WHERE g.codGrupo = :codGrupo AND t.codNotificacao = :codNotificacao")
	public NotificacaoGrupo findNotificacaoGrupo(int codGrupo, int codNotificacao);
	
	@Query(value = "SELECT ng FROM NotificacaoGrupo ng JOIN ng.grupo g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario")
	public List<NotificacaoGrupo> getNotificacoesGrupo(int codUsuario);

}
