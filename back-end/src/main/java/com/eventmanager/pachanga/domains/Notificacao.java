package com.eventmanager.pachanga.domains;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "notificacao")
public class Notificacao {
	
	@Id
	@Column(name = "cod_notificacao")
	private int codNotificacao;
	
	@Column(name="desc_notificacao")
	private String descNotificacao;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "notificacao")
	private Set<NotificacaoGrupo> notificacaoGrupo;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "notificacao")
	private Set<NotificacaoConvidado> notificacaoConvidado;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "notificacao")
	private Set<NotificacaoUsuario> notificacaoUsuario;

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

	public Set<NotificacaoGrupo> getNotificacaoGrupo() {
		return notificacaoGrupo;
	}

	public void setNotificacaoGrupo(Set<NotificacaoGrupo> notificacaoGrupo) {
		this.notificacaoGrupo = notificacaoGrupo;
	}

	public Set<NotificacaoConvidado> getNotificacaoConvidado() {
		return notificacaoConvidado;
	}

	public void setNotificacaoConvidado(Set<NotificacaoConvidado> notificacaoConvidado) {
		this.notificacaoConvidado = notificacaoConvidado;
	}

	public Set<NotificacaoUsuario> getNotificacaoUsuario() {
		return notificacaoUsuario;
	}

	public void setNotificacaoUsuario(Set<NotificacaoUsuario> notificacaoUsuario) {
		this.notificacaoUsuario = notificacaoUsuario;
	}
	
}
