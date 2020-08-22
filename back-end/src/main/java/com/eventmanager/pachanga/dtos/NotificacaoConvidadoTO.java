package com.eventmanager.pachanga.dtos;

public class NotificacaoConvidadoTO {

	private int codNotificacao;
	
	private int codConvidado;
	
	private String mensagem;

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

}
