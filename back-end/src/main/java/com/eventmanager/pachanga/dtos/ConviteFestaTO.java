package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

public class ConviteFestaTO {
	
	private String nomeFesta;
	private LocalDateTime horarioInicial;
	private LocalDateTime horarioFinal;
	private String nomeGrupo;
	private String enderecoFesta;
	
	public String getNomeFesta() {
		return nomeFesta;
	}
	public void setNomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
	}
	public LocalDateTime getHorarioInicial() {
		return horarioInicial;
	}
	public void setHorarioInicial(LocalDateTime horarioInicial) {
		this.horarioInicial = horarioInicial;
	}
	public LocalDateTime getHorarioFinal() {
		return horarioFinal;
	}
	public void setHorarioFinal(LocalDateTime horarioFinal) {
		this.horarioFinal = horarioFinal;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public String getEnderecoFesta() {
		return enderecoFesta;
	}
	public void setEnderecoFesta(String enderecoFesta) {
		this.enderecoFesta = enderecoFesta;
	}

}
