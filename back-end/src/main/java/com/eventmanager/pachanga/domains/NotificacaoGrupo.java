package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notificacao_x_grupo")
public class NotificacaoGrupo {
	
	@Id
	@Column(name = "cod_notificacao_grupo")
	private int codNotificacaoGrupo;
	
	@ManyToOne
	@JoinColumn(name = "cod_grupo")
	private Grupo grupo;
	
	@ManyToOne
	@JoinColumn(name = "cod_notificacao")
	private Notificacao notificacao;
	
	@Column(name = "mensagem")
	private String mensagem;
	
	@Column(name = "data_emissao")
	private LocalDateTime dataEmissao;

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
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

	public int getCodNotificacaoGrupo() {
		return codNotificacaoGrupo;
	}

	public void setCodNotificacaoGrupo(int codNotificacaoGrupo) {
		this.codNotificacaoGrupo = codNotificacaoGrupo;
	}
}
