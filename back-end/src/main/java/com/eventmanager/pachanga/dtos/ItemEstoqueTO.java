package com.eventmanager.pachanga.dtos;

public class ItemEstoqueTO {
	private int codProduto;
	private int codEstoque;
	private int codFesta;
	private int quantidadeMax;
	private int quantidadeAtual;
	private int porcentagemMin;
	
	public int getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(int codProduto) {
		this.codProduto = codProduto;
	}
	public int getCodEstoque() {
		return codEstoque;
	}
	public void setCodEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
	}
	public int getCodFesta() {
		return codFesta;
	}
	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}
	public int getQuantidadeMax() {
		return quantidadeMax;
	}
	public void setQuantidadeMax(int quantidadeMax) {
		this.quantidadeMax = quantidadeMax;
	}
	public int getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(int quantiadadeAtual) {
		this.quantidadeAtual = quantiadadeAtual;
	}
	public int getPorcentagemMin() {
		return porcentagemMin;
	}
	public void setPorcentagemMin(int porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
	}
	
	
	
	

}
