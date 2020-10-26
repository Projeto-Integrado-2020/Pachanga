package com.eventmanager.pachanga.tipo;

public enum TipoProblema {
	
	TUMULTTO(1,"Tumulto"),
	VIOLNCIA(2,"Briga"),
	PESOAMAL(3,"Pessoa Passando Mal ou Ferida"),
	ROUBOFUR(4,"Furto"),
	ASSALTTO(5,"Assalto"),
	POSEDARM(6,"Porte de Objeto ou Substância Proibida"),
	VENDANAU(7,"Venda de Produtos não Autorizados"),
	SAIRNPAG(8,"Participante Tendando Sair Sem Pagar"),
	VANDALIS(9,"Vandalismo"),
	ASSEDDIO(10,"Assédio Sexual"),
	ATENTPUD(11,"Atentado ao Pudor"),
	ENTRNAUT(12,"Entrada não Autorizada"),
	OUTROCAS(13,"Outros");
	
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
