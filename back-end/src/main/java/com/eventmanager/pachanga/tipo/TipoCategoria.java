package com.eventmanager.pachanga.tipo;

public enum TipoCategoria {

	PRIMARIO("P"),
	SECUNDARIO("S");

	private String descricao;

	private TipoCategoria(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
