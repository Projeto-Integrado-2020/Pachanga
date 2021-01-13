package com.eventmanager.pachanga.dtos;

import java.util.Map;

public class InfoLucroFesta {
	
	private Float lucroTotal;
	
	private Map<String, Float> lucroLote;

	public Float getLucroTotal() {
		return lucroTotal;
	}

	public void setLucroTotal(Float lucroTotal) {
		this.lucroTotal = lucroTotal;
	}

	public Map<String, Float> getLucroLote() {
		return lucroLote;
	}

	public void setLucroLote(Map<String, Float> lucroLote) {
		this.lucroLote = lucroLote;
	}
}
