package com.eventmanager.pachanga.dtos;

public class CupomTO {

	private int codCupom;
	private String nomeCupom;
	private int codFesta;
	private float precoDesconto;
	private int porcentagemDesc;
	private String tipoDesconto;

	public int getCodCupom() {
		return codCupom;
	}

	public void setCodCupom(int codCupom) {
		this.codCupom = codCupom;
	}

	public int getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}

	public float getPrecoDesconto() {
		return precoDesconto;
	}

	public void setPrecoDesconto(float precoDesconto) {
		this.precoDesconto = precoDesconto;
	}

	public String getNomeCupom() {
		return nomeCupom;
	}

	public void setNomeCupom(String nomeCupom) {
		this.nomeCupom = nomeCupom;
	}

	public int getPorcentagemDesc() {
		return porcentagemDesc;
	}

	public void setPorcentagemDesc(int porcentagemDesc) {
		this.porcentagemDesc = porcentagemDesc;
	}

	public String getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(String tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

}
