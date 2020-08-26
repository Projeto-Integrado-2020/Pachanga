package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.dtos.ConviteFestaTO;

public class ConviteFestaTOBuilder {
	
	private String nomeFesta;
	private LocalDateTime horarioInicial;
	private LocalDateTime horarioFinal;
	private String nomeGrupo;
	private String enderecoFesta;
	
	public static ConviteFestaTOBuilder getInstance() {
		return new ConviteFestaTOBuilder();
	}
	
	public ConviteFestaTOBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}
	public ConviteFestaTOBuilder horarioInicial(LocalDateTime horarioInicial) {
		this.horarioInicial = horarioInicial;
		return this;
	}
	public ConviteFestaTOBuilder horarioFinal(LocalDateTime horarioFinal) {
		this.horarioFinal = horarioFinal;
		return this;
	}
	public ConviteFestaTOBuilder nomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
		return this;
	}
	public ConviteFestaTOBuilder enderecoFesta(String enderecoFesta) {
		this.enderecoFesta = enderecoFesta;
		return this;
	}
	
	public ConviteFestaTO build() {
		ConviteFestaTO conviteFesta = new ConviteFestaTO();
		conviteFesta.setEnderecoFesta(enderecoFesta);
		conviteFesta.setHorarioFinal(horarioFinal);
		conviteFesta.setHorarioInicial(horarioInicial);
		conviteFesta.setNomeFesta(nomeFesta);
		conviteFesta.setNomeGrupo(nomeGrupo);
		return conviteFesta;
	}
}
