package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;

public class NotificacaoAreaSegurancaTOBuilder {

	private int codFesta;

	private String nomeFesta;

	private int codProblema;

	private String descProblema;
	
	private String descProblemaRelatado;

	private int codArea;

	private String nomeArea;
	
	private int codAreaProblema;
	
	public static NotificacaoAreaSegurancaTOBuilder getInstance() {
		return new NotificacaoAreaSegurancaTOBuilder();
	}

	public NotificacaoAreaSegurancaTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public NotificacaoAreaSegurancaTOBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}

	public NotificacaoAreaSegurancaTOBuilder codProblema(int codProblema) {
		this.codProblema = codProblema;
		return this;
	}

	public NotificacaoAreaSegurancaTOBuilder descProblema(String descProblema) {
		this.descProblema = descProblema;
		return this;
	}

	public NotificacaoAreaSegurancaTOBuilder codArea(int codArea) {
		this.codArea = codArea;
		return this;
	}

	public NotificacaoAreaSegurancaTOBuilder nomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
		return this;
	}
	
	public NotificacaoAreaSegurancaTOBuilder descProblemaRelatado(String descProblemaRelatado) {
		this.descProblemaRelatado = descProblemaRelatado;
		return this;
	}
	
	public NotificacaoAreaSegurancaTOBuilder codAreaProblema(int codAreaProblema) {
		this.codAreaProblema = codAreaProblema;
		return this;
	}
	
	public NotificacaoAreaSegurancaTO build() {
		NotificacaoAreaSegurancaTO notificacao = new NotificacaoAreaSegurancaTO();
		notificacao.setCodArea(codArea);
		notificacao.setCodFesta(codFesta);
		notificacao.setCodProblema(codProblema);
		notificacao.setDescProblema(descProblema);
		notificacao.setNomeArea(nomeArea);
		notificacao.setNomeFesta(nomeFesta);
		notificacao.setDescProblemaRelatado(descProblemaRelatado);
		notificacao.setCodAreaProblema(codAreaProblema);
		return notificacao;
	}
	
	

}
