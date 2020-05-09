package com.eventmanager.pachanga.domains;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
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
	
//	public Usuario(String nomeUser, String tipConta, String email, String senha, Date dtNasc, String sexo) {
//		this.nomeUser = nomeUser;
//		this.tipConta = tipConta;
//		this.email = email;
//		this.senha = senha;
//		this.dtNasc = dtNasc;
//		this.sexo = sexo;
//	}
    
}
