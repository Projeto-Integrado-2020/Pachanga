package com.eventmanager.pachanga.domains;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {

	@Id
	@Column(name = "cod_categoria")
	private int codCategoria;
	
	@Column(name = "nome_categoria")
	private String nomeCategoria;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "categoria")
	private Set<CategoriasFesta> categoriaFesta;
	

	public int getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(int codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public Set<CategoriasFesta> getCategoriaFesta() {
		return categoriaFesta;
	}

	public void setCategoriaFesta(Set<CategoriasFesta> categoriaFesta) {
		this.categoriaFesta = categoriaFesta;
	}
}
