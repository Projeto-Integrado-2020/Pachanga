package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eventmanager.pachanga.idclass.NotificacaoConvidadoId;

@Entity
@Table(name = "convidado_x_notificacao")
@IdClass(NotificacaoConvidadoId.class)
public class NotificacaoConvidado {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "cod_convidado")
	private Convidado convidado;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "cod_notificacao")
	private Notificacao notificacao;
	
	@Column(name = "mensagem")
	private String mensagem;

	public Convidado getConvidado() {
		return convidado;
	}

	public void setConvidado(Convidado convidado) {
		this.convidado = convidado;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
