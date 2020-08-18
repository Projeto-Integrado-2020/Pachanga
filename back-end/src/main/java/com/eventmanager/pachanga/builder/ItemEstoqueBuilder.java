package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;

public class ItemEstoqueBuilder {
	private Produto produto;
	private Estoque estoque;
//	private int codFesta;
	private int quantidadeMax;
	private int quantidadeAtual;
	private int porcentagemMin;
	
	public static ItemEstoqueBuilder getInstance() {
		return new ItemEstoqueBuilder();
	}

	public ItemEstoqueBuilder codProduto(Produto produto) {
		this.produto = produto;
		return this;
	}

	public ItemEstoqueBuilder codEstoque(Estoque estoque) {
		this.estoque = estoque;
		return this;
	}

//	public ItemEstoqueBuilder codFesta(int codFesta) {
//		this.codFesta = codFesta;
//		return this;
//	}

	public ItemEstoqueBuilder quantidadeMax(int quantidadeMax) {
		this.quantidadeMax = quantidadeMax;
		return this;
	}

	public ItemEstoqueBuilder quantidadeAtual(int quantiadadeAtual) {
		this.quantidadeAtual = quantiadadeAtual;
		return this;
	}

	public ItemEstoqueBuilder porcentagemMin(int porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
		return this;
	}
	
	public ItemEstoque build() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setEstoque(estoque);
		itemEstoque.setProduto(produto);
//		itemEstoque.setCodFesta(codFesta);
		itemEstoque.setQuantidadeMax(quantidadeMax);
		itemEstoque.setQuantidadeAtual(quantidadeAtual);
		itemEstoque.setPorcentagemMin(porcentagemMin);
		
		return itemEstoque;
	}
	
}
