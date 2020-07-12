package com.eventmanager.pachanga.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eventmanager.pachanga.idclass.FestaGrupoUsuarioId;

@Entity
@Table(name = "usuario_x_festa_x_grupo")
@IdClass(FestaGrupoUsuarioId.class)
public class FestaGrupoUsuario {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_grupo")
	private Grupo grupo;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_usuario")
	private Usuario usuario;
	
	public FestaGrupoUsuario() {
	}
	
	public FestaGrupoUsuario(Grupo grupo, Festa festa, Usuario usuario) {
		this.grupo = grupo;
        this.usuario = usuario;
        this.festa = festa;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	

}
