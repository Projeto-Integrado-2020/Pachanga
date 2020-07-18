package com.eventmanager.pachanga.builder;

import java.util.Date;

import com.eventmanager.pachanga.dtos.UsuarioTO;

public class UsuarioTOBuilder {
	
	private int codUsuario;
	private Date dtNasc;
	private String nomeUser;
	private String email;
	private String sexo;
	private String tipConta;
	private String funcionalidade;
	
	public static UsuarioTOBuilder getInstance() {
		return new UsuarioTOBuilder();
	}
	
	public UsuarioTOBuilder codUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
		return this;
	}
	
	public UsuarioTOBuilder dtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
		return this;
	}



	public UsuarioTOBuilder nomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
		return this;
	}



	public UsuarioTOBuilder email(String email) {
		this.email = email;
		return this;
	}



	public UsuarioTOBuilder sexo(String sexo) {
		this.sexo = sexo;
		return this;
	}
	
	public UsuarioTOBuilder tipConta(String tipConta) {
		this.tipConta = tipConta;
		return this;
	}

	public UsuarioTOBuilder funcionalidade(String funcionalidade) {
		this.funcionalidade = funcionalidade;
		return this;
	}

	public UsuarioTO build() {
		UsuarioTO user = new UsuarioTO();
		user.setCodUsuario(codUsuario);
		user.setDtNasc(dtNasc);
		user.setEmail(email);
		user.setNomeUser(nomeUser);
		user.setSexo(sexo);
		user.setTipConta(tipConta);
		user.setFuncionalidade(funcionalidade);
		return user;
	}

}