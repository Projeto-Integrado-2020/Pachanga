package com.eventmanager.pachanga.dtos;


import java.util.Date;

public class UsuarioTO {
    public String nomeUser;
    public String tipConta;
    public Date dtNasc;
    public String sexo;
    
    public UsuarioTO(String nomeUser, String tipConta, Date dtNasc, String sexo) {
    	this.nomeUser = nomeUser;
    	this.tipConta = tipConta;
    	this.dtNasc = dtNasc;
    	this.sexo = sexo;
    }
}