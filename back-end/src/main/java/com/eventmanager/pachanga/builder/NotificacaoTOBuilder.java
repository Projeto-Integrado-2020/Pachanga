package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.NotificacaoTO;

public class NotificacaoTOBuilder {

	private int codNotificacao;

	private String descNotificacao;

	private String status;

	private boolean destaque;

	public static NotificacaoTOBuilder getInstance() {
		return new NotificacaoTOBuilder();
	}

	public NotificacaoTOBuilder codNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
		return this;
	}

	public NotificacaoTOBuilder descNotificacao(String descNotificacao) {
		this.descNotificacao = descNotificacao;
		return this;
	}

	public NotificacaoTOBuilder status(String status) {
		this.status = status;
		return this;
	}

	public NotificacaoTOBuilder destaque(boolean destaque) {
		this.destaque = destaque;
		return this;
	}

	public NotificacaoTO build() {
		NotificacaoTO notificacaoTo = new NotificacaoTO();
		notificacaoTo.setCodNotificacao(codNotificacao);
		notificacaoTo.setDescNotificacao(descNotificacao);
		notificacaoTo.setDestaque(destaque);
		notificacaoTo.setStatus(status);
		return notificacaoTo;
	}


}
