package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.GrupoTO;

public class GrupoTOBuilder {
	private int codGrupo;
	private int codFesta;
	private String nomeGrupo;
	private boolean organizador;
    private int quantMaxPessoas;
    
	public static GrupoTOBuilder getInstance() {
		return new GrupoTOBuilder();
	}
    
	public GrupoTOBuilder codGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
		return this;
	}
	
	public GrupoTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}
	
	public GrupoTOBuilder nomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
		return this;
	}
	
	public GrupoTOBuilder quantMaxPessoas(int quantMaxPessoas) {
		this.quantMaxPessoas = quantMaxPessoas;
		return this;
	}
	
	public GrupoTOBuilder isOrganizador(boolean organizador) {
		this.organizador = organizador;
		return this;
	}
	
	public GrupoTO build() {
		GrupoTO grupoTO = new GrupoTO();
		
		grupoTO.setCodGrupo(codGrupo);
		grupoTO.setCodFesta(codFesta);
		grupoTO.setNomeGrupo(nomeGrupo);
		grupoTO.setQuantMaxPessoas(quantMaxPessoas);
		grupoTO.setIsOrganizador(organizador);
		return grupoTO;
	}

}
