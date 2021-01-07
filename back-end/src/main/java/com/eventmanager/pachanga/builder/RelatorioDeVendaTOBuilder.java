package com.eventmanager.pachanga.builder;

import java.util.Map;

import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;

public class RelatorioDeVendaTOBuilder {
	private Map<String, Integer> ingressosVendidos;
	
	private Map<String, Map<Integer, Integer>> ingressosCompradosPagos;
	
	public static RelatorioDeVendaTOBuilder getInstance() {
		return new RelatorioDeVendaTOBuilder();
	}
	
	public RelatorioDeVendaTOBuilder ingressosVendidos(Map<String, Integer> ingressosVendidos) {
		this.ingressosVendidos = ingressosVendidos;
		return this;
	}
	
	public RelatorioDeVendaTOBuilder ingressosCompradosPagos(Map<String, Map<Integer, Integer>> ingressosCompradosPagos) {
		this.ingressosCompradosPagos = ingressosCompradosPagos;
		return this;
	}
	
	public RelatorioDeVendaTO build() {
		RelatorioDeVendaTO relatorioDeVendaTO = new RelatorioDeVendaTO();
		relatorioDeVendaTO.setIngressos(ingressosVendidos);
		relatorioDeVendaTO.setIngressosCompradosPagos(ingressosCompradosPagos);
		return relatorioDeVendaTO;
	}
}
