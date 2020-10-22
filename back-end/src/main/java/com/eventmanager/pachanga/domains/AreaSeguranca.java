package com.eventmanager.pachanga.domains;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "area_seguranca")
public class AreaSeguranca {
	
	@Id
	@Column(name = "cod_area")
	private int codArea;
	
	@Column(name = "cod_festa")
	private int codFesta;
	
	@Column(name = "nome_area")
	private String nomeArea;
	
	@Column(name = "status_seguranca")
	private String statusSeguranca;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "area")
	private Set<AreaSegurancaProblema> areaSegurancaProblema;

	public int getCodArea() {
		return codArea;
	}

	public void setCodArea(int codArea) {
		this.codArea = codArea;
	}

	public int getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}

	public String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public String getStatusSeguranca() {
		return statusSeguranca;
	}

	public void setStatusSeguranca(String statusSeguranca) {
		this.statusSeguranca = statusSeguranca;
	}

	public Set<AreaSegurancaProblema> getAreaSegurancaProblema() {
		return areaSegurancaProblema;
	}

	public void setAreaSegurancaProblema(Set<AreaSegurancaProblema> areaSegurancaProblema) {
		this.areaSegurancaProblema = areaSegurancaProblema;
	}

}
