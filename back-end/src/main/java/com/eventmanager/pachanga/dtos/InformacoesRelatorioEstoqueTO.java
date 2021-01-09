package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public class InformacoesRelatorioEstoqueTO {

	private String nomeProduto;

	private Map<LocalDateTime, Integer> quantidadeHora;
	
	private Integer quantidadeConsumo;
	
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

	public Integer getQuantidadeConsumo() {
		return quantidadeConsumo;
	}

	public void setQuantidadeConsumo(Integer quantidadeConsumo) {
		this.quantidadeConsumo = quantidadeConsumo;
	}
}
