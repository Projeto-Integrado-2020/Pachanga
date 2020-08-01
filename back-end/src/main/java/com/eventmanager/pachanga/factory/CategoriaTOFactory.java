package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.CategoriaTOBuilder;
import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.dtos.CategoriaTO;

public class CategoriaTOFactory {
	
	private CategoriaTOFactory(){
	}
	
	public static CategoriaTO getCategoriaTo(Categoria categoria) {
		return CategoriaTOBuilder.getInstance().codCategoria(categoria.getCodCategoria()).nomeCategoria(categoria.getNomeCategoria()).build();
	}

}
