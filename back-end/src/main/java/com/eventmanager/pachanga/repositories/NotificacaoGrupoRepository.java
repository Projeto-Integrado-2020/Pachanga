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
	
	@Query(value = "SELECT n FROM NotificacaoGrupo n JOIN n.grupo g JOIN g.festa f JOIN f.estoques e JOIN n.notificacao t WHERE g.codGrupo = :codGrupo AND t.codNotificacao = :codNotificacao AND e.codEstoque = :codEstoque")
	public NotificacaoGrupo findNotificacaoGrupo(Integer codGrupo, Integer codNotificacao, Integer codEstoque);
	
	@Query(value = "SELECT ng FROM NotificacaoGrupo ng JOIN ng.grupo g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario")
	public List<NotificacaoGrupo> getNotificacoesGrupo(Integer codUsuario);
	
	@Query(value = "SELECT ng FROM NotificacaoGrupo ng JOIN ng.grupo g WHERE g.codGrupo = :codGrupo AND ng.mensagem = :mensagem")
	public List<NotificacaoGrupo> getNotificacoesGrupoByMensagem(Integer codGrupo, String mensagem);
	
	@Modifying
	@Query(value = "INSERT INTO notificacao_x_grupo (cod_notificacao_grupo, cod_grupo, cod_notificacao, mensagem, data_emissao) VALUES(:codNotificacaoGrupo, :codGrupo, :codNotificacao, :mensagem, :dataEmissao)", nativeQuery = true)
	public void insertNotificacaoGrupo(Integer codNotificacaoGrupo ,Integer codGrupo, Integer codNotificacao, String mensagem, LocalDateTime dataEmissao);

	@Query(value = "SELECT ng FROM NotificacaoGrupo ng JOIN ng.grupo g WHERE g.codGrupo IN :codGrupo")
	public List<NotificacaoGrupo> findNotificacoesGrupos(List<Integer> codGrupo);

}
