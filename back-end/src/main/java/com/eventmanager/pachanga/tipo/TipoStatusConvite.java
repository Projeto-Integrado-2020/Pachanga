package com.eventmanager.pachanga.tipo;

public enum TipoStatusConvite {
	
	RECUSADO("R"),
	PENDENTE("P");
	
	private String descricao;

	private TipoStatusConvite(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
