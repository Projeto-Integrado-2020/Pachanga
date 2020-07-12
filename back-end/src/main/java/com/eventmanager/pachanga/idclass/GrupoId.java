package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class GrupoId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codGrupo;
	private int festa;
	
	public GrupoId() {
	}
	
	public GrupoId(int codGrupo, int festa) {
		this.codGrupo = codGrupo;
		this.festa = festa;
	}
	
	public int getCodGrupo() {
		return codGrupo;
	}
	public void setCodGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
	}
	public int getFesta() {
		return festa;
	}
	public void setFesta(int codFesta) {
		this.festa = codFesta;
	}

}
