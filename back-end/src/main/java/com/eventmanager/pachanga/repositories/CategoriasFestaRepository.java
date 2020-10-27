package com.eventmanager.pachanga.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.CategoriasFesta;

public interface CategoriasFestaRepository extends JpaRepository<CategoriasFesta, Integer>{
	
	@Modifying(clearAutomatically = true)
	@Query(value="INSERT INTO festa_x_categoria (cod_festa, cod_categoria, tip_categoria) VALUES(:codFesta, :codCategoria, :tipoCategoria)", nativeQuery = true)
	public void addCategoriasFesta(int codFesta, int codCategoria, String tipoCategoria);

	@Query(value = "SELECT cf FROM Categoria c JOIN c.categoriaFesta cf JOIN cf.festa f WHERE f.codFesta = :codFesta")
	public Set<CategoriasFesta> findCategoriasFesta(int codFesta);
	
	@Query(value = "SELECT cf FROM CategoriasFesta cf JOIN cf.festa f WHERE f.codFesta = :codFesta AND cf.tipCategoria = :tipoCategoria")
	public CategoriasFesta findCategoriasFestaTipoCategoria(int codFesta, String tipoCategoria);

}
