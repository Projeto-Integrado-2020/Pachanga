package com.eventmanager.pachanga.domains;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
	@Column(name = "quant_max_pessoas")
	private int quantMaxPessoas;
	@OneToMany(fetch = FetchType.LAZY)
	private List<FestaGrupoUsuario> festaGrupoUsuarios;
	
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
	public List<FestaGrupoUsuario> getFestaGrupoUsuarios() {
		return festaGrupoUsuarios;
	}
	public void setFestaGrupoUsuarios(List<FestaGrupoUsuario> festaGrupoUsuarios) {
		this.festaGrupoUsuarios = festaGrupoUsuarios;
	}

}
