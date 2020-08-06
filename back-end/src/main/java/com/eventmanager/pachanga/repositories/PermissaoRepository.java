package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Permissao;

@Repository
@Transactional
public interface PermissaoRepository extends CrudRepository<Permissao, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_permissao');", nativeQuery = true)
	public int getNextValMySequence();
	
	public Permissao findById(int codPermissao);
	
	@Query(value = "SELECT p FROM Permissao p")
	public List<Permissao> findAllPermissao();
	
}
