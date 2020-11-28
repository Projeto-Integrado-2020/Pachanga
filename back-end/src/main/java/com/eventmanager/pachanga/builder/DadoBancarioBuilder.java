package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.domains.Festa;

public class DadoBancarioBuilder {

	private int codDadosBancario;

	private Festa festa;

	private String codBanco;

	private int codAgencia;

	private int codConta;
	
	public static DadoBancarioBuilder getInstance() {
		return new DadoBancarioBuilder();
	}

	public DadoBancarioBuilder codDadosBancario(int codDadosBancario) {
		this.codDadosBancario = codDadosBancario;
		return this;
	}

	public DadoBancarioBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}

	public DadoBancarioBuilder codBanco(String codBanco) {
		this.codBanco = codBanco;
		return this;
	}

	public DadoBancarioBuilder codAgencia(int codAgencia) {
		this.codAgencia = codAgencia;
		return this;
	}

	public DadoBancarioBuilder codConta(int codConta) {
		this.codConta = codConta;
		return this;
	}
	
	public DadoBancario build() {
		DadoBancario dadoBancario = new DadoBancario();
		dadoBancario.setCodAgencia(codAgencia);
		dadoBancario.setCodBanco(codBanco);
		dadoBancario.setCodConta(codConta);
		dadoBancario.setCodDadosBancario(codDadosBancario);
		dadoBancario.setFesta(festa);
		return dadoBancario;
	}
	

}
