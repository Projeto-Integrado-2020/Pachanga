package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.InfoIntegracao;

public class InfoIntegracaoBuilder {
	
	private int codInfo;

	private String terceiroInt;

	private String token;

	private Festa festa;
	
	private String codEvent;
	
	public static InfoIntegracaoBuilder getInstance() {
		return new InfoIntegracaoBuilder();
	}

	public InfoIntegracaoBuilder codInfo(int codInfo) {
		this.codInfo = codInfo;
		return this;
	}

	public InfoIntegracaoBuilder terceiroInt(String terceiroInt) {
		this.terceiroInt = terceiroInt;
		return this;
	}

	public InfoIntegracaoBuilder token(String token) {
		this.token = token;
		return this;
	}
	
	public InfoIntegracaoBuilder codEvent(String codEvent) {
		this.codEvent = codEvent;
		return this;
	}

	public InfoIntegracaoBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}
	
	public InfoIntegracao build() {
		InfoIntegracao info = new InfoIntegracao();
		info.setCodInfo(codInfo);
		info.setFesta(festa);
		info.setTerceiroInt(terceiroInt);
		info.setToken(token);
		info.setCodEvent(codEvent);
		return info;
	}
	
}
