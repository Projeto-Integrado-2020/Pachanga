package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class RelatorioDeVendaTO {
	private Map<String, Integer> ingressos;
	
	private Map<String, Map<Integer, Integer>> ingressosCompradosPagos;

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
	
	
}
