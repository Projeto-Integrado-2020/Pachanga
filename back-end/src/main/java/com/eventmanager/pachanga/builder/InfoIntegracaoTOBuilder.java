package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.InfoIntegracaoTO;

public class InfoIntegracaoTOBuilder {
	
	private int codInfo;

	private String terceiroInt;

	private String token;

	private int codfesta;
	
	private int codEvent;
	
	public static InfoIntegracaoTOBuilder getInstance() {
		return new InfoIntegracaoTOBuilder();
	}

	public InfoIntegracaoTOBuilder codInfo(int codInfo) {
		this.codInfo = codInfo;
		return this;
	}

	public InfoIntegracaoTOBuilder terceiroInt(String terceiroInt) {
		this.terceiroInt = terceiroInt;
		return this;
	}

	public InfoIntegracaoTOBuilder token(String token) {
		this.token = token;
		return this;
	}

	public InfoIntegracaoTOBuilder codFesta(int codfesta) {
		this.codfesta = codfesta;
		return this;
	}
	
	public InfoIntegracaoTOBuilder codEvent(int codEvent) {
		this.codEvent = codEvent;
		return this;
	}
	
	public InfoIntegracaoTO build() {
		InfoIntegracaoTO infoTo = new InfoIntegracaoTO();
		infoTo.setCodInfo(codInfo);
		infoTo.setCodFesta(codfesta);
		infoTo.setTerceiroInt(terceiroInt);
		infoTo.setToken(token);
		infoTo.setCodEvent(codEvent);
		return infoTo;
	}

}
