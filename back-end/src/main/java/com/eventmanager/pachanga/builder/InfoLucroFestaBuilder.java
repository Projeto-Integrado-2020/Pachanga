package com.eventmanager.pachanga.builder;

import java.util.Map;

import com.eventmanager.pachanga.dtos.InfoLucroFesta;

public class InfoLucroFestaBuilder {

	private Float lucroTotal;

	private Map<String, Float> lucroLote;
	
	public static InfoLucroFestaBuilder getInstance() {
		return new InfoLucroFestaBuilder();
	}
	
	public InfoLucroFestaBuilder lucroTotal(Float lucroTotal) {
		this.lucroTotal = lucroTotal;
		return this;
	}
	
	public InfoLucroFestaBuilder lucroLote(Map<String, Float> lucroLote) {
		this.lucroLote = lucroLote;
		return this;
	}
	
	public InfoLucroFesta build() {
		InfoLucroFesta infoLucroFesta = new InfoLucroFesta();
		infoLucroFesta.setLucroLote(lucroLote);
		infoLucroFesta.setLucroTotal(lucroTotal);
		return infoLucroFesta;
	}

}
