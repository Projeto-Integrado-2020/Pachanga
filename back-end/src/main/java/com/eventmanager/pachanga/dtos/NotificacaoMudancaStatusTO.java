package com.eventmanager.pachanga.dtos;

public class NotificacaoMudancaStatusTO {
	
	private String nomeFesta;
	
	private String tipoAlteracao;
	
	private int codFesta;
	
	public String getNomeFesta() {
		return nomeFesta;
	}

	public void setNomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
	}

	public String getTipoAlteracao() {
		return tipoAlteracao;
	}

	public void setTipoAlteracao(String tipoAlteracao) {
		this.tipoAlteracao = tipoAlteracao;
	}

	public int getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}

}
