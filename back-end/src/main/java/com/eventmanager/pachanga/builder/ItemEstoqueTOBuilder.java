package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;

public class ItemEstoqueTOBuilder {
	
	private int codProduto;
	private int codEstoque;
	private int codFesta;
	private int quantidadeMax;
	private int quantiadadeAtual;
	private int porcentagemMin;
	
	public static ItemEstoqueTOBuilder getInstance() {
		return new ItemEstoqueTOBuilder();
	}

	public ItemEstoqueTOBuilder codProduto(int codProduto) {
		this.codProduto = codProduto;
		return this;
	}

	public ItemEstoqueTOBuilder codEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
		return this;
	}

	public ItemEstoqueTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public ItemEstoqueTOBuilder quantidadeMax(int quantidadeMax) {
		this.quantidadeMax = quantidadeMax;
		return this;
	}

	public ItemEstoqueTOBuilder quantiadadeAtual(int quantiadadeAtual) {
		this.quantiadadeAtual = quantiadadeAtual;
		return this;
	}

	public ItemEstoqueTOBuilder porcentagemMin(int porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
		return this;
	}
	
	public ItemEstoqueTO build() {
		ItemEstoqueTO itemEstoqueTO = new ItemEstoqueTO();
		itemEstoqueTO.setCodEstoque(codEstoque);
		itemEstoqueTO.setCodProduto(codProduto);
		itemEstoqueTO.setCodFesta(codFesta);
		itemEstoqueTO.setQuantidadeMax(quantidadeMax);
		itemEstoqueTO.setQuantiadadeAtual(quantiadadeAtual);
		itemEstoqueTO.setPorcentagemMin(porcentagemMin);
		
		return itemEstoqueTO;
	}
	
}
