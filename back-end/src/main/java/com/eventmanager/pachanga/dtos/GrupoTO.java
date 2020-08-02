package com.eventmanager.pachanga.dtos;

import java.util.List;

public class GrupoTO {
    
	private int codGrupo;
	private int codFesta;
	private String nomeGrupo;
    private int quantMaxPessoas;
    private List<UsuarioTO> usuariosTO;
    private List<PermissaoTO> permissoesTO;
	
    public int getCodGrupo() {
		return codGrupo;
	}
	public void setCodGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
	}
	public int getCodFesta() {
		return codFesta;
	}
	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public int getQuantMaxPessoas() {
		return quantMaxPessoas;
	}
	public void setQuantMaxPessoas(int quantMaxPessoas) {
		this.quantMaxPessoas = quantMaxPessoas;
	}
	public List<UsuarioTO> getUsuariosTO() {
		return usuariosTO;
	}
	public void setUsuariosTO(List<UsuarioTO> usuariosTO) {
		this.usuariosTO = usuariosTO;
	}
	public List<PermissaoTO> getPermissoesTO() {
		return permissoesTO;
	}
	public void setPermissoesTO(List<PermissaoTO> permissoesTO) {
		this.permissoesTO = permissoesTO;
	}
	
	
}
