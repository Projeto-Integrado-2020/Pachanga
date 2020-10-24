package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.AreaSeguranca;

public class AreaSegurancaBuilder {
	
	private int codArea;

	private int codFesta;

	private String nomeArea;

	private String statusSeguranca;
	
	public static AreaSegurancaBuilder getInstance() {
		return new AreaSegurancaBuilder();
	}

	public AreaSegurancaBuilder codArea(int codArea) {
		this.codArea = codArea;
		return this;
	}

	public AreaSegurancaBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public AreaSegurancaBuilder nomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
		return this;
	}

	public AreaSegurancaBuilder statusSeguranca(String statusSeguranca) {
		this.statusSeguranca = statusSeguranca;
		return this;
	}
	
	public AreaSeguranca build() {
		AreaSeguranca areaSeguranca = new AreaSeguranca();
		areaSeguranca.setCodArea(codArea);
		areaSeguranca.setCodFesta(codFesta);
		areaSeguranca.setNomeArea(nomeArea);
		areaSeguranca.setStatusSeguranca(statusSeguranca);
		return areaSeguranca;
	}

}
