package com.eventmanager.pachanga.builder;

import java.util.Map;

import com.eventmanager.pachanga.dtos.InfoLucroFesta;
import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;

public class RelatorioDeVendaTOBuilder {
	private Map<String, Integer> ingressosVendidos;

	private Map<String, Map<Integer, Integer>> ingressosCompradosPagos;

	private String nomeFesta;

	private InfoLucroFesta infoLucroEsperado;

	private InfoLucroFesta infoLucroReal;

	public static RelatorioDeVendaTOBuilder getInstance() {
		return new RelatorioDeVendaTOBuilder();
	}

	public RelatorioDeVendaTOBuilder ingressosVendidos(Map<String, Integer> ingressosVendidos) {
		this.ingressosVendidos = ingressosVendidos;
		return this;
	}

	public RelatorioDeVendaTOBuilder ingressosCompradosPagos(
			Map<String, Map<Integer, Integer>> ingressosCompradosPagos) {
		this.ingressosCompradosPagos = ingressosCompradosPagos;
		return this;
	}

	public RelatorioDeVendaTOBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}

	public RelatorioDeVendaTOBuilder infoLucroReal(InfoLucroFesta infoLucroReal) {
		this.infoLucroReal = infoLucroReal;
		return this;
	}

	public RelatorioDeVendaTOBuilder infoLucroEsperado(InfoLucroFesta infoLucroEsperado) {
		this.infoLucroEsperado = infoLucroEsperado;
		return this;
	}

	public RelatorioDeVendaTO build() {
		RelatorioDeVendaTO relatorioDeVendaTO = new RelatorioDeVendaTO();
		relatorioDeVendaTO.setIngressos(ingressosVendidos);
		relatorioDeVendaTO.setIngressosCompradosPagos(ingressosCompradosPagos);
		relatorioDeVendaTO.setInfoLucroEsperado(infoLucroEsperado);
		relatorioDeVendaTO.setInfoLucroReal(infoLucroReal);
		relatorioDeVendaTO.setNomeFesta(nomeFesta);
		return relatorioDeVendaTO;
	}
}
