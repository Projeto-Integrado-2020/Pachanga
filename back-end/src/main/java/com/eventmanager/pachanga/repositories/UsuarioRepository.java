package com.eventmanager.pachanga.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eventmanager.pachanga.domains.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
}
