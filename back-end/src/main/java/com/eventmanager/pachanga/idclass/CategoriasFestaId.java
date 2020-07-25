package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class CategoriasFestaId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int festa;
	private int categoria;

	public CategoriasFestaId() {
	}

	public CategoriasFestaId(int codFesta, int codCategoria) {
		this.festa = codFesta;
		this.categoria = codCategoria;
	}
	
	public int getFesta() {
		return festa;
	}

	public void setFesta(int festa) {
		this.festa = festa;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

}
