package com.eventmanager.pachanga.tipo;

public enum TipoStatusProblema {
	
	ANDAMENTO("A"), 
	FINALIZADO("F"), 
	ENGANO("E");
	
	private String valor;

	private TipoStatusProblema(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
