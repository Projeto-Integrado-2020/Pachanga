package com.eventmanager.pachanga.tipo;

public enum TipoNotificacao {
	
	CONVFEST(1, "CONVFEST"),// Notificacao de convite de festa
	CONVACEI(2, "CONVACEI"),// Convidado aceitou a festa (notificação para o admin)
	ESTBAIXO(3, "ESTBAIXO"),// Estoque abaixo do porcentual mínimo
	STAALTER(4, "STAALTER");// Status da festa foi alterado
	
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
