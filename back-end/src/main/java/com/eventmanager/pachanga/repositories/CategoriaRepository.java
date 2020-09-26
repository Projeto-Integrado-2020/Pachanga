package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
	@Query(value = "select categoria0_.cod_categoria as cod_categoria, categoria0_.nome_categoria as nome_categoria from categoria categoria0_ inner join festa_x_categoria categoriaf1_ on categoria0_.cod_categoria=categoriaf1_.cod_categoria inner join festa festa2_ on categoriaf1_.cod_festa=festa2_.cod_festa where festa2_.cod_festa=:codFesta and categoriaf1_.tip_categoria = CAST(:tipoCategoria AS tip_categoria_t)", nativeQuery = true)
	public Categoria findCategoriaFesta(int codFesta, String tipoCategoria);
	
	public Categoria findByCodCategoria(int codCategoria);

}
