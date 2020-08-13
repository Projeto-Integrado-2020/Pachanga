package com.eventmanager.pachanga.dtos;

public class NotificacaoTO {
	
	private int codNotificacao;
	
	private String descNotificacao;
	
	private String status;
	
	private boolean destaque;

	public int getCodNotificacao() {
		return codNotificacao;
	}

	public void setCodNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
	}

	public String getDescNotificacao() {
		return descNotificacao;
	}

	public void setDescNotificacao(String descNotificacao) {
		this.descNotificacao = descNotificacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}

}
