package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_usuario');", nativeQuery = true)
	public int getNextValMySequence();
	
	Usuario findByEmail(String email);
	
	Usuario findByEmailAndTipConta(String email, String tipConta);
	
	Usuario findByNomeUser(String nomeUser);
	
}
