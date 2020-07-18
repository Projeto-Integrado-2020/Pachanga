package com.eventmanager.pachanga.tipo;

public enum TipoConta {
	PACHANGA("P"),
	GMAIL("G"),
	FACEBOOK("F");
	
	private String descricao;
	
	private TipoConta(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
