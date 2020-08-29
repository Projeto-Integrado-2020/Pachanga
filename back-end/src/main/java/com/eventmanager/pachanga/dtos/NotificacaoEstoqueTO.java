package com.eventmanager.pachanga.dtos;

public class NotificacaoEstoqueTO {

	private String nomeEstoque;
	
	private String nomeProduto;
	
	private String nomeFesta;
	
	private Integer quantAtual;

	public String getNomeEstoque() {
		return nomeEstoque;
	}

	public void setNomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getNomeFesta() {
		return nomeFesta;
	}

	public void setNomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
	}

	public Integer getQuantAtual() {
		return quantAtual;
	}

	public void setQuantAtual(Integer quantAtual) {
		this.quantAtual = quantAtual;
	}
}
