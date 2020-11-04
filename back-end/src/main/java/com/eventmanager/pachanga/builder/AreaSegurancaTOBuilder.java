package com.eventmanager.pachanga.builder;

import java.util.List;

import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.dtos.AreaSegurancaTO;

public class AreaSegurancaTOBuilder {
	
	private int codArea;

	private int codFesta;

	private String nomeArea;

	private String statusSeguranca;
	
	private List<AreaSegurancaProblemaTO> problemasArea;
	
	public static AreaSegurancaTOBuilder getInstance() {
		return new AreaSegurancaTOBuilder();
	}

	public AreaSegurancaTOBuilder codArea(int codArea) {
		this.codArea = codArea;
		return this;
	}

	public AreaSegurancaTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public AreaSegurancaTOBuilder nomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
		return this;
	}
	
	public AreaSegurancaTOBuilder problemasArea(List<AreaSegurancaProblemaTO> problemasArea) {
		this.problemasArea = problemasArea;
		return this;
	}

	public AreaSegurancaTOBuilder statusSeguranca(String statusSeguranca) {
		this.statusSeguranca = statusSeguranca;
		return this;
	}
	
	public AreaSegurancaTO build() {
		AreaSegurancaTO areaSegurancaTO = new AreaSegurancaTO();
		areaSegurancaTO.setCodArea(codArea);
		areaSegurancaTO.setCodFesta(codFesta);
		areaSegurancaTO.setNomeArea(nomeArea);
		areaSegurancaTO.setStatusSeguranca(statusSeguranca);
		areaSegurancaTO.setProblemasArea(problemasArea);
		return areaSegurancaTO;
	}
	
	

}
