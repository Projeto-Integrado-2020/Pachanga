package com.eventmanager.pachanga.domains;

import lombok.Data;

import java.util.Date;

@Data
public class Usuario {
    private int codUsuario;
    private String nomeUser;
    private String tipConta;
    private String senha;
    private Date dtNasc;
    private String sexo;
}
