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
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM convidado_x_notificacao WHERE cod_convidado = :codConvidado AND cod_notificacao = :codNotificacao AND mensagem = :mensagem", nativeQuery = true)
	public void deleteConvidadoNotificacao(int codConvidado, int codNotificacao, String mensagem);

	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM notificacao_x_grupo WHERE mensagem = :mensagem AND cod_grupo = :codGrupo", nativeQuery = true)
	public void deleteNotificacaoGrupo(int codGrupo, String mensagem);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM notificacao_x_grupo WHERE mensagem LIKE %:mensagem%", nativeQuery = true)
	public void deleteNotificacaoGrupo(String mensagem);
	
	@Query(value = "SELECT n FROM Notificacao n WHERE n.codNotificacao = :codNotificacao")
	public Notificacao findByCodNotificacao(int codNotificacao);

}
