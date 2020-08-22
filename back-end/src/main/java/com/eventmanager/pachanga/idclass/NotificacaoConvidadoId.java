package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class NotificacaoConvidadoId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int convidado;
	
	private int notificacao;

	public int getConvidado() {
		return convidado;
	}

	public void setConvidado(int convidado) {
		this.convidado = convidado;
	}

	public int getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(int notificacao) {
		this.notificacao = notificacao;
	}
}
