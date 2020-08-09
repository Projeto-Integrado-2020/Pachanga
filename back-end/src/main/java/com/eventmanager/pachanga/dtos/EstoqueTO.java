package com.eventmanager.pachanga.dtos;

public class EstoqueTO {

	private int codEstoque;

	private boolean principal;

	private String nomeEstoque;

	public int getCodEstoque() {
		return codEstoque;
	}

	public void setCodEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getNomeEstoque() {
		return nomeEstoque;
	}

	public void setNomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
	}

}
