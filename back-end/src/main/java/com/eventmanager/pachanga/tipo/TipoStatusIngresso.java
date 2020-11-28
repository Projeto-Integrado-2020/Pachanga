package com.eventmanager.pachanga.tipo;

public enum TipoStatusIngresso {
	
	CHECKED("C"),
	UNCHECKED("U");
	
	private String descricao;

	private TipoStatusIngresso(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
