package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.ProblemaTO;

public class ProblemaTOBuilder {
	private int codProblema;
	private String descProblema;
	
	public static ProblemaTOBuilder getInstance() {
		return new ProblemaTOBuilder();
	}

	public ProblemaTOBuilder codProblema(int codProblema) {
		this.codProblema = codProblema;
		return this;
	}

	public ProblemaTOBuilder descProblema(String descProblema) {
		this.descProblema = descProblema;
		return this;
	}
	
	public ProblemaTO build() {
		ProblemaTO problemaTO = new ProblemaTO();
		problemaTO.setCodProblema(codProblema);
		problemaTO.setDescProblema(descProblema);
		return problemaTO;
	}
}
