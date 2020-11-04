package com.eventmanager.pachanga.dtos;

import java.util.List;

public class AreaSegurancaTO {

	private int codArea;

	private int codFesta;

	private String nomeArea;

	private String statusSeguranca;
	
	private List<AreaSegurancaProblemaTO> problemasArea;

	public int getCodArea() {
		return codArea;
	}

	public void setCodArea(int codArea) {
		this.codArea = codArea;
	}

	public int getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}

	public String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public String getStatusSeguranca() {
		return statusSeguranca;
	}

	public void setStatusSeguranca(String statusSeguranca) {
		this.statusSeguranca = statusSeguranca;
	}

	public List<AreaSegurancaProblemaTO> getProblemasArea() {
		return problemasArea;
	}

	public void setProblemasArea(List<AreaSegurancaProblemaTO> problemasArea) {
		this.problemasArea = problemasArea;
	}

}
