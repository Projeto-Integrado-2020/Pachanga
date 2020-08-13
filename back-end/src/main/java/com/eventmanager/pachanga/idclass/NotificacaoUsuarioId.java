package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class NotificacaoUsuarioId implements Serializable{

	private static final long serialVersionUID = 1L;

	private int usuario;

	private int notificacao;
		
	public NotificacaoUsuarioId() {}

	public NotificacaoUsuarioId(int usuario, int notificacao) {
		this.usuario = usuario;
		this.notificacao = notificacao;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public int getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(int notificacao) {
		this.notificacao = notificacao;
	}

}
