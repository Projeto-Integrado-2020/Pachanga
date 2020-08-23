package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.dtos.NotificacaoConvidadoTO;

public class NotificacaoConvidadoTOBuilder {

	private int codNotificacao;
	
	private int codConvidado;
	
	private String mensagem;
	
	private LocalDateTime dataEmissao;

	public static NotificacaoConvidadoTOBuilder getInstance() {
		return new NotificacaoConvidadoTOBuilder();
	}

	public NotificacaoConvidadoTOBuilder codNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
		return this;
	}

	public NotificacaoConvidadoTOBuilder codConvidado(int codConvidado) {
		this.codConvidado = codConvidado;
		return this;
	}
	
	public NotificacaoConvidadoTOBuilder mensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}
	
	public NotificacaoConvidadoTOBuilder dataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
		return this;
	}

	public NotificacaoConvidadoTO build() {
		NotificacaoConvidadoTO notificacaoConvidadoTo = new NotificacaoConvidadoTO();
		notificacaoConvidadoTo.setCodNotificacao(codNotificacao);
		notificacaoConvidadoTo.setCodConvidado(codConvidado);
		notificacaoConvidadoTo.setMensagem(mensagem);
		notificacaoConvidadoTo.setDataEmissao(dataEmissao);
		return notificacaoConvidadoTo;
	}




}
