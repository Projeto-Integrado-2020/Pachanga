package com.eventmanager.pachanga.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.NotificacaoGrupo;

@Repository
public interface NotificacaoGrupoRepository extends JpaRepository<NotificacaoGrupo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_notificacao_grupo');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT n FROM NotificacaoGrupo n JOIN n.grupo g JOIN n.notificacao t WHERE g.codGrupo = :codGrupo AND t.codNotificacao = :codNotificacao")
	public NotificacaoGrupo findNotificacaoGrupo(int codGrupo, int codNotificacao);
	
	@Query(value = "SELECT ng FROM NotificacaoGrupo ng JOIN ng.grupo g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario")
	public List<NotificacaoGrupo> getNotificacoesGrupo(int codUsuario);
	
	@Modifying
	@Query(value = "INSERT INTO notificacao_x_grupo (cod_notificacao_grupo, cod_grupo, cod_notificacao, mensagem, data_emissao) VALUES(:codNotificacaoGrupo, :codGrupo, :codNotificacao, :mensagem, :dataEmissao)", nativeQuery = true)
	public void insertNotificacaoGrupo(int codNotificacaoGrupo ,int codGrupo, int codNotificacao, String mensagem, LocalDateTime dataEmissao);

}
