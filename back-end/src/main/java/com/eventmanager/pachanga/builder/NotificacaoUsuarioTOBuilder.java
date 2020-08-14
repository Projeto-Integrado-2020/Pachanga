package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.NotificacaoUsuarioTO;

public class NotificacaoUsuarioTOBuilder {
	
	private int codUsuario;

	private int codNotificacao;

	private boolean destaque;

	private String status;

	private String mensagem;
	
	public static NotificacaoUsuarioTOBuilder getInstance() {
		return new NotificacaoUsuarioTOBuilder();
	}

	public NotificacaoUsuarioTOBuilder codUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
		return this;
	}

	public NotificacaoUsuarioTOBuilder codNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
		return this;
	}

	public NotificacaoUsuarioTOBuilder destaque(boolean destaque) {
		this.destaque = destaque;
		return this;
	}

	public NotificacaoUsuarioTOBuilder status(String status) {
		this.status = status;
		return this;
	}

	public NotificacaoUsuarioTOBuilder mensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}
	
	public NotificacaoUsuarioTO build() {
		NotificacaoUsuarioTO notUsuarioTo = new NotificacaoUsuarioTO();
		notUsuarioTo.setCodUsuario(codUsuario);
		notUsuarioTo.setDestaque(destaque);
		notUsuarioTo.setMensagem(mensagem);
		notUsuarioTo.setNotificacao(codNotificacao);
		notUsuarioTo.setStatus(status);
		return notUsuarioTo;
	}
	
	
}
