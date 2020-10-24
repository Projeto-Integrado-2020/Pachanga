package com.eventmanager.pachanga.tipo;

public enum TipoAreaSeguranca {
	
	SEGURO("S"),
	INSEGURO("I");
	
	private String descricao;

	private TipoAreaSeguranca(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
