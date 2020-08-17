package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.ItemEstoque;

public class ItemEstoqueBuilder {
	private int codProduto;
	private int codEstoque;
	private int codFesta;
	private int quantidadeMax;
	private int quantiadadeAtual;
	private int porcentagemMin;
	
	public static ItemEstoqueBuilder getInstance() {
		return new ItemEstoqueBuilder();
	}

	public ItemEstoqueBuilder codProduto(int codProduto) {
		this.codProduto = codProduto;
		return this;
	}

	public ItemEstoqueBuilder codEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
		return this;
	}

	public ItemEstoqueBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public ItemEstoqueBuilder quantidadeMax(int quantidadeMax) {
		this.quantidadeMax = quantidadeMax;
		return this;
	}

	public ItemEstoqueBuilder quantiadadeAtual(int quantiadadeAtual) {
		this.quantiadadeAtual = quantiadadeAtual;
		return this;
	}

	public ItemEstoqueBuilder porcentagemMin(int porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
		return this;
	}
	
	public ItemEstoque build() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodEstoque(codEstoque);
		itemEstoque.setCodProduto(codProduto);
		itemEstoque.setCodFesta(codFesta);
		itemEstoque.setQuantidadeMax(quantidadeMax);
		itemEstoque.setQuantiadadeAtual(quantiadadeAtual);
		itemEstoque.setPorcentagemMin(porcentagemMin);
		
		return itemEstoque;
	}
	
}
