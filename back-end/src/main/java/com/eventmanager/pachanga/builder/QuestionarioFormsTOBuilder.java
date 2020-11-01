package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.QuestionarioFormsTO;

public class QuestionarioFormsTOBuilder {
	
	private int codQuestionario;
    private int codFesta;
    private String nomeQuestionario;
    private String urlQuestionario;
    
    public static QuestionarioFormsTOBuilder getInstance() {
		return new QuestionarioFormsTOBuilder();
	}
	
	public QuestionarioFormsTOBuilder codQuestionario(int codQuestionario) {
		this.codQuestionario = codQuestionario;
		return this;
	}

	public QuestionarioFormsTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public QuestionarioFormsTOBuilder nomeQuestionario(String nomeQuestionario) {
		this.nomeQuestionario = nomeQuestionario;
		return this;
	}
	
	public QuestionarioFormsTOBuilder urlQuestionario(String urlQuestionario) {
		this.urlQuestionario = urlQuestionario;
		return this;
	}
	
	public QuestionarioFormsTO build() {
		QuestionarioFormsTO questionarioFormsTO = new QuestionarioFormsTO();
		questionarioFormsTO.setCodFesta(codFesta);
		questionarioFormsTO.setCodQuestionario(codQuestionario);
		questionarioFormsTO.setNomeQuestionario(nomeQuestionario);
		questionarioFormsTO.setUrlQuestionario(urlQuestionario);
		return questionarioFormsTO;
	}
}
