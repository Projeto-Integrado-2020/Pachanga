package com.eventmanager.pachanga.tipo;

public enum TipoProblema {
	
	TUMULTTO(1,"TUMULTTO"),
	BRIGAVIO(2,"BRIGAVIO"),
	EMEGMEDI(3,"EMEGMEDI"),
	FURTOOBJ(4,"FURTOOBJ"),
	ASSALTTO(5,"ASSALTTO"),
	PORTOBJT(6,"PORTOBJT"),
	PORTSUBS(7,"PORTSUBS"),
	VENDAILG(8,"VENDAILG"),
	SAIRSPAG(9,"SAIRSPAG"),
	VANDALIS(10,"VANDALIS"),
	ASSEDSEX(11,"ASSEDSEX"),
	ATENTPUD(12,"ATENTPUD"),
	ENTRPROB(13,"ENTRPROB"),
	OUTRPROB(14,"OUTRPROB");
	
	private String valor;
	private int codigo;

	private TipoProblema(int codigo, String valor) {
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
