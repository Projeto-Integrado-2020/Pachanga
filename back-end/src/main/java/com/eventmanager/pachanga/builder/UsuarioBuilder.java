package com.eventmanager.pachanga.builder;

import java.util.Date;

import com.eventmanager.pachanga.domains.Usuario;

public class UsuarioBuilder{

	private Date dtNasc;
	private int codUsuario;
	private  String nomeUser;
	private String facebook;
	private String gmail;
	private String email;
	private String senha;
	private String genero;
	
	public static UsuarioBuilder getInstance() {
		return new UsuarioBuilder();
	}
	
	public UsuarioBuilder dtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
		return this;
	}

	public UsuarioBuilder codUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
		return this;
	}

	public UsuarioBuilder nomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
		return this;
	}

	public UsuarioBuilder gmail(String gmail) {
		this.gmail = gmail;
		return this;
	}
	
	public UsuarioBuilder facebook(String facebook) {
		this.facebook = facebook;
		return this;
	}

	public UsuarioBuilder email(String email) {
		this.email = email;
		return this;
	}

	public UsuarioBuilder senha(String senha) {
		this.senha = senha;
		return this;
	}

	public UsuarioBuilder genero(String genero) {
		this.genero = genero;
		return this;
	}

	public Usuario build() {
		Usuario user = new Usuario();
		user.setCodUsuario(codUsuario);
		user.setDtNasc(dtNasc);
		user.setEmail(email);
		user.setNomeUser(nomeUser);
		user.setSenha(senha);
		user.setGenero(genero);
		user.setGmail(gmail);
		user.setFacebook(facebook);
		return user;
	}

}
