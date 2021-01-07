package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class RelatorioAreaSegurancaTO {
	
	private Map<String, Integer> problemasArea;
	
	private Map<String, Map<Integer, Integer>> chamadasEmitidasFuncionario; // int primeiro é o finalizado depois o engano
	
	private Map<String, Integer> solucionadorAlertasSeguranca;

	public Map<String, Integer> getProblemasArea() {
		return problemasArea;
	}

	public void setProblemasArea(Map<String, Integer> problemasArea) {
		this.problemasArea = problemasArea;
	}

	public Map<String, Map<Integer, Integer>> getChamadasEmitidasFuncionario() {
		return chamadasEmitidasFuncionario;
	}

	public void setChamadasEmitidasFuncionario(Map<String, Map<Integer, Integer>> chamadasEmitidasFuncionario) {
		this.chamadasEmitidasFuncionario = chamadasEmitidasFuncionario;
	}

	public Map<String, Integer> getSolucionadorAlertasSeguranca() {
		return solucionadorAlertasSeguranca;
	}

	public void setSolucionadorAlertasSeguranca(Map<String, Integer> solucionadorAlertasSeguranca) {
		this.solucionadorAlertasSeguranca = solucionadorAlertasSeguranca;
	}

}
