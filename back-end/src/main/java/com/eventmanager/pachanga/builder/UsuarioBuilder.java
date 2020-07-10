package com.eventmanager.pachanga.builder;

import java.util.Date;

import com.eventmanager.pachanga.domains.Usuario;

public class UsuarioBuilder{

	private Date dtNasc;
	private int codUsuario;
	private  String nomeUser;
	private String tipConta;
	private String email;
	private String senha;
	private String sexo;
	
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

	public UsuarioBuilder tipConta(String tipConta) {
		this.tipConta = tipConta;
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

	public UsuarioBuilder sexo(String sexo) {
		this.sexo = sexo;
		return this;
	}

	public Usuario build() {
		Usuario user = new Usuario();
		user.setCodUsuario(codUsuario);
		user.setDtNasc(dtNasc);
		user.setEmail(email);
		user.setNomeUser(nomeUser);
		user.setSenha(senha);
		user.setSexo(sexo);
		user.setTipConta(tipConta);
		return user;
	}

}
