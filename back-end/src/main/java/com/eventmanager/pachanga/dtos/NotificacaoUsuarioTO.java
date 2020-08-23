package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

public class NotificacaoUsuarioTO {
	
	private int codUsuario;
	
	private int codNotificacao;
	
	private boolean destaque;
	
	private String status;
	
	private String mensagem;
	
	private LocalDateTime dataEmissao;

	public int getNotificacao() {
		return codNotificacao;
	}

	public void setNotificacao(int codNotificacao) {
		this.codNotificacao = codNotificacao;
	}

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}
	
	public LocalDateTime getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

}
