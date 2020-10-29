package com.eventmanager.pachanga.domains;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	private String genero;
	@Column(name = "pronome")
	private String pronome;

	@ManyToMany(mappedBy = "usuarios",
			fetch = FetchType.LAZY)
	private Set<Grupo> grupos;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "usuario")
	private Set<NotificacaoUsuario> notificacaoUsuario;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "codUsuarioResolv")
	private Set<AreaSegurancaProblema> areaSegurancaProblemaResolv;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "codUsuarioEmissor")
	private Set<AreaSegurancaProblema> areaSegurancaProblemaEmissor;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "usuario")
	private Set<Ingresso> ingressos;

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
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
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
	public String getPronome() {
		return pronome;
	}
	public void setPronome(String pronome) {
		this.pronome = pronome;
	}
	public Set<Ingresso> getIngressos() {
		return ingressos;
	}
	public void setIngressos(Set<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
	
}
