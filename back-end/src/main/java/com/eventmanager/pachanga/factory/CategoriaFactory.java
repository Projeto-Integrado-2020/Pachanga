package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.CategoriaTOBuilder;
import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.dtos.CategoriaTO;

public class CategoriaFactory {
	
	private CategoriaFactory() {
	}

	public static CategoriaTO getCategoriaTO(Categoria categoria) {
		return CategoriaTOBuilder.getInstance().codCategoria(categoria == null ? null : categoria.getCodCategoria())
				.nomeCategoria(categoria == null ? null : categoria.getNomeCategoria()).build();
	}
}
