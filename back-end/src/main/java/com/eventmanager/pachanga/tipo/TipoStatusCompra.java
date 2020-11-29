package com.eventmanager.pachanga.tipo;

public enum TipoStatusCompra {
	
	COMPRADO("C"),
	PAGO("P");
	
	private String descricao;

	private TipoStatusCompra(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
