package com.eventmanager.pachanga.tipo;

public enum TipoPagamentoBoleto {
	
	DECLINED("DECLINED"),
	PAID("PAID");
	
	private String descricao;

	private TipoPagamentoBoleto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
