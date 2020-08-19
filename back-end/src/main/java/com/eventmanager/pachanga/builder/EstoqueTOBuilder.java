package com.eventmanager.pachanga.builder;

import java.util.List;

import com.eventmanager.pachanga.dtos.EstoqueTO;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;

public class EstoqueTOBuilder {

	private int codEstoque;

	private boolean principal;

	private String nomeEstoque;
	
	private List<ItemEstoqueTO> itemEstoque;

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
	
	public EstoqueTOBuilder itensEstoque(List<ItemEstoqueTO> itemEstoque) {
		this.itemEstoque = itemEstoque;
		return this;
	}

	public EstoqueTOBuilder nomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
		return this;
	}

	public EstoqueTO build() {
		EstoqueTO estoqueTo = new EstoqueTO();
		estoqueTo.setItemEstoque(itemEstoque);
		estoqueTo.setCodEstoque(codEstoque);
		estoqueTo.setNomeEstoque(nomeEstoque);
		estoqueTo.setPrincipal(principal);
		return estoqueTo;
	}
}
