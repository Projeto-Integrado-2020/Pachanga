package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class RelatorioCheckInTO {
	
	private Map<Integer, Integer> quantitadeFaixaEtaria;//primeiro relacionado a idade e o outro relacionado a quantidade de pessoas com aquela idade
	
	private Map<String, Integer> quantidadeGenero;//o genero da pessoa seguido da quantidade de pessoas daquele genero
	
	private Map<String, Integer> quantidadePessoasHora;//primeiro a hora e a quantidade de pessoas que entrou
	
	private Map<String, Map<Integer, Integer>> ingressosLoteFesta;// nomeLote, Quantidade total ingressos e quantidade total entrada
	
	private Map<String, Map<Integer, Integer>> ingressoFestaCheckedUnchecked;// primeiro checked depois unchecked
	
	public Map<Integer, Integer> getQuantitadeFaixaEtaria() {
		return quantitadeFaixaEtaria;
	}

	public void setQuantitadeFaixaEtaria(Map<Integer, Integer> quantitadeFaixaEtaria) {
		this.quantitadeFaixaEtaria = quantitadeFaixaEtaria;
	}

	public Map<String, Integer> getQuantidadeGenero() {
		return quantidadeGenero;
	}

	public void setQuantidadeGenero(Map<String, Integer> quantidadeGenero) {
		this.quantidadeGenero = quantidadeGenero;
	}

	public Map<String, Integer> getQuantidadePessoasHora() {
		return quantidadePessoasHora;
	}

	public void setQuantidadePessoasHora(Map<String, Integer> quantidadePessoasHora) {
		this.quantidadePessoasHora = quantidadePessoasHora;
	}

	public Map<String, Map<Integer, Integer>> getIngressosLoteFesta() {
		return ingressosLoteFesta;
	}

	public void setIngressosLoteFesta(Map<String, Map<Integer, Integer>> ingressosLoteFesta) {
		this.ingressosLoteFesta = ingressosLoteFesta;
	}

	public Map<String, Map<Integer, Integer>> getIngressoFestaCheckedUnchecked() {
		return ingressoFestaCheckedUnchecked;
	}

	public void setIngressoFestaCheckedUnchecked(Map<String, Map<Integer, Integer>> ingressoFestaCheckedUnchecked) {
		this.ingressoFestaCheckedUnchecked = ingressoFestaCheckedUnchecked;
	}
}
