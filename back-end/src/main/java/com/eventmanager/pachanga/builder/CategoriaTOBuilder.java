package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.CategoriaTO;

public class CategoriaTOBuilder {

	private Integer codCategoria;
	private String nomeCategoria;
	
	public static CategoriaTOBuilder getInstance() {
		return new CategoriaTOBuilder();
	}

	public CategoriaTOBuilder codCategoria(Integer codCategoria) {
		this.codCategoria = codCategoria;
		return this;
	}

	public CategoriaTOBuilder nomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
		return this;
	}
	
	public CategoriaTO build() {
		CategoriaTO categoriaTo = new CategoriaTO();
		categoriaTo.setCodCategoria(codCategoria);
		categoriaTo.setNomeCategoria(nomeCategoria);
		return categoriaTo;
	}
}
