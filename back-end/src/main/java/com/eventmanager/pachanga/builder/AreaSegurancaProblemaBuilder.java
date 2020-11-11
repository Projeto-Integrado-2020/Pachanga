package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;

public class  AreaSegurancaProblemaBuilder {
	private AreaSeguranca areaSeguranca;
	private Festa festa;
	private Problema problema;
	private Usuario usuarioResolv;
    private String statusProblema ;
	private LocalDateTime horarioInicio;
	private LocalDateTime horarioFim;
	private Usuario usuarioEmissor;
    private String descProblema;
    private String observacaoSolucao;
	
	public static AreaSegurancaProblemaBuilder getInstance() {
		return new AreaSegurancaProblemaBuilder();
	}
	
	public AreaSegurancaProblemaBuilder areaSeguranca(AreaSeguranca areaSeguranca) {
		this.areaSeguranca = areaSeguranca;
		return this;
	}

	public AreaSegurancaProblemaBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}
	
	public AreaSegurancaProblemaBuilder problema(Problema problema) {
		this.problema = problema;
		return this;
	}

	public AreaSegurancaProblemaBuilder usuarioResolv(Usuario usuarioResolv) {
		this.usuarioResolv = usuarioResolv;
		return this;
	}

	public AreaSegurancaProblemaBuilder statusProblema(String statusProblema) {
		this.statusProblema = statusProblema;
		return this;
	}

	public AreaSegurancaProblemaBuilder horarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
		return this;
	}

	public AreaSegurancaProblemaBuilder horarioFim(LocalDateTime horarioFim) {
		this.horarioFim = horarioFim;
		return this;
	}

	public AreaSegurancaProblemaBuilder usuarioEmissor(Usuario usuarioEmissor) {
		this.usuarioEmissor = usuarioEmissor;
		return this;
	}

	public AreaSegurancaProblemaBuilder descProblema(String descProblema) {
		this.descProblema = descProblema;
		return this;
	}
	
	public AreaSegurancaProblemaBuilder observacaoSolucao(String observacaoSolucao) {
		this.observacaoSolucao = observacaoSolucao;
		return this;
	}

	public AreaSegurancaProblema build() {
		AreaSegurancaProblema problemaSeguranca = new AreaSegurancaProblema();
		problemaSeguranca.setArea(areaSeguranca);
		problemaSeguranca.setFesta(festa);
		problemaSeguranca.setProblema(problema);
		problemaSeguranca.setCodUsuarioEmissor(usuarioEmissor);
		problemaSeguranca.setCodUsuarioResolv(usuarioResolv);
		problemaSeguranca.setDescProblema(descProblema);
		problemaSeguranca.setHorarioFim(horarioFim);
		problemaSeguranca.setHorarioInicio(horarioInicio);
		problemaSeguranca.setStatusProblema(statusProblema);
		problemaSeguranca.setObservacaoSolucao(observacaoSolucao);
		return problemaSeguranca;
	}
}
