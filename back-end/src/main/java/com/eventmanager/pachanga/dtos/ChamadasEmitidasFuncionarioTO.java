package com.eventmanager.pachanga.dtos;

public class ChamadasEmitidasFuncionarioTO {
	
	private String nomeUsuario;
	
	private int codUsuario;
	
	private Integer chamadasFinalizadas;
	
	private Integer chamadasEngano;

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

	public Integer getChamadasFinalizadas() {
		return chamadasFinalizadas;
	}

	public void setChamadasFinalizadas(Integer chamadasFinalizadas) {
		this.chamadasFinalizadas = chamadasFinalizadas;
	}

	public Integer getChamadasEngano() {
		return chamadasEngano;
	}

	public void setChamadasEngano(Integer chamadasEngano) {
		this.chamadasEngano = chamadasEngano;
	}
	
}
