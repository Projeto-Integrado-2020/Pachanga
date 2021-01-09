package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;
import java.util.Map;

import com.eventmanager.pachanga.dtos.InformacoesRelatorioEstoqueTO;

public class InformacoesRelatorioEstoqueTOBuilder {
	
	private String nomeProduto;

	private Map<LocalDateTime, Integer> quantidadeHora;
	
	private Integer quantidadeConsumo;
	
	public static InformacoesRelatorioEstoqueTOBuilder getInstance() {
		return new InformacoesRelatorioEstoqueTOBuilder();
	}
	
	public InformacoesRelatorioEstoqueTOBuilder nomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
		return this;
	}
	
	public InformacoesRelatorioEstoqueTOBuilder quantidadeConsumo(Integer quantidadeConsumo) {
		this.quantidadeConsumo = quantidadeConsumo;
		return this;
	}

	public InformacoesRelatorioEstoqueTOBuilder quantidadeHora(Map<LocalDateTime, Integer> quantidadeHora) {
		this.quantidadeHora = quantidadeHora;
		return this;
	}
	
	public InformacoesRelatorioEstoqueTO build() {
		InformacoesRelatorioEstoqueTO informacoesRelatorio= new InformacoesRelatorioEstoqueTO();
		informacoesRelatorio.setNomeProduto(nomeProduto);
		informacoesRelatorio.setQuantidadeHora(quantidadeHora);
		informacoesRelatorio.setQuantidadeConsumo(quantidadeConsumo);
		return informacoesRelatorio;
	}

}
