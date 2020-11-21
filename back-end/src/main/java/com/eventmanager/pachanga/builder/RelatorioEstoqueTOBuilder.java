package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;
import java.util.Map;

import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;

public class RelatorioEstoqueTOBuilder {

	private String nomeEstoque;

	private String nomeProduto;

	private Map<LocalDateTime, Integer> quantidadeHora;
	
	public static RelatorioEstoqueTOBuilder getInstance() {
		return new RelatorioEstoqueTOBuilder();
	}

	public RelatorioEstoqueTOBuilder nomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
		return this;
	}

	public RelatorioEstoqueTOBuilder nomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
		return this;
	}

	public RelatorioEstoqueTOBuilder quantidadeHora(Map<LocalDateTime, Integer> quantidadeHora) {
		this.quantidadeHora = quantidadeHora;
		return this;
	}
	
	public RelatorioEstoqueTO build() {
		RelatorioEstoqueTO relatorioEstoque = new RelatorioEstoqueTO();
		relatorioEstoque.setNomeEstoque(nomeEstoque);
		relatorioEstoque.setNomeProduto(nomeProduto);
		relatorioEstoque.setQuantidadeHora(quantidadeHora);
		return relatorioEstoque;
	}

}
