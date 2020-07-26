package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eventmanager.pachanga.idclass.CategoriasFestaId;

@Entity
@Table(name = "festa_x_categoria")
@IdClass(CategoriasFestaId.class)
public class CategoriasFesta {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "cod_categoria")
	private Categoria categoria;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@Column(name = "tip_categoria")
	private String tipCategoria;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public String getTipCategoria() {
		return tipCategoria;
	}

	public void setTipCategoria(String tipCategoria) {
		this.tipCategoria = tipCategoria;
	}

}
