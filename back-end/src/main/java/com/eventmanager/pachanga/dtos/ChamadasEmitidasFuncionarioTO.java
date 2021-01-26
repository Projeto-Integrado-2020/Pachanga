package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class ChamadasEmitidasFuncionarioTO {
	
	private String nomeUsuario;
	
	private int codUsuario;
	
	private Map<Integer, Integer> chamadasFinalizadasEngano; // int primeiro é o finalizado depois o engano

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public int getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}

	public Map<Integer, Integer> getChamadasFinalizadasEngano() {
		return chamadasFinalizadasEngano;
	}

	public void setChamadasFinalizadasEngano(Map<Integer, Integer> chamadasFinalizadasEngano) {
		this.chamadasFinalizadasEngano = chamadasFinalizadasEngano;
	}
	
}
