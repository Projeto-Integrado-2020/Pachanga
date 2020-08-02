package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Permissao;

public class PermissaoBuilder {
	private int codPermissao;
    private String descPermissao;
    private String tipPermissao;
	
	public static PermissaoBuilder getInstance() {
		return new PermissaoBuilder();
	}
	
	public PermissaoBuilder codPermissao(int codPermissao) {
		this.codPermissao = codPermissao;
		return this;
	}
	
	public PermissaoBuilder descPermissao(String descPermissao) {
		this.descPermissao = descPermissao;
		return this;
	}
	
	public PermissaoBuilder tipPermissao(String tipPermissao) {
		this.tipPermissao = tipPermissao;
		return this;
	}
	
	public Permissao build() {
		Permissao permissao = new Permissao();
		
		permissao.setCodPermissao(codPermissao);
		permissao.setDescPermissao(descPermissao);
		permissao.setTipPermissao(tipPermissao);
		
		return permissao;
	}
}
