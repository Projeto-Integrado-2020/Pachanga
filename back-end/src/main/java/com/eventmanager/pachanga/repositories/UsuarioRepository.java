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
	
	public Usuario findByEmail(String email);
	
	public Usuario findByEmailAndTipConta(String email, String tipConta);
	
	public Usuario findByNomeUser(String nomeUser);
	
	public Usuario findById(int idUsuario);
	
	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta")
	public List<Usuario> findByIdFesta(int codFesta);
	
	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta AND g.nomeGrupo = :nomeGrupo")
	public Usuario findByFestaGrupo(int codFesta, String nomeGrupo); 
	
	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta AND u.codUsuario = :codUsuario")
	public Usuario findBycodFestaAndUsuario(int codFesta, int codUsuario);
}
