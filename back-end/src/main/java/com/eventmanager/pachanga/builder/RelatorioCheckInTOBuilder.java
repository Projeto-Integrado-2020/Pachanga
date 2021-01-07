package com.eventmanager.pachanga.builder;

import java.util.Map;

import com.eventmanager.pachanga.dtos.RelatorioCheckInTO;

public class RelatorioCheckInTOBuilder {

	private Map<Integer, Integer> quantitadeFaixaEtaria;// primeiro relacionado a idade e o outro relacionado a
														// quantidade de pessoas com aquela idade

	private Map<String, Integer> quantidadeGenero;// o genero da pessoa seguido da quantidade de pessoas daquele genero

	private Map<String, Integer> quantidadePessoasHora;// primeiro a hora e a quantidade de pessoas que entrou
	
	private Map<String, Map<Integer, Integer>> ingressosLoteFesta;// nomeLote, Quantidade total ingressos e quantidade total entrada
	
	private Map<String, Map<Integer, Integer>> ingressoFestaCheckedUnchecked;

	public static RelatorioCheckInTOBuilder getInstance() {
		return new RelatorioCheckInTOBuilder();
	}
	
	public RelatorioCheckInTOBuilder quantidadeFaixaEtaria(Map<Integer, Integer> quantitadeFaixaEtaria) {
		this.quantitadeFaixaEtaria = quantitadeFaixaEtaria;
		return this;
	}
	
	public RelatorioCheckInTOBuilder quantidadeGenero(Map<String, Integer> quantidadeGenero) {
		this.quantidadeGenero = quantidadeGenero;
		return this;
	}
	
	public RelatorioCheckInTOBuilder quantidadePessoasHora(Map<String, Integer> quantidadePessoasHora) {
		this.quantidadePessoasHora = quantidadePessoasHora;
		return this;
	}
	
	public RelatorioCheckInTOBuilder ingressosLoteFesta(Map<String, Map<Integer, Integer>> ingressosLoteFesta) {
		this.ingressosLoteFesta = ingressosLoteFesta;
		return this;
	}
	
	public RelatorioCheckInTOBuilder ingressoFestaCheckedUnchecked(Map<String, Map<Integer, Integer>> ingressoFestaCheckedUnchecked) {
		this.ingressoFestaCheckedUnchecked = ingressoFestaCheckedUnchecked;
		return this;
	}
	
	public RelatorioCheckInTO build() {
		RelatorioCheckInTO relatorio = new RelatorioCheckInTO();
		relatorio.setQuantidadeGenero(quantidadeGenero);
		relatorio.setQuantidadePessoasHora(quantidadePessoasHora);
		relatorio.setIngressosLoteFesta(ingressosLoteFesta);
		relatorio.setQuantitadeFaixaEtaria(quantitadeFaixaEtaria);
		relatorio.setIngressoFestaCheckedUnchecked(ingressoFestaCheckedUnchecked);
		return relatorio;
	}

}
