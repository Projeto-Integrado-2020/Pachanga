package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.NotificacaoMudancaStatusTO;

public class NotificacaoMudancaStatusTOBuilder {
	
	private int codFesta;

	private String nomeFesta;

	private String tipoAlteracao;

	public static NotificacaoMudancaStatusTOBuilder getInstance() {
		return new NotificacaoMudancaStatusTOBuilder();
	}

	public NotificacaoMudancaStatusTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public NotificacaoMudancaStatusTOBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}

	public NotificacaoMudancaStatusTOBuilder tipoAlteracao(String tipoAlteracao) {
		this.tipoAlteracao = tipoAlteracao;
		return this;
	}

	public NotificacaoMudancaStatusTO build() {
		NotificacaoMudancaStatusTO notificacaoMudancaStatusTo = new NotificacaoMudancaStatusTO();
		notificacaoMudancaStatusTo.setNomeFesta(nomeFesta);
		notificacaoMudancaStatusTo.setTipoAlteracao(tipoAlteracao);
		notificacaoMudancaStatusTo.setCodFesta(codFesta);
		return notificacaoMudancaStatusTo;
	}

}
