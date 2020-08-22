package com.eventmanager.pachanga.tipo;

public enum TipoNotificacao {
	
	CONVFEST(1, "CONVFEST"),
	CONVACEI(2, "CONVACEI"),
	ESTBAIXO(3, "ESTBAIXO");
	
	private String valor;
	private int codigo;

	private TipoNotificacao(int codigo, String valor) {
		this.valor = valor;
		this.codigo = codigo;
	}

	public String getValor() {
		return valor;
	}
	
	public int getCodigo() {
		return codigo;
	}

}
