package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;

public class CupomBuilder {
	
    private int codCupom;
    private String nomeCupom;
	private Festa festa;
	private float precoDesconto;
	private int porcentagemDesc;
	private String tipoDesconto;
	
	public static CupomBuilder getInstance() {
		return new CupomBuilder();
	}
	
	public CupomBuilder codCupom(int codCupom) { 
		this.codCupom = codCupom;
		return this;
	}
	
	public CupomBuilder nomeCupom(String nomeCupom) {
		this.nomeCupom = nomeCupom;
		return this;
	}
	
	public CupomBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}
	
	public CupomBuilder precoDesconto(float precoDesconto) {
		this.precoDesconto = precoDesconto;
		return this;
	}
	
	public CupomBuilder porcentagemDesc(int porcentagemDesc) {
		this.porcentagemDesc = porcentagemDesc;
		return this;
	}
	
	public CupomBuilder tipoDesconto(String tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
		return this;
	}
	
	public Cupom build() {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(codCupom);
		cupom.setNomeCupom(nomeCupom);
		cupom.setFesta(festa);
		cupom.setPrecoDesconto(precoDesconto);
		cupom.setPorcentagemDesc(porcentagemDesc);
		cupom.setTipoDesconto(tipoDesconto);
		return cupom;
	}
}
