package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.QuestionarioFormsBuilder;
import com.eventmanager.pachanga.builder.QuestionarioFormsTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.QuestionarioForms;
import com.eventmanager.pachanga.dtos.QuestionarioFormsTO;

@Component(value = "questionarioFormsFactory")
public class QuestionarioFormsFactory {
	
	public QuestionarioForms getQuestionarioForms(QuestionarioFormsTO questionarioFormsTo, Festa festa) {
		return QuestionarioFormsBuilder.getInstance().festa(festa).codQuestionario(questionarioFormsTo.getCodQuestionario())
				.nomeQuestionario(questionarioFormsTo.getNomeQuestionario()).urlQuestionario(questionarioFormsTo.getUrlQuestionario())
				.build();
	}
	
	public QuestionarioFormsTO getQuestionarioFormsTO(QuestionarioForms questionarioForms) {
		return QuestionarioFormsTOBuilder.getInstance().codFesta(questionarioForms.getFesta().getCodFesta())
				.codQuestionario(questionarioForms.getCodQuestionario()).nomeQuestionario(questionarioForms.getNomeQuestionario())
				.urlQuestionario(questionarioForms.getUrlQuestionario()).build();
	}
	
}
