package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class ItemEstoqueId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int produto;
	
	private int estoque;

	public ItemEstoqueId(int produto, int estoque) {
		this.produto = produto;
		this.estoque = estoque;
	}

	public ItemEstoqueId() {
	}

	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	
}
