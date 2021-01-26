package com.eventmanager.pachanga.dtos;

import java.util.List;
import java.util.Map;

public class RelatorioAreaSegurancaTO {
	
	private Map<String, Integer> problemasArea;
	
	private List<ChamadasEmitidasFuncionarioTO> chamadasEmitidasFuncionario;
	
	private Map<String, Integer> solucionadorAlertasSeguranca;

	public Map<String, Integer> getProblemasArea() {
		return problemasArea;
	}

	public void setProblemasArea(Map<String, Integer> problemasArea) {
		this.problemasArea = problemasArea;
	}

	public Map<String, Integer> getSolucionadorAlertasSeguranca() {
		return solucionadorAlertasSeguranca;
	}

	public void setSolucionadorAlertasSeguranca(Map<String, Integer> solucionadorAlertasSeguranca) {
		this.solucionadorAlertasSeguranca = solucionadorAlertasSeguranca;
	}

	public List<ChamadasEmitidasFuncionarioTO> getChamadasEmitidasFuncionario() {
		return chamadasEmitidasFuncionario;
	}

	public void setChamadasEmitidasFuncionario(List<ChamadasEmitidasFuncionarioTO> chamadasEmitidasFuncionario) {
		this.chamadasEmitidasFuncionario = chamadasEmitidasFuncionario;
	}
	
}
