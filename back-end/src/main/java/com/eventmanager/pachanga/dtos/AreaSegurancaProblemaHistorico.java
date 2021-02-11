package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

public class AreaSegurancaProblemaHistorico {
	
	private Integer codArea; 
	
	private String nomeArea;
	
	private String statusProblema;
	
	private String descProblema;
	
	private Integer codUsuarioResolv;
	
	private LocalDateTime dataInicialProblema;

	public Integer getCodArea() {
		return codArea;
	}

	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}

	public String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public String getStatusProblema() {
		return statusProblema;
	}

	public void setStatusProblema(String statusProblema) {
		this.statusProblema = statusProblema;
	}

	public String getDescProblema() {
		return descProblema;
	}

	public void setDescProblema(String descProblema) {
		this.descProblema = descProblema;
	}

	public Integer getCodUsuarioResolv() {
		return codUsuarioResolv;
	}

	public void setCodUsuarioResolv(Integer codUsuarioResolv) {
		this.codUsuarioResolv = codUsuarioResolv;
	}

	public LocalDateTime getDataInicialProblema() {
		return dataInicialProblema;
	}

	public void setDataInicialProblema(LocalDateTime dataInicialProblema) {
		this.dataInicialProblema = dataInicialProblema;
	}
	
}
