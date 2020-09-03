package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Estoque;

public class EstoqueBuilder {
	
	private boolean principal;

	private String nomeEstoque;

	public static EstoqueBuilder getInstance() {
		return new EstoqueBuilder();
	}

	public EstoqueBuilder principal(boolean principal) {
		this.principal = principal;
		return this;
	}

	public EstoqueBuilder nomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
		return this;
	}

	public Estoque build() {
		Estoque estoque = new Estoque();
		estoque.setNomeEstoque(nomeEstoque);
		estoque.setPrincipal(principal);
		return estoque;
	}
	
}
