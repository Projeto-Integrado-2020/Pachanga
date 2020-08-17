package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.NotificacaoGrupoTO;

public class NotificacaoGrupoTOBuilder {
	
	private int codGrupo;

	private int codNotificacao;

	private String mensagem;
	
	public static NotificacaoGrupoTOBuilder getInstance() {
		return new NotificacaoGrupoTOBuilder();
	}

	public NotificacaoGrupoTOBuilder codGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
		return this;
	}

	public NotificacaoGrupoTOBuilder codNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
		return this;
	}

	public NotificacaoGrupoTOBuilder mensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}
	
	public NotificacaoGrupoTO build() {
		NotificacaoGrupoTO notGrupoTo = new NotificacaoGrupoTO();
		notGrupoTo.setGrupo(codGrupo);
		notGrupoTo.setMensagem(mensagem);
		notGrupoTo.setNotificacao(codNotificacao);
		return notGrupoTo;
	}

}
