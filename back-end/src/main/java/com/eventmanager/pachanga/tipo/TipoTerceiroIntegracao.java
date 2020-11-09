package com.eventmanager.pachanga.tipo;

public enum TipoTerceiroIntegracao {
	
	SYMPLA("S"),
	EVENTIBRITE("E");
	
	
	private String valor;

	private TipoTerceiroIntegracao(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	

}
