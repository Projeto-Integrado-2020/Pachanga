package com.eventmanager.pachanga.builder;

import java.util.Map;

import com.eventmanager.pachanga.dtos.ChamadasEmitidasFuncionarioTO;

public class ChamadasEmitidasFuncionarioTOBuilder {

	private String nomeUsuario;

	private int codUsuario;

	private Map<Integer, Integer> chamadasFinalizadasEngano; // int primeiro é o finalizado depois o engano
	
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
	
	public ChamadasEmitidasFuncionarioTOBuilder chamadasFinalizadasEngano(Map<Integer, Integer> chamadasFinalizadasEngano) {
		this.chamadasFinalizadasEngano = chamadasFinalizadasEngano;
		return this;
	}
	
	public ChamadasEmitidasFuncionarioTO build() {
		ChamadasEmitidasFuncionarioTO chamadas = new ChamadasEmitidasFuncionarioTO();
		chamadas.setChamadasFinalizadasEngano(chamadasFinalizadasEngano);
		chamadas.setCodUsuario(codUsuario);
		chamadas.setNomeUsuario(nomeUsuario);
		return chamadas;
	}

}
