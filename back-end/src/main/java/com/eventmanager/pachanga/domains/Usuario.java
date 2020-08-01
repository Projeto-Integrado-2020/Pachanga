package com.eventmanager.pachanga.domains;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@Column(name = "cod_usuario")
	private int codUsuario;
	@Column(name = "nome_user")
	private  String nomeUser;
	@Column(name = "facebook")
	private String facebook;
	@Column(name = "gmail")
	private String gmail;
	private String email;
	@Column(name = "senha")
	private String senha;
	@Column(name = "dt_nasc")
	private Date dtNasc;
	private String sexo;

	@ManyToMany(mappedBy = "usuarios",
			fetch = FetchType.LAZY)
	private Set<Grupo> grupos;

	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getNomeUser() {
		return nomeUser;
	}
	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
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
	public int getCodUsuario() {
		return codUsuario;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	
}
