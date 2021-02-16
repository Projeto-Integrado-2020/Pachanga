package com.eventmanager.pachanga.tipo;

public enum TipoPermissao {
	
	EDITDFES(1,"EDITDFES"),//Editar dados da festa
	CREGRPER(4,"CREGRPER"),//Criar grupo de permissão
	DELGRPER(5,"DELGRPER"),//deletar grupo de permissão
	EDIGRPER(6,"EDIGRPER"),//editar grupo de permissão
	ADDMEMBE(7,"ADDMEMBE"),//adicionar membros
	DELMEMBE(8,"DELMEMBE"),//expulsar membros
	DISMEMBE(9,"DISMEMBE"),//distribuir os membros entre os grupos de permissão da festa
	CADAESTO(11,"CADAESTO"),//permissão para cadastrar estoque
	DELEESTO(12,"DELEESTO"),//permissão para remover estoque
	EDITESTO(13,"EDITESTO"),//permissão para editar estoque
	CADMESTO(14,"CADMESTO"),//permissão para cadastrar marca em estoque
	DELMESTO(15,"DELMESTO"),//permissão para remover marca do estoque
	EDIMESTO(16,"EDIMESTO"),//permissão para editar marca do estoque
	ADDMESTO(17,"ADDMESTO"),//permissão para adicionar quantidade à marca
	BAIMESTO(18,"BAIMESTO"),//permissão para dar baixa em produto no estoque
	DELEFEST(2,"DELEFEST"),//deletar festa
	VISUESTO(19,"VISUESTO"),//visualizar festa
	VISUAREA(20,"VISUAREA"),//visualizar areas
	ADDAREAS(21,"ADDAREAS"),//inserir areas
	DELEAREA(22,"DELEAREA"),//deletar areas
	EDITAREA(23,"EDITAREA"),//editar areas
	VISUPSEG(24,"VISUPSEG"),//visualizar area seguranca problema / problema seguranca
	ADDPSEGU(25,"ADDPSEGU"),//inserir area seguranca problema / problema seguranca
	DELEPSEG(26,"DELEPSEG"),//deletar area seguranca problema / problema seguranca
	EDITPSEG(27,"EDITPSEG"),//editar area seguranca problema / problema seguranca
	VISULOTE(28,"VISULOTE"),//visualizar lote
	ADDLOTES(29,"ADDLOTES"),//inserir lote
	DELELOTE(30,"DELELOTE"),//deletar lote
	EDITLOTE(31,"EDITLOTE"),//editar lote
	VISUFORM(45,"VISUFORM"),//visualizar questionario
	ADDFORMS(46,"ADDFORMS"),//inserir questionarios
	DELEFORM(47,"DELEFORM"),//deletar questionario
	EDITFORM(48,"EDITFORM"),//editar questionario
	VISUINTE(32,"VISUINTE"),//visualizar integração
	ADDINTEG(33,"ADDINTEG"),//inserir integração
	DELEINTE(34,"DELEINTE"),//deletar integração
	EDITINTE(35,"EDITINTE"),//editar integração
	VISURELA(49,"VISURELA"),//visualizar relatório
	VISUDADO(36,"VISUDADO"),//visualizar dados bancários
	MODDADOB(37,"MODDADOB"),//modificar(inserir ou atualizar) dados bancários
	DELEDADO(38,"DELEDADO"),//deletar dados bancários
	VISUCUPM(39,"VISUCUPM"),//visualizar cupom
	ADDCUPOM(40,"ADDCUPOM"),//inserir cupom
	DELECUPM(41,"DELECUPM"),//deletar cupom
	EDITCUPM(42,"EDITCUPM"),//editar cupom
	RELCHECK(43,"RELCHECK"),//realizar check-in da festa
	EXPOREPO(44,"EXPOREPO"),//exportar relatório
	VISGRPER(10,"VISGRPER"),//visualizar grupos de permissão
	EDITSTAS(3,"EDITSTAS");//editar status festa

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
