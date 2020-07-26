package com.eventmanager.pachanga.tipo;

public enum TipoStatusFesta {

	PREPARACAO("P"),
	INICIADO("I"),
	FINALIZADO("F");

	private String valor;

	private TipoStatusFesta(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
