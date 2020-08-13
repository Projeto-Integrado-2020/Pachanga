package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class NotificacaoGrupoId implements Serializable{

	private static final long serialVersionUID = 1L;

	private int grupo;

	private int notificacao;
	
	public NotificacaoGrupoId() {}

	public NotificacaoGrupoId(int grupo, int notificacao) {
		this.grupo = grupo;
		this.notificacao = notificacao;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public int getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(int notificacao) {
		this.notificacao = notificacao;
	}

}
