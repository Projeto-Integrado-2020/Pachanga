package com.eventmanager.pachanga.tipo;

public enum TipoGrupo {
	
	ORGANIZADOR("Organizador"),
	CONVIDADO("CONVIDADO");
	
	private String valor;
	
	private TipoGrupo(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
