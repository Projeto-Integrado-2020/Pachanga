package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.NotificacaoGrupo;

public interface NotificacaoGrupoRepository extends JpaRepository<NotificacaoGrupo, Integer>{
	
	@Query(value = "SELECT ng FROM NotificacaoGrupo ng JOIN ng.grupo g JOIN g.usuarios u WHERE u.codUsuario = :codUsuario")
	public List<NotificacaoGrupo> getNotificacoesGrupo(int codUsuario);

}
