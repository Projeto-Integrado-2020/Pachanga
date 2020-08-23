package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

public class NotificacaoGrupoTO {

	private int codGrupo;

	private int codNotificacao;

	private String mensagem;
	
	private LocalDateTime dataEmissao;

	public int getGrupo() {
		return codGrupo;
	}

	public void setGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
	}

	public int getNotificacao() {
		return codNotificacao;
	}

	public void setNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public LocalDateTime getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

}
