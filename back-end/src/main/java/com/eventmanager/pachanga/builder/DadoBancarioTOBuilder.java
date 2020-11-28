package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.DadoBancarioTO;

public class DadoBancarioTOBuilder {
	
	private int codDadosBancario;

	private int codFesta;

	private String codBanco;

	private int codAgencia;

	private int codConta;
	
	public static DadoBancarioTOBuilder getInstance() {
		return new DadoBancarioTOBuilder();
	}

	public DadoBancarioTOBuilder codDadosBancario(int codDadosBancario) {
		this.codDadosBancario = codDadosBancario;
		return this;
	}

	public DadoBancarioTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public DadoBancarioTOBuilder codBanco(String codBanco) {
		this.codBanco = codBanco;
		return this;
	}

	public DadoBancarioTOBuilder codAgencia(int codAgencia) {
		this.codAgencia = codAgencia;
		return this;
	}

	public DadoBancarioTOBuilder codConta(int codConta) {
		this.codConta = codConta;
		return this;
	}
	
	public DadoBancarioTO build() {
		DadoBancarioTO dadoBancario = new DadoBancarioTO();
		dadoBancario.setCodAgencia(codAgencia);
		dadoBancario.setCodBanco(codBanco);
		dadoBancario.setCodConta(codConta);
		dadoBancario.setCodDadosBancario(codDadosBancario);
		dadoBancario.setCodFesta(codFesta);
		return dadoBancario;
	}
	
	

}
