package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.NotificacaoConvidadoTO;

public class NotificacaoConvidadoTOBuilder {

	private int codNotificacao;

	private String descNotificacao;

	public static NotificacaoConvidadoTOBuilder getInstance() {
		return new NotificacaoConvidadoTOBuilder();
	}

	public NotificacaoConvidadoTOBuilder codNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
		return this;
	}

	public NotificacaoConvidadoTOBuilder descNotificacao(String descNotificacao) {
		this.descNotificacao = descNotificacao;
		return this;
	}

	public NotificacaoConvidadoTO build() {
		NotificacaoConvidadoTO notificacaoConvidadoTo = new NotificacaoConvidadoTO();
		notificacaoConvidadoTo.setCodNotificacao(codNotificacao);
		notificacaoConvidadoTo.setDescNotificacao(descNotificacao);
		return notificacaoConvidadoTo;
	}


}
