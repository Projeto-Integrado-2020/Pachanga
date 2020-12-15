package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;

public class AreaSegurancaProblemaTOBuilder {
	private int codAreaProblema;
	private int codAreaSeguranca;
	private int codFesta;
	private int codProblema;
	private int codUsuarioResolv;
    private String statusProblema ;
	private LocalDateTime horarioInicio;
	private LocalDateTime horarioFim;
	private int codUsuarioEmissor;
    private String descProblema;
    private String observacaoSolucao;
    private String descProblemaEmissor;
    private byte[] imagemProblema;
	
	public static AreaSegurancaProblemaTOBuilder getInstance() {
		return new AreaSegurancaProblemaTOBuilder();
	}
	
	public AreaSegurancaProblemaTOBuilder codAreaSeguranca(int codAreaSeguranca) {
		this.codAreaSeguranca = codAreaSeguranca;
		return this;
	}
	
	public AreaSegurancaProblemaTOBuilder codAreaProblema(int codAreaProblema) {
		this.codAreaProblema = codAreaProblema;
		return this;
	}

	public AreaSegurancaProblemaTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}
	
	public AreaSegurancaProblemaTOBuilder codProblema(int codProblema) {
		this.codProblema = codProblema;
		return this;
	}

	public AreaSegurancaProblemaTOBuilder codUsuarioResolv(int codUsuarioResolv) {
		this.codUsuarioResolv = codUsuarioResolv;
		return this;
	}

	public AreaSegurancaProblemaTOBuilder statusProblema(String statusProblema) {
		this.statusProblema = statusProblema;
		return this;
	}

	public AreaSegurancaProblemaTOBuilder horarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
		return this;
	}

	public AreaSegurancaProblemaTOBuilder horarioFim(LocalDateTime horarioFim) {
		this.horarioFim = horarioFim;
		return this;
	}

	public AreaSegurancaProblemaTOBuilder codUsuarioEmissor(int codUsuarioEmissor) {
		this.codUsuarioEmissor = codUsuarioEmissor;
		return this;
	}

	public AreaSegurancaProblemaTOBuilder descProblema(String descProblema) {
		this.descProblema = descProblema;
		return this;
	}
	
	public AreaSegurancaProblemaTOBuilder observacaoSolucao(String observacaoSolucao) {
		this.observacaoSolucao = observacaoSolucao;
		return this;
	}
	
	public AreaSegurancaProblemaTOBuilder descProblemaEmissor(String descProblemaEmissor) {
		this.descProblemaEmissor = descProblemaEmissor;
		return this;
	}
	
	public AreaSegurancaProblemaTOBuilder imagemProblema(byte[] imagemProblema) {
		this.imagemProblema = imagemProblema;
		return this;
	}

	public  AreaSegurancaProblemaTO build() {
		AreaSegurancaProblemaTO problemaSegurancaTO = new  AreaSegurancaProblemaTO();
		problemaSegurancaTO.setCodAreaSeguranca(codAreaSeguranca);
		problemaSegurancaTO.setCodFesta(codFesta);
		problemaSegurancaTO.setCodProblema(codProblema);
		problemaSegurancaTO.setCodUsuarioEmissor(codUsuarioEmissor);
		problemaSegurancaTO.setCodUsuarioResolv(codUsuarioResolv);
		problemaSegurancaTO.setDescProblema(descProblema);
		problemaSegurancaTO.setHorarioFim(horarioFim);
		problemaSegurancaTO.setHorarioInicio(horarioInicio);
		problemaSegurancaTO.setStatusProblema(statusProblema);
		problemaSegurancaTO.setCodAreaProblema(codAreaProblema);
		problemaSegurancaTO.setObservacaoSolucao(observacaoSolucao);
		problemaSegurancaTO.setDescProblemaEmissor(descProblemaEmissor);
		problemaSegurancaTO.setImagemProblema(imagemProblema);
		return problemaSegurancaTO;
	}
	
}
