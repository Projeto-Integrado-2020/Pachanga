package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.ProdutoTO;

public class ItemEstoqueTOBuilder {
	
	private int codProduto;
	private int codEstoque;
	private int codFesta;
	private int quantidadeMax;
	private int quantidadeAtual;
	private int porcentagemMin;
	private ProdutoTO produto; 
	
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
	
	public ItemEstoqueTOBuilder produto(ProdutoTO produto) {
		this.produto = produto;
		return this;
	}

	public ItemEstoqueTOBuilder quantidadeMax(int quantidadeMax) {
		this.quantidadeMax = quantidadeMax;
		return this;
	}

	public ItemEstoqueTOBuilder quantidadeAtual(int quantiadadeAtual) {
		this.quantidadeAtual = quantiadadeAtual;
		return this;
	}

	public ItemEstoqueTOBuilder porcentagemMin(int porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
		return this;
	}
	
	public ItemEstoqueTO build() {
		ItemEstoqueTO itemEstoqueTO = new ItemEstoqueTO();
		itemEstoqueTO.setProduto(produto);
		itemEstoqueTO.setCodEstoque(codEstoque);
		itemEstoqueTO.setCodProduto(codProduto);
		itemEstoqueTO.setCodFesta(codFesta);
		itemEstoqueTO.setQuantidadeMax(quantidadeMax);
		itemEstoqueTO.setQuantidadeAtual(quantidadeAtual);
		itemEstoqueTO.setPorcentagemMin(porcentagemMin);
		
		return itemEstoqueTO;
	}
	
}
