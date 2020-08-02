package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.GrupoTO;

public class GrupoTOBuilder {
	private int codGrupo;
	private Festa codFesta;
	private String nomeGrupo;
    private int quantMaxPessoas;
    
	public static GrupoTOBuilder getInstance() {
		return new GrupoTOBuilder();
	}
	
	public GrupoTOBuilder codGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
		return this;
	}
	
	public GrupoTOBuilder codFesta(Festa codFesta) {
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
	
	public GrupoTO build() {
		GrupoTO grupoTO = new GrupoTO();
		
		grupoTO.setCodGrupo(codGrupo);
		grupoTO.setCodFesta(codFesta.getCodFesta());
		grupoTO.setNomeGrupo(nomeGrupo);
		grupoTO.setQuantMaxPessoas(quantMaxPessoas);
		
		return grupoTO;
	}
}
