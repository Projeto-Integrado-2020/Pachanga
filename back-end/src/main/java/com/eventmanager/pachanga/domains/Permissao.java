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
@Table(name = "permissao")
public class Permissao {

	@Id
	@Column(name = "cod_permissao")
	private int codPermissao;
	@Column(name = "desc_permissao")
	private String descPermissao;
	@Column(name = "tip_permissao")
	private String tipPermissao;
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JoinTable(name = "permissao_x_grupo",
	joinColumns = @JoinColumn(name ="cod_permissao"),
	inverseJoinColumns = @JoinColumn(name = "cod_grupo"))
	private Set<Grupo> grupos;
	public int getCodPermissao() {
		return codPermissao;
	}
	public void setCodPermissao(int codPermissao) {
		this.codPermissao = codPermissao;
	}
	public String getDescPermissao() {
		return descPermissao;
	}
	public void setDescPermissao(String descPermissao) {
		this.descPermissao = descPermissao;
	}
	public String getTipPermissao() {
		return tipPermissao;
	}
	public void setTipPermissao(String tipPermissao) {
		this.tipPermissao = tipPermissao;
	}
	public Set<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	
}
