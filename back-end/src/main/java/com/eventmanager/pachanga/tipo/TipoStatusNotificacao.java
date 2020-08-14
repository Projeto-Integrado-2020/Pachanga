package com.eventmanager.pachanga.tipo;

public enum TipoStatusNotificacao {
	LIDA("L"),
	NAOLIDA("N");
	
	private String descricao;
	
	private TipoStatusNotificacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
