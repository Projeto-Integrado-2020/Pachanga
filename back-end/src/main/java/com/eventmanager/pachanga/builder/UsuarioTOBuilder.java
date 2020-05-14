package com.eventmanager.pachanga.builder;

import java.util.Date;

import com.eventmanager.pachanga.dtos.UsuarioTO;

public class UsuarioTOBuilder {
	
	private Date dtNasc;
	private  String nomeUser;
	private String email;
	private String sexo;
	
	public static UsuarioTOBuilder getInstance() {
		return new UsuarioTOBuilder();
	}
	
	public UsuarioTOBuilder DtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
		return this;
	}



	public UsuarioTOBuilder NomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
		return this;
	}



	public UsuarioTOBuilder Email(String email) {
		this.email = email;
		return this;
	}



	public UsuarioTOBuilder Sexo(String sexo) {
		this.sexo = sexo;
		return this;
	}


	public UsuarioTO build() {
		UsuarioTO user = new UsuarioTO();
		user.setDtNasc(dtNasc);
		user.setEmail(email);
		user.setNomeUser(nomeUser);
		user.setSexo(sexo);
		return user;
	}

}