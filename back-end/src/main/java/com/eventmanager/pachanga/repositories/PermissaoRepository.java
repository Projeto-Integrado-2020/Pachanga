package com.eventmanager.pachanga.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Permissao;

@Repository
@Transactional
public interface PermissaoRepository extends CrudRepository<Permissao, Integer>{

}
