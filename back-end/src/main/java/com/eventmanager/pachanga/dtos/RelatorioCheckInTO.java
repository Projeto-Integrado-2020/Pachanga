package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class RelatorioCheckInTO {
	
	private Map<Integer, Integer> quantitadeFaixaEtaria;//primeiro relacionado a idade e o outro relacionado a quantidade de pessoas com aquela idade
	
	private Map<String, Integer> quantidadeGenero;//o genero da pessoa seguido da quantidade de pessoas daquele genero
	
	private Map<String, Integer> quantidadePessoasHora;//primeiro a hora e a quantidade de pessoas que entrou
	
	private int quantidadeIngressosComprados;
	
	private int quantidadeIngressosEntradas;

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

	public int getQuantidadeIngressosComprados() {
		return quantidadeIngressosComprados;
	}

	public void setQuantidadeIngressosComprados(int quantidadeIngressosComprados) {
		this.quantidadeIngressosComprados = quantidadeIngressosComprados;
	}

	public int getQuantidadeIngressosEntradas() {
		return quantidadeIngressosEntradas;
	}

	public void setQuantidadeIngressosEntradas(int quantidadeIngressosEntradas) {
		this.quantidadeIngressosEntradas = quantidadeIngressosEntradas;
	}

}
