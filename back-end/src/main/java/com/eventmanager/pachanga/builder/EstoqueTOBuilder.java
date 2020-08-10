package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.EstoqueTO;

public class EstoqueTOBuilder {

	private int codEstoque;

	private boolean principal;

	private String nomeEstoque;

	public static EstoqueTOBuilder getInstance() {
		return new EstoqueTOBuilder();
	}


	public EstoqueTOBuilder codEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
		return this;
	}

	public EstoqueTOBuilder principal(boolean principal) {
		this.principal = principal;
		return this;
	}

	public EstoqueTOBuilder nomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
		return this;
	}

	public EstoqueTO build() {
		EstoqueTO estoqueTo = new EstoqueTO();
		estoqueTo.setCodEstoque(codEstoque);
		estoqueTo.setNomeEstoque(nomeEstoque);
		estoqueTo.setPrincipal(principal);
		return estoqueTo;
	}
}
