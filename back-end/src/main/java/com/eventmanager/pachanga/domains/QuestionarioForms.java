package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questionario_forms")
public class QuestionarioForms {
	
	@Id
	@Column(name = "cod_questionario")
	private int codQuestionario;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@Column(name = "nome_questionario")
    private String nomeQuestionario;
	
	@Column(name = "url_questionario")
    private String urlQuestionario;
    
	public int getCodQuestionario() {
		return codQuestionario;
	}
	public void setCodQuestionario(int codQuestionario) {
		this.codQuestionario = codQuestionario;
	}
	public Festa getFesta() {
		return festa;
	}
	public void setFesta(Festa festa) {
		this.festa = festa;
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
