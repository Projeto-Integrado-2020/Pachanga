package com.eventmanager.pachanga.tipo;

public enum TipoStatusPoblema {
	
	ANDAMENTO("A"), 
	FINALIZADO("F"), 
	ENGANO("E");
	
	private String valor;

	private TipoStatusPoblema(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
