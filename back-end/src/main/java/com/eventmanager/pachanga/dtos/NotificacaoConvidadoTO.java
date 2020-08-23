package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

public class NotificacaoConvidadoTO {

	private int codNotificacao;
	
	private int codConvidado;
	
	private String mensagem;
	
	private LocalDateTime dataEmissao;

	public int getCodNotificacao() {
		return codNotificacao;
	}

	public void setCodNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
	}

	public int getCodConvidado() {
		return codConvidado;
	}

	public void setCodConvidado(int codConvidado) {
		this.codConvidado = codConvidado;
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
