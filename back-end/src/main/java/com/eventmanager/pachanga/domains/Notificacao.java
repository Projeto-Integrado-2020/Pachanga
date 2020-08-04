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
import javax.persistence.Table;

@Entity
@Table(name = "notificacao")
public class Notificacao {
	
	@Id
	@Column(name = "cod_notificacao")
	private int codNotificacao;
	
	@Column(name="desc_notificacao")
	private String descNotificacao;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JoinTable(name = "notificacao_x_grupo",
	joinColumns = @JoinColumn(name ="cod_notificacao"),
	inverseJoinColumns = @JoinColumn(name = "cod_grupo"))
	private Set<Grupo> grupos;

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

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	
}
