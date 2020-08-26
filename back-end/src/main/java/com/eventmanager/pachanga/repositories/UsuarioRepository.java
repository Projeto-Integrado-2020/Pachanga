package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_usuario');", nativeQuery = true)
	public int getNextValMySequence();
	
	public Usuario findByEmail(String email);
	
	public Usuario findByNomeUser(String nomeUser);
	
	public Usuario findById(int idUsuario);
	
	@Query(value = "SELECT DISTINCT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta")
	public List<Usuario> findByIdFesta(int codFesta);
	
	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta AND g.organizador = :organizador")
	public Usuario findByFestaGrupo(int codFesta, boolean organizador); 
	
	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta AND u.codUsuario = :codUsuario")
	public Usuario findBycodFestaAndUsuario(int codFesta, int codUsuario);
	
	@Modifying
	@Query(value = "UPDATE Usuario u SET u.facebook = :facebook WHERE u.codUsuario = :idUsuario")
	public void updateFacebookUsuario(int idUsuario, String facebook);

	@Modifying
	@Query(value = "UPDATE Usuario u SET u.gmail = :gmail WHERE u.codUsuario = :idUsuario")
	public void updateGmailUsuario(int idUsuario, String gmail);

	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f WHERE f.codFesta = :codFesta AND u.email = :email")
	public Usuario findBycodFestaAndEmail(int codFesta, String email);
	
	@Query(value = "SELECT u FROM Usuario u JOIN u.grupos g JOIN g.festa f JOIN g.permissoes p WHERE u.codUsuario = :codUsuario AND f.codFesta = :codFesta AND p.codPermissao = :codPermissao")
	public List<Usuario> findUsuarioComPermissao(Integer codFesta, Integer codUsuario, int codPermissao);
}
