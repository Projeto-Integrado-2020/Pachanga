package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;

public class GrupoBuilder {
	private int codGrupo;
	private Festa codFesta;
	private String nomeGrupo;
    private boolean organizador;
    
	public static GrupoBuilder getInstance() {
		return new GrupoBuilder();
	}
	
	public GrupoBuilder codGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
		return this;
	}

	public GrupoBuilder codFesta(Festa festa) {
		this.codFesta = festa;
		return this;
    }
	
	public GrupoBuilder nomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
		return this;
	}
	
	public GrupoBuilder organizador(boolean organizador) {
		this.organizador = organizador;
		return this;
	}
	
	public Grupo build() {
		Grupo grupo = new Grupo();
		
		grupo.setCodGrupo(codGrupo);
		grupo.setFesta(codFesta);
		grupo.setNomeGrupo(nomeGrupo);
		grupo.setOrganizador(organizador);
		
		return grupo;
	}

	
	
	
	
}
