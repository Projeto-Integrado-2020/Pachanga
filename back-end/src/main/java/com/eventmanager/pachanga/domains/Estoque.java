package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "estoque")
public class Estoque {
	
	@Id
	@Column(name = "cod_estoque")
	private int codEstoque;
	
	@Column(name = "principal")
	private boolean principal;
	
	@Column(name = "nome_estoque")
	private String nomeEstoque;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;

	public int getCodEstoque() {
		return codEstoque;
	}

	public void setCodEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getNomeEstoque() {
		return nomeEstoque;
	}

	public void setNomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

}
