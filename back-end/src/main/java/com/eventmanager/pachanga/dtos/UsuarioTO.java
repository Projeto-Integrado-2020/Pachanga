package com.eventmanager.pachanga.dtos;


import java.util.Date;

import com.eventmanager.pachanga.domains.Usuario;

public class UsuarioTO {
    private Date dtNasc;
    private int codUsuario;
    private  String nomeUser;
    private String tipConta;
    private String email;
    private String senha;
    private String sexo;
    
    public UsuarioTO() {
    }
    
	public UsuarioTO(Usuario usuario) {
		this.codUsuario = usuario.getCodUsuario();
		this.nomeUser = usuario.getNomeUser();
		this.tipConta = usuario.getTipConta();
		this.dtNasc = usuario.getDtNasc();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.sexo = usuario.getSexo();
	}

	public Date getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}

	public int getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNomeUser() {
		return nomeUser;
	}

	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}

	public String getTipConta() {
		return tipConta;
	}

	public void setTipConta(String tipConta) {
		this.tipConta = tipConta;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	    
}