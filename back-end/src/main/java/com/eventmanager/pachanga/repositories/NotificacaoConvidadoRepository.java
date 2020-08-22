package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.NotificacaoConvidado;

public interface NotificacaoConvidadoRepository extends JpaRepository<NotificacaoConvidado, Integer>{
	
	@Query(value = "SELECT nc FROM NotificacaoConvidado nc JOIN nc.convidado c WHERE c.email = :email")
	public List<NotificacaoConvidado> findConvidadoNotificacaoByEmail(String email);
	
	@Modifying
	@Query(value = "INSERT INTO convidado_x_notificacao (cod_convidado, cod_notificacao, mensagem) VALUES(:codConvidado, :codNotificacao, :mensagem)", nativeQuery = true)
	public void insertConvidadoNotificacao(int codConvidado, int codNotificacao, String mensagem);

}
