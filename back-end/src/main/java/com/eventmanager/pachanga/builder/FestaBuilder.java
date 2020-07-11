package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.domains.Festa;

public class FestaBuilder {
	
	private int codFesta;
	private String nomeFesta;
	private String statusFesta;
	private String organizador;
	private LocalDateTime horarioInicioFesta;
	private LocalDateTime horarioFimFesta;
	private String descricaoFesta;
	private String codEnderecoFesta;
	private String descOrganizador;
	private LocalDateTime horarioFimFestaReal;
	
	public static FestaBuilder getInstance() {
		return new FestaBuilder();
	}

	public FestaBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public FestaBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}

	public FestaBuilder statusFesta(String statusFesta) {
		this.statusFesta = statusFesta;
		return this;
	}

	public FestaBuilder organizador(String organizador) {
		this.organizador = organizador;
		return this;
	}

	public FestaBuilder horarioInicioFesta(LocalDateTime horarioInicioFesta) {
		this.horarioInicioFesta = horarioInicioFesta;
		return this;
	}

	public FestaBuilder horarioFimFesta(LocalDateTime horarioFimFesta) {
		this.horarioFimFesta = horarioFimFesta;
		return this;
	}

	public FestaBuilder descricaoFesta(String descricaoFesta) {
		this.descricaoFesta = descricaoFesta;
		return this;
	}

	public FestaBuilder codEnderecoFesta(String codEnderecoFesta) {
		this.codEnderecoFesta = codEnderecoFesta;
		return this;
	}

	public FestaBuilder descOrganizador(String descOrganizador) {
		this.descOrganizador = descOrganizador;
		return this;
	}
	
	public FestaBuilder horarioFimFestaReal(LocalDateTime horarioFimFestaReal) {
		this.horarioFimFestaReal = horarioFimFestaReal;
		return this;
	}
	
	public Festa build() {
		Festa festa = new Festa();
		festa.setCodEnderecoFesta(codEnderecoFesta);
		festa.setCodFesta(codFesta);
		festa.setDescOrganizador(descOrganizador);
		festa.setDescricaoFesta(descricaoFesta);
		festa.setHorarioInicioFesta(horarioInicioFesta);
		festa.setHorarioFimFesta(horarioFimFesta);
		festa.setNomeFesta(nomeFesta);
		festa.setOrganizador(organizador);
		festa.setStatusFesta(statusFesta);
		festa.setHorarioFimFestaReal(horarioFimFestaReal);
		return festa;
	}
}
