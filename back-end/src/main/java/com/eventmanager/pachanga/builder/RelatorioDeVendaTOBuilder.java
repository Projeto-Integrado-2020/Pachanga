package com.eventmanager.pachanga.builder;

import java.util.Map;

import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;

public class RelatorioDeVendaTOBuilder {
	private Map<String, Integer> ingressosVendidos;
	
	public static RelatorioDeVendaTOBuilder getInstance() {
		return new RelatorioDeVendaTOBuilder();
	}
	
	public RelatorioDeVendaTOBuilder ingressosVendidos(Map<String, Integer> ingressosVendidos) {
		this.ingressosVendidos = ingressosVendidos;
		return this;
	}
	
	public RelatorioDeVendaTO build() {
		RelatorioDeVendaTO relatorioDeVendaTO = new RelatorioDeVendaTO();
		relatorioDeVendaTO.setIngressosVendidos(ingressosVendidos);
		return relatorioDeVendaTO;
	}
}
