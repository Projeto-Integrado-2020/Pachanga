package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.dtos.NotificacaoGrupoTO;

public class NotificacaoGrupoTOBuilder {
	
	private int codGrupo;

	private int codNotificacao;

	private String mensagem;
	
	private LocalDateTime dataEmissao;
	
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
	
	public NotificacaoGrupoTOBuilder dataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
		return this;
	}
	
	public NotificacaoGrupoTO build() {
		NotificacaoGrupoTO notGrupoTo = new NotificacaoGrupoTO();
		notGrupoTo.setGrupo(codGrupo);
		notGrupoTo.setMensagem(mensagem);
		notGrupoTo.setNotificacao(codNotificacao);
		notGrupoTo.setDataEmissao(dataEmissao);
		return notGrupoTo;
	}

}
