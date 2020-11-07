package com.eventmanager.pachanga.dtos;

public class InfoIntegracaoTO {

	private int codInfo;

	private String terceiroInt;

	private String token;

	private int codFesta;
	
	private int codEvent;

	public int getCodInfo() {
		return codInfo;
	}

	public void setCodInfo(int codInfo) {
		this.codInfo = codInfo;
	}

	public String getTerceiroInt() {
		return terceiroInt;
	}

	public void setTerceiroInt(String terceiroInt) {
		this.terceiroInt = terceiroInt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}

	public int getCodEvent() {
		return codEvent;
	}

	public void setCodEvent(int codEvent) {
		this.codEvent = codEvent;
	}

}
