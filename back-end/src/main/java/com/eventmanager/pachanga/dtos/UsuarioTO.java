package com.eventmanager.pachanga.dtos;


import java.util.Date;

import com.eventmanager.pachanga.domains.Usuario;

import lombok.Data;

@Data
public class UsuarioTO {
    private Date dtNasc;
    private int codUsuario;
    private  String nomeUser;
    private String tipConta;
    private String email;
    private String senha;
    private String sexo;
    
	public UsuarioTO(Usuario usuario) {
		this.dtNasc = usuario.getDtNasc();
		this.codUsuario = usuario.getCodUsuario();
		this.nomeUser = usuario.getNomeUser();
		this.tipConta = usuario.getTipConta();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.sexo = usuario.getSexo();
	}
    
}