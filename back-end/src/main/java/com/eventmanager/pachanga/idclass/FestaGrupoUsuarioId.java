package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class FestaGrupoUsuarioId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int grupo;
    private int usuario;
    private int festa;
    
    public FestaGrupoUsuarioId() {
    }

    public FestaGrupoUsuarioId(int grupo, int usuario, int festa) {
        this.grupo = grupo;
        this.usuario = usuario;
        this.festa = festa;
    }

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int codGrupo) {
		this.grupo = codGrupo;
	}

	public int getCodUsuario() {
		return usuario;
	}

	public void setCodUsuario(int usuario) {
		this.usuario = usuario;
	}

	public int getFesta() {
		return festa;
	}

	public void setFesta(int festa) {
		this.festa = festa;
	}

}
