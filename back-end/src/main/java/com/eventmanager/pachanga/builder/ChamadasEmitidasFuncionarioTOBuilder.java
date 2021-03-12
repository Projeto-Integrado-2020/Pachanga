package com.eventmanager.pachanga.builder;

import java.util.Map;

import com.eventmanager.pachanga.dtos.ChamadasEmitidasFuncionarioTO;

public class ChamadasEmitidasFuncionarioTOBuilder {

	private String nomeUsuario;

	private int codUsuario;

	private Integer chamadasFinalizadas;

	private Integer chamadasEngano;

	public static ChamadasEmitidasFuncionarioTOBuilder getInstance() {
		return new ChamadasEmitidasFuncionarioTOBuilder();
	}

	public ChamadasEmitidasFuncionarioTOBuilder nomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
		return this;
	}

	public ChamadasEmitidasFuncionarioTOBuilder codUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
		return this;
	}
	
	public ChamadasEmitidasFuncionarioTOBuilder chamadasFinalizadas(int chamadasFinalizadas) {
		this.chamadasFinalizadas = chamadasFinalizadas;
		return this;
	}
	
	public ChamadasEmitidasFuncionarioTOBuilder chamadasEngano(int chamadasEngano) {
		this.chamadasEngano = chamadasEngano;
		return this;
	}

	public ChamadasEmitidasFuncionarioTO build() {
		ChamadasEmitidasFuncionarioTO chamadas = new ChamadasEmitidasFuncionarioTO();
		chamadas.setChamadasEngano(chamadasEngano);
		chamadas.setChamadasFinalizadas(chamadasFinalizadas);
		chamadas.setCodUsuario(codUsuario);
		chamadas.setNomeUsuario(nomeUsuario);
		return chamadas;
	}

}
