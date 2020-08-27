package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notificacao_x_usuario")
public class NotificacaoUsuario {
	
	@Id
	@Column(name = "cod_notificacao_usuario")
	private int codNotificacaoUsuario;
	
	@ManyToOne
	@JoinColumn(name = "cod_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "cod_notificacao")
	private Notificacao notificacao;
	
	@Column(name = "destaque")
	private boolean destaque;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "mensagem")
	private String mensagem;
	
	@Column(name = "data_emissao")
	private LocalDateTime dataEmissao;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
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

	public LocalDateTime getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getCodNotificacaoUsuario() {
		return codNotificacaoUsuario;
	}

	public void setCodNotificacaoUsuario(int codNotificacaoUsuario) {
		this.codNotificacaoUsuario = codNotificacaoUsuario;
	}

}
