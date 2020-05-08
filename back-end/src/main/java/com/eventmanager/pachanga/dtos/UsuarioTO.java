package com.eventmanager.pachanga.dtos;


import java.util.Date;

public class UsuarioTO {
    private String nomeUser;
    private Date dtNasc;
    
    public UsuarioTO(String nomeUser, Date dtNasc) {
    	this.nomeUser = nomeUser;
    	this.dtNasc = dtNasc;
    }

	public String getNomeUser() {
		return nomeUser;
	}

	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}

	public Date getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}
    
}