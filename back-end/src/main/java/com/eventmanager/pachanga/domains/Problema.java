package com.eventmanager.pachanga.domains;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "problema")
public class Problema {

	@Id
	@Column(name = "cod_problema")
	private int codProblema;
	
	@Column(name = "desc_problema")
	private String descProblema;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "problema")
	private Set<AreaSegurancaProblema> areaSegurancaProblema;

	public int getCodProblema() {
		return codProblema;
	}

	public void setCodProblema(int codProblema) {
		this.codProblema = codProblema;
	}

	public String getDescProblema() {
		return descProblema;
	}

	public void setDescProblema(String descProblema) {
		this.descProblema = descProblema;
	}

	public Set<AreaSegurancaProblema> getAreaSegurancaProblema() {
		return areaSegurancaProblema;
	}

	public void setAreaSegurancaProblema(Set<AreaSegurancaProblema> areaSegurancaProblema) {
		this.areaSegurancaProblema = areaSegurancaProblema;
	}

}
