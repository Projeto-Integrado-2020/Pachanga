package com.eventmanager.pachanga.domains;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "usuario")
public class Usuario {
	@Id
	@Column(name = "cod_usuario")
    private int codUsuario;
    @Column(name = "nome_user")
    private  String nomeUser;
    @Column(name = "tip_conta")
    private String tipConta;
    private String email;
    @Column(name = "senha")
    private String senha;
    @Column(name = "dt_nasc")
    private Date dtNasc;
    private String sexo;
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
	public Date getDtNasc() {
		return dtNasc;
	}
	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
//	public Usuario(String nomeUser, String tipConta, String email, String senha, Date dtNasc, String sexo) {
//		this.nomeUser = nomeUser;
//		this.tipConta = tipConta;
//		this.email = email;
//		this.senha = senha;
//		this.dtNasc = dtNasc;
//		this.sexo = sexo;
//	}
     
}
