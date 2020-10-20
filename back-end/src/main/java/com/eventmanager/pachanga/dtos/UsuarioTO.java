package com.eventmanager.pachanga.dtos;


import java.util.Date;

public class UsuarioTO {
    private Date dtNasc;
    private int codUsuario;
    private String nomeUser;
    private String tipConta;
    private String conta;
    private String email; //email atual da pesoa
    private String emailNovo;
    private String senha;
    private String senhaNova;
    private String genero;
    private String funcionalidade;
    private String pronome;
    
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

	public void setEmail(String emailAtual) {
		this.email = emailAtual;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEmailNovo() {
		return emailNovo;
	}

	public void setEmailNovo(String emailNovo) {
		this.emailNovo = emailNovo;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(String funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getPronome() {
		return pronome;
	}

	public void setPronome(String pronome) {
		this.pronome = pronome;
	}
	    
}