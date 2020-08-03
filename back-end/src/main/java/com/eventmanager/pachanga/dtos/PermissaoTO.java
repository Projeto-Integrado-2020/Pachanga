package com.eventmanager.pachanga.dtos;

public class PermissaoTO {
	private int codPermissao;
    private String descPermissao;
    private String tipPermissao;
    
	public int getCodPermissao() {
		return codPermissao;
	}
	public void setCodPermissao(int codPermissao) {
		this.codPermissao = codPermissao;
	}
	public String getDescPermissao() {
		return descPermissao;
	}
	public void setDescPermissao(String descPermissao) {
		this.descPermissao = descPermissao;
	}
	public String getTipPermissao() {
		return tipPermissao;
	}
	public void setTipPermissao(String tipPermissao) {
		this.tipPermissao = tipPermissao;
	}
}
