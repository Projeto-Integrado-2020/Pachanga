package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dado_bancario")
public class DadoBancario {
	
	@Id
	@Column(name = "cod_dados_bank")
	private int codDadosBancario;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@Column(name = "banco")
	private String codBanco;
	
	@Column(name = "agencia")
	private int codAgencia;
	
	@Column(name = "conta")
	private int codConta;

	public int getCodDadosBancario() {
		return codDadosBancario;
	}

	public void setCodDadosBancario(int codDadosBancario) {
		this.codDadosBancario = codDadosBancario;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public String getCodBanco() {
		return codBanco;
	}

	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}

	public int getCodAgencia() {
		return codAgencia;
	}

	public void setCodAgencia(int codAgencia) {
		this.codAgencia = codAgencia;
	}

	public int getCodConta() {
		return codConta;
	}

	public void setCodConta(int codConta) {
		this.codConta = codConta;
	}

}
