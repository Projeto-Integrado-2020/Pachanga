package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class RelatorioDeVendaTO {
	private Map<String, Integer> ingressos;

	private Map<String, Map<Integer, Integer>> ingressosCompradosPagos;

	private String nomeFesta;
	
	private InfoLucroFesta infoLucroEsperado;
	
	private InfoLucroFesta infoLucroReal;
	
	public Map<String, Integer> getIngressos() {
		return ingressos;
	}

	public Map<String, Map<Integer, Integer>> getIngressosCompradosPagos() {
		return ingressosCompradosPagos;
	}

	public void setIngressosCompradosPagos(Map<String, Map<Integer, Integer>> ingressosCompradosPagos) {
		this.ingressosCompradosPagos = ingressosCompradosPagos;
	}

	public void setIngressos(Map<String, Integer> ingressos) {
		this.ingressos = ingressos;
	}

	public String getNomeFesta() {
		return nomeFesta;
	}

	public void setNomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
	}

	public InfoLucroFesta getInfoLucroEsperado() {
		return infoLucroEsperado;
	}

	public void setInfoLucroEsperado(InfoLucroFesta infoLucroEsperado) {
		this.infoLucroEsperado = infoLucroEsperado;
	}

	public InfoLucroFesta getInfoLucroReal() {
		return infoLucroReal;
	}

	public void setInfoLucroReal(InfoLucroFesta infoLucroReal) {
		this.infoLucroReal = infoLucroReal;
	}
}
