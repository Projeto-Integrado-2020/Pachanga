package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.CupomTO;

public class CupomTOBuilder {
	
    private int codCupom;
    private String nomeCupom;
	private int codFesta;
	private float precoDesconto;
	private int porcentagemDesc;
	private String tipoDesconto;
	
	public static CupomTOBuilder getInstance() {
		return new CupomTOBuilder();
	}
	
	public CupomTOBuilder codCupom(int codCupom) {
		this.codCupom = codCupom;
		return this;
	}
	
	public CupomTOBuilder nomeCupom(String nomeCupom) {
		this.nomeCupom = nomeCupom;
		return this;
	}
	
	public CupomTOBuilder codfesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}
	
	public CupomTOBuilder precoDesconto(float precoDesconto) {
		this.precoDesconto = precoDesconto;
		return this;
	} 
	
	public CupomTOBuilder porcentagemDesc(int porcentagemDesc) {
		this.porcentagemDesc = porcentagemDesc;
		return this;
	}
	
	public CupomTOBuilder tipoDesconto(String tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
		return this;
	}
	
	public CupomTO build() {
		CupomTO cupomTO = new CupomTO();
		cupomTO.setCodCupom(codCupom);
		cupomTO.setNomeCupom(nomeCupom);
		cupomTO.setCodFesta(codFesta);
		cupomTO.setPrecoDesconto(precoDesconto);
		cupomTO.setPorcentagemDesc(porcentagemDesc);
		cupomTO.setTipoDesconto(tipoDesconto);
		return cupomTO;
	}

}
