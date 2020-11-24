package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public class RelatorioEstoqueTO {
	
	private String nomeEstoque;
	
	private String nomeProduto;
	
	private Map<LocalDateTime, Integer> quantidadeHora;

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

	public Map<LocalDateTime, Integer> getQuantidadeHora() {
		return quantidadeHora;
	}

	public void setQuantidadeHora(Map<LocalDateTime, Integer> quantidadeHora) {
		this.quantidadeHora = quantidadeHora;
	}

}
