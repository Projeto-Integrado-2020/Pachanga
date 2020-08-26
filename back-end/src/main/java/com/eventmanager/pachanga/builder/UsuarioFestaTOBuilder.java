package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.UsuarioFestaTO;

public class UsuarioFestaTOBuilder {

	private String nomeFesta;

	private String nomeGrupo;

	private String nomeUsuario;
	
	public static UsuarioFestaTOBuilder getInstance() {
		return new UsuarioFestaTOBuilder();
	}

	public UsuarioFestaTOBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}

	public UsuarioFestaTOBuilder nomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
		return this;
	}

	public UsuarioFestaTOBuilder nomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
		return this;
	}
	
	public UsuarioFestaTO build() {
		UsuarioFestaTO infoUser = new UsuarioFestaTO();
		infoUser.setNomeFesta(nomeFesta);
		infoUser.setNomeGrupo(nomeGrupo);
		infoUser.setNomeUsuario(nomeUsuario);
		return infoUser;
	}

}
