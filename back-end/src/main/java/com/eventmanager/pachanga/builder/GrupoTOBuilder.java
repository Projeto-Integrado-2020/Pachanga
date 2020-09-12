package com.eventmanager.pachanga.builder;

import java.util.List;

import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.dtos.PermissaoTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;

public class GrupoTOBuilder {
	private int codGrupo;
	private int codFesta;
	private String nomeGrupo;
	private boolean organizador;
    private List<UsuarioTO> usuarios;
    private List<ConvidadoTO> convidados;
    private List<PermissaoTO> permissoes;
    
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
	
	public GrupoTOBuilder isOrganizador(boolean organizador) {
		this.organizador = organizador;
		return this;
	}
	
	public GrupoTOBuilder convidados(List<ConvidadoTO> convidados) {
		this.convidados = convidados;
		return this;
	}
	
	public GrupoTOBuilder permissoes(List<PermissaoTO> permissoes) {
		this.permissoes = permissoes;
		return this;
	}
	
	public GrupoTOBuilder usuarios(List<UsuarioTO> usuarios) {
		this.usuarios = usuarios;
		return this;
	}
	
	public GrupoTO build() {
		GrupoTO grupoTO = new GrupoTO();
		
		grupoTO.setCodGrupo(codGrupo);
		grupoTO.setCodFesta(codFesta);
		grupoTO.setNomeGrupo(nomeGrupo);
		grupoTO.setIsOrganizador(organizador);
		grupoTO.setUsuariosTO(usuarios);
		grupoTO.setPermissoesTO(permissoes);
		grupoTO.setConvidadosTO(convidados);
		return grupoTO;
	}

}
