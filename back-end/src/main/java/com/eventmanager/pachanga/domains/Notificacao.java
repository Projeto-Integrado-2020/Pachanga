package com.eventmanager.pachanga.domains;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private Set<NotificacaoUsuario> notificacaoUsuario;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JoinTable(name = "convidado_x_notificacao",
	joinColumns = @JoinColumn(name ="cod_notificacao"),
	inverseJoinColumns = @JoinColumn(name = "cod_convidado"))
	private Set<Convidado> convidados;

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

	public Set<Convidado> getConvidados() {
		return convidados;
	}

	public void setConvidados(Set<Convidado> convidados) {
		this.convidados = convidados;
	}
	
}
