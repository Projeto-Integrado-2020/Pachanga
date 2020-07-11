package com.eventmanager.pachanga.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eventmanager.pachanga.domains.Grupo;

public interface GrupoRepository extends CrudRepository<Grupo, Integer>{
	
	public Grupo findById(int codGrupo);

}
