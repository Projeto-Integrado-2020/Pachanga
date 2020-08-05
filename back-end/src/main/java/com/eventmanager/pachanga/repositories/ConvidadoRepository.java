package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eventmanager.pachanga.domains.Convidado;

public interface ConvidadoRepository extends CrudRepository<Convidado, Integer>{

	@Query(value = "SELECT c FROM Convidado c WHERE c.email = :emailConvidado")
	public Convidado findByEmail(String emailConvidado);

}
