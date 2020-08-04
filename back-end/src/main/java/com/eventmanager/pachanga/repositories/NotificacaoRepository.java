package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer>{

	@Query(value = "SELECT n FROM Notificacao n JOIN n.grupos g JOIN n.convidados JOIN g.usuarios u WHERE u.codUsuario = :idUser")
	List<Notificacao> findByUserId(int idUser);

}
