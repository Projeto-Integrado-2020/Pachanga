package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.QuestionarioForms;

public class QuestionarioFormsBuilder {
	
	private int codQuestionario;
	private Festa festa;
    private String nomeQuestionario;
    private String urlQuestionario;
    
    public static QuestionarioFormsBuilder getInstance() {
		return new QuestionarioFormsBuilder();
	}
    
    public QuestionarioFormsBuilder codQuestionario(int codQuestionario) {
		this.codQuestionario = codQuestionario;
		return this;
	}

	public QuestionarioFormsBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}

	public QuestionarioFormsBuilder nomeQuestionario(String nomeQuestionario) {
		this.nomeQuestionario = nomeQuestionario;
		return this;
	}
	
	public QuestionarioFormsBuilder urlQuestionario(String urlQuestionario) {
		this.urlQuestionario = urlQuestionario;
		return this;
	}
	
	public QuestionarioForms build() {
		QuestionarioForms questionarioForms = new QuestionarioForms();
		questionarioForms.setFesta(festa);
		questionarioForms.setCodQuestionario(codQuestionario);
		questionarioForms.setNomeQuestionario(nomeQuestionario);
		questionarioForms.setUrlQuestionario(urlQuestionario);
		return questionarioForms;
	}
}
