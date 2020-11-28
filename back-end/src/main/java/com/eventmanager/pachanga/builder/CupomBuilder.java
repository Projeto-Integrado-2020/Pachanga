package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;

public class CupomBuilder {
	
    private String codCupom;
	private Festa festa;
	private float precoDesconto;
	
	public static CupomBuilder getInstance() {
		return new CupomBuilder();
	}
	
	public CupomBuilder codCupom(String codCupom) {
		this.codCupom = codCupom;
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
	
	public Cupom build() {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(codCupom);
		cupom.setFesta(festa);
		cupom.setPrecoDesconto(precoDesconto);
		return cupom;
	}
}
