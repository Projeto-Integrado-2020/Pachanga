package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;

public class GrupoBuilder {
	private int codGrupo;
	private int codFesta;
	private String nomeGrupo;
    private int quantMaxPessoas;
    
	public static GrupoBuilder getInstance() {
		return new GrupoBuilder();
	}
	
	public GrupoBuilder codGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
		return this;
	}
	
	public GrupoBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}
	
	public GrupoBuilder codFesta(Festa festa) {
		this.codFesta = festa.getCodFesta();
		return this;
	}
	
	public GrupoBuilder nomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
		return this;
	}
	
	public GrupoBuilder quantMaxPessoas(int quantMaxPessoas) {
		this.quantMaxPessoas = quantMaxPessoas;
		return this;
	}
	
	public Grupo build() {
		Grupo grupo = new Grupo();
		
		grupo.setCodGrupo(codGrupo);
		grupo.setFesta(new Festa());
		grupo.setNomeGrupo(nomeGrupo);
		grupo.setQuantMaxPessoas(quantMaxPessoas);
		
		return grupo;
	}
	
	
	
	
}
