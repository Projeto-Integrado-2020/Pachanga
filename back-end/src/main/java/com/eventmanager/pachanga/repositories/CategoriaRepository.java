package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
	@Query(value = "SELECT c FROM Categoria c JOIN c.categoriaFesta cf JOIN cf.festa f WHERE f.codFesta = :codFesta AND cf.tipCategoria = :tipoCategoria")
	public Categoria findCategoriaFesta(int codFesta, String tipoCategoria);
	
	public Categoria findByCodCategoria(int codCategoria);

}
