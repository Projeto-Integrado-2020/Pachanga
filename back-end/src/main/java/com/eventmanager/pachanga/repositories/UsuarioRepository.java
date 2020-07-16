package com.eventmanager.pachanga.repositories;

import java.util.List;

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
	
	Usuario findById(int idUsuario);
	
	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta")
	List<Usuario> findByIdFesta(int codFesta);
}
