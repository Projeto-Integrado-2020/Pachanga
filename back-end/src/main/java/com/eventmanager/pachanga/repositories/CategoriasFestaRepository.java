package com.eventmanager.pachanga.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventmanager.pachanga.domains.CategoriasFesta;

public interface CategoriasFestaRepository extends JpaRepository<CategoriasFesta, Integer>{

	@Modifying(clearAutomatically = true)
	@Query(value="INSERT INTO festa_x_categoria (cod_festa, cod_categoria, tip_categoria) VALUES(:codFesta, :codCategoria, CAST( :tipoCategoria AS tip_categoria_t))", nativeQuery = true)
	public void addCategoriasFesta(int codFesta, int codCategoria, @Param("tipoCategoria") String tipoCategoria);

	@Query(value = "SELECT cf FROM Categoria c JOIN c.categoriaFesta cf JOIN cf.festa f WHERE f.codFesta = :codFesta")
	public Set<CategoriasFesta> findCategoriasFesta(int codFesta);
	
	@Query(value = "SELECT cf.* FROM festa_x_categoria cf WHERE cf.cod_festa = :codFesta AND cf.tip_categoria = CAST(:tipoCategoria AS tip_categoria_t)", nativeQuery = true)
	public CategoriasFesta findCategoriasFestaTipoCategoria(int codFesta, String tipoCategoria);

}
