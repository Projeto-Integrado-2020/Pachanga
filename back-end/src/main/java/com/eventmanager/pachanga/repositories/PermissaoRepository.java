package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Permissao;

@Repository
public interface PermissaoRepository extends CrudRepository<Permissao, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_permissao');", nativeQuery = true)
	public int getNextValMySequence();
	
	public Permissao findById(int codPermissao);
	
	@Query(value = "SELECT p FROM Permissao p")
	public List<Permissao> findAllPermissao();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM permissao_x_grupo WHERE cod_grupo = :codGrupo", nativeQuery = true)
	public void deletePermissoesGrupo(int codGrupo);
	
	@Query(value = "SELECT p FROM Permissao p WHERE codPermissao IN (:permissoes)")
	public List<Permissao> findPermissoes(List<Integer> permissoes);
}
