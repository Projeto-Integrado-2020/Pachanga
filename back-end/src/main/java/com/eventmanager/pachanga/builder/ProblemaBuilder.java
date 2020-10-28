package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Problema;

public class ProblemaBuilder {
	private int codProblema;
	private String descProblema;
	
	public static ProblemaBuilder getInstance() {
		return new ProblemaBuilder();
	}

	public ProblemaBuilder codProblema(int codProblema) {
		this.codProblema = codProblema;
		return this;
	}

	public ProblemaBuilder descProblema(String descProblema) {
		this.descProblema = descProblema;
		return this;
	}
	
	public Problema build() {
		Problema problema = new Problema();
		problema.setCodProblema(codProblema);
		problema.setDescProblema(descProblema);
		return problema;
	}
	
}
