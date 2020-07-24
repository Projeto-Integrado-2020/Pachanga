package com.eventmanager.pachanga.tipo;

public enum TipoPermissao {
	
	EDITDFES(1,"EDITDFES"),//Editar dados da festa
	CREGRPER(2,"CREGRPER"),//Criar grupo de permissão
	DELGRPER(3,"DELGRPER"),//deletar grupo de permissão
	EDIGRPER(4,"EDIGRPER"),//editar grupo de permissão
	ADDMEMBE(5,"ADDMEMBE"),//adicionar membros
	DELMEMBE(6,"DELMEMBE"),//expulsar membros
	DISMEMBE(7,"DISMEMBE"),//distribuir os membros entre os grupos de permissão da festa
	CADAESTO(8,"CADAESTO"),//permissão para cadastrar estoque
	DELEESTO(9,"DELEESTO"),//permissão para remover estoque
	EDITESTO(10,"EDITESTO"),//permissão para editar estoque
	CADMESTO(11,"CADMESTO"),//permissão para cadastrar marca em estoque
	DELMESTO(12,"DELMESTO"),//permissão para remover marca do estoque
	EDIMESTO(13,"EDIMESTO"),//permissão para editar marca do estoque
	ADDMESTO(14,"ADDMESTO"),//permissão para adicionar quantidade à marca
	BAIMESTO(15,"BAIMESTO"),//permissão para dar baixa em produto no estoque
	DELEFEST(16,"DELEFEST");//deletar festa

	private String valor;
	private int codigo;

	private TipoPermissao(int codigo, String valor) {
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
