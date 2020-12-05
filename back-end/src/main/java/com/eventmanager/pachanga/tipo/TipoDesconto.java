package com.eventmanager.pachanga.tipo;

public enum TipoDesconto {
	
	PORCENTAGEM("P"),
	VALOR("V");

	private String descricao;

	private TipoDesconto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
