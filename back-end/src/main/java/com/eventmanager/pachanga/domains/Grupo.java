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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grupo")
public class Grupo {
	
	@Id
	@Column(name = "cod_grupo")
	private int codGrupo;
	
	@Column(name = "nome_grupo")
	private String nomeGrupo;
	
	@Column(name = "organizador")
	private boolean organizador;
	
	@Column(name = "quant_max_pessoas")
	private int quantMaxPessoas;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	
	@JoinTable(name = "usuario_x_grupo",
	joinColumns = @JoinColumn(name ="cod_grupo"),
	inverseJoinColumns = @JoinColumn(name = "cod_usuario"))
	private Set<Usuario> usuarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JoinTable(name = "permissao_x_grupo",
	joinColumns = @JoinColumn(name ="cod_grupo"),
	inverseJoinColumns = @JoinColumn(name = "cod_permissao"))
	private Set<Permissao> permissoes;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "grupo")
	private Set<NotificacaoGrupo> notificacaoGrupo;
	
	@ManyToMany(mappedBy = "grupos",
			fetch = FetchType.LAZY)
	private Set<Convidado> convidados;
	
	public Grupo(int codGrupo, Festa festa, String nomeGrupo, int quant, boolean organizador) {
		this.codGrupo = codGrupo;
		this.nomeGrupo = nomeGrupo;
		this.quantMaxPessoas = quant;
		this.festa = festa;
		this.organizador = organizador;
	}
	
	public Grupo() {
	}
	
	public int getCodGrupo() {
		return codGrupo;
	}
	public void setCodGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public int getQuantMaxPessoas() {
		return quantMaxPessoas;
	}
	public void setQuantMaxPessoas(int quantMaxPessoas) {
		this.quantMaxPessoas = quantMaxPessoas;
	}
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public Festa getFesta() {
		return festa;
	}
	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public Set<Convidado> getConvidados() {
		return convidados;
	}

	public void setConvidados(Set<Convidado> convidados) {
		this.convidados = convidados;
	}

	public boolean getOrganizador() {
		return organizador;
	}

	public void setOrganizador(boolean organizador) {
		this.organizador = organizador;
	}

	public Set<NotificacaoGrupo> getNotificacaoGrupo() {
		return notificacaoGrupo;
	}

	public void setNotificacaoGrupo(Set<NotificacaoGrupo> notificacaoGrupo) {
		this.notificacaoGrupo = notificacaoGrupo;
	}

}
