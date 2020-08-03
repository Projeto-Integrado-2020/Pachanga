package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.PermissaoTO;

public class PermissaoTOBuilder {
	private int codPermissao;
    private String descPermissao;
    private String tipPermissao;
	
	public static PermissaoTOBuilder getInstance() {
		return new PermissaoTOBuilder();
	}
	
	public PermissaoTOBuilder codPermissao(int codPermissao) {
		this.codPermissao = codPermissao;
		return this;
	}
	
	public PermissaoTOBuilder descPermissao(String descPermissao) {
		this.descPermissao = descPermissao;
		return this;
	}
	
	public PermissaoTOBuilder tipPermissao(String tipPermissao) {
		this.tipPermissao = tipPermissao;
		return this;
	}
	
	public PermissaoTO build() {
		PermissaoTO permissaoTO = new PermissaoTO();
		
		permissaoTO.setCodPermissao(codPermissao);
		permissaoTO.setDescPermissao(descPermissao);
		permissaoTO.setTipPermissao(tipPermissao);
		
		return permissaoTO;
	}
}
