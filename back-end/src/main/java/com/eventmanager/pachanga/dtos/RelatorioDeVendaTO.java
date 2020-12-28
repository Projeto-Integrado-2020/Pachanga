package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class RelatorioDeVendaTO {
	private Map<String, Integer> ingressos;

	public Map<String, Integer> getIngressos() {
		return ingressos;
	}

	public void setIngressosVendidos(Map<String, Integer> ingressos) {
		this.ingressos = ingressos;
	}
	
	
}
