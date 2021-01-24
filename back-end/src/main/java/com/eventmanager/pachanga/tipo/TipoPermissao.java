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
	DELEFEST(16,"DELEFEST"),//deletar festa
	VISUESTO(17,"VISUESTO"),//visualizar festa
	VISUAREA(18,"VISUAREA"),//visualizar areas
	ADDAREAS(19,"ADDAREAS"),//inserir areas
	DELEAREA(20,"DELEAREA"),//deletar areas
	EDITAREA(21,"EDITAREA"),//editar areas
	VISUPSEG(22,"VISUPSEG"),//visualizar area seguranca problema / problema seguranca
	ADDPSEGU(23,"ADDPSEGU"),//inserir area seguranca problema / problema seguranca
	DELEPSEG(24,"DELEPSEG"),//deletar area seguranca problema / problema seguranca
	EDITPSEG(25,"EDITPSEG"),//editar area seguranca problema / problema seguranca
	VISULOTE(26,"VISULOTE"),//visualizar lote
	ADDLOTES(27,"ADDLOTES"),//inserir lote
	DELELOTE(28,"DELELOTE"),//deletar lote
	EDITLOTE(29,"EDITLOTE"),//editar lote
	VISUFORM(30,"VISUFORM"),//visualizar questionario
	ADDFORMS(31,"ADDFORMS"),//inserir questionarios
	DELEFORM(32,"DELEFORM"),//deletar questionario
	EDITFORM(33,"EDITFORM"),//editar questionario
	VISUINTE(34,"VISUINTE"),//visualizar integração
	ADDINTEG(35,"ADDINTEG"),//inserir integração
	DELEINTE(36,"DELEINTE"),//deletar integração
	EDITINTE(37,"EDITINTE"),//editar integração
	VISURELA(38,"VISURELA"),//visualizar relatório
	VISUDADO(39,"VISUDADO"),//visualizar dados bancários
	MODDADOB(40,"MODDADOB"),//modificar(inserir ou atualizar) dados bancários
	DELEDADO(41,"DELEDADO"),//deletar dados bancários
	VISUCUPM(42,"VISUCUPM"),//visualizar cupom
	ADDCUPOM(43,"ADDCUPOM"),//inserir cupom
	DELECUPM(44,"DELECUPM"),//deletar cupom
	EDITCUPM(45,"EDITCUPM"),//editar cupom
	RELCHECK(46,"RELCHECK"),//realizar check-in da festa
	EXPOREPO(47,"EXPOREPO");//exportar relatório

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
