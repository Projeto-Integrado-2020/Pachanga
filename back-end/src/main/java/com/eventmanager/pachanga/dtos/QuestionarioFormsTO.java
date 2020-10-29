package com.eventmanager.pachanga.dtos;

public class QuestionarioFormsTO {
	
	private int codQuestionario;
    private int codFesta;
    private String nomeQuestionario;
    private String urlQuestionario;
    
	public int getCodQuestionario() {
		return codQuestionario;
	}
	public void setCodQuestionario(int codQuestionario) {
		this.codQuestionario = codQuestionario;
	}
	public int getCodFesta() {
		return codFesta;
	}
	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}
	public String getNomeQuestionario() {
		return nomeQuestionario;
	}
	public void setNomeQuestionario(String nomeQuestionario) {
		this.nomeQuestionario = nomeQuestionario;
	}
	public String getUrlQuestionario() {
		return urlQuestionario;
	}
	public void setUrlQuestionario(String urlQuestionario) {
		this.urlQuestionario = urlQuestionario;
	}
    
}
