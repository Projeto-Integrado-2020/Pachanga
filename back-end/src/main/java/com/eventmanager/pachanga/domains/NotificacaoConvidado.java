package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "convidado_x_notificacao")
public class NotificacaoConvidado {
	
	@Id
	@Column(name = "cod_convidado_notificacao")
	private int codConvidadoNotificacao;
	
	@ManyToOne
	@JoinColumn(name = "cod_convidado")
	private Convidado convidado;
	
	@ManyToOne
	@JoinColumn(name = "cod_notificacao")
	private Notificacao notificacao;
	
	@Column(name = "mensagem")
	private String mensagem;
	
	@Column(name = "data_emissao")
	private LocalDateTime dataEmissao;

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

	public LocalDateTime getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getCodConvidadoNotificacao() {
		return codConvidadoNotificacao;
	}

	public void setCodConvidadoNotificacao(int codConvidadoNotificacao) {
		this.codConvidadoNotificacao = codConvidadoNotificacao;
	}

}
