package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "area_seguranca_x_problema_fluxo")
public class AreaSegurancaProblemaFluxo {
	
	@Id
	@Column(name="cod_historico")
	private Integer codHistorico;
	
	@Column(name = "dt_horario")
	private LocalDateTime dataHorario;
	
	@Column(name = "cod_usuario_resolv")
	private Integer codUsuarioResolv;
	
	@Column(name = "cod_festa")
	private Integer codFesta;
	
	@Column(name = "nome_usuario_resolv")
	private String nomeUsuarioResolv;
	
	@Column(name = "horario_inicio")
	private LocalDateTime horarioInicio;
	
	@Column(name = "cod_usuario_emissor")
	private Integer codUsuarioEmissor;
	
	@Column(name = "nome_usuario_emissor")
	private String nomeUsuarioEmissor;
	
	@Column(name = "descricao_prob")
	private String descProblema;
	
	@Column(name = "obs_solucao")
	private String observacaoSolucao;
	
	@Column(name = "horario_fim")
	private LocalDateTime horarioFim;
	
	@Column(name = "cod_area")
	private Integer codArea;
	
	@Column(name = "nome_area")
	private String nomeArea;
	
	@Column(name = "cod_problema")
	private Integer codProblema;
	
	@Column(name = "desc_problema")
	private String nomeProblema;
	
	@Column(name = "cod_area_problema")
	private Integer codAreaProblema;
	
	@Column(name = "status_problema")
	private String statusProblema;
	
	public AreaSegurancaProblemaFluxo() {
		
	}
	
	public AreaSegurancaProblemaFluxo(AreaSegurancaProblema areaProblema, Usuario usuarioEmissor, Usuario usuarioResolv) {
		this.codUsuarioResolv = areaProblema.getCodUsuarioResolv() == null ? null : areaProblema.getCodUsuarioResolv().getCodUsuario();
		this.horarioInicio = areaProblema.getHorarioInicio();
		this.codUsuarioEmissor = areaProblema.getCodUsuarioEmissor() == null ? null : areaProblema.getCodUsuarioEmissor().getCodUsuario();
		this.descProblema = areaProblema.getDescProblema();
		this.observacaoSolucao = areaProblema.getObservacaoSolucao();
		this.horarioFim = areaProblema.getHorarioFim();
		this.codArea = areaProblema.getArea().getCodArea();
		this.codProblema = areaProblema.getProblema().getCodProblema();
		this.codAreaProblema = areaProblema.getCodAreaProblema();
		this.nomeArea = areaProblema.getArea().getNomeArea();
		this.codFesta = areaProblema.getFesta().getCodFesta();
		this.nomeUsuarioEmissor = usuarioEmissor == null ? null : usuarioEmissor.getNomeUser();
		this.nomeUsuarioResolv = usuarioResolv == null ? null : usuarioResolv.getNomeUser();
		this.statusProblema = areaProblema.getStatusProblema();
		this.nomeProblema = areaProblema.getProblema() == null ? null: areaProblema.getProblema().getDescProblema();
	}
	
	public Integer getCodHistorico() {
		return codHistorico;
	}

	public void setCodHistorico(Integer codHistorico) {
		this.codHistorico = codHistorico;
	}

	public LocalDateTime getDataHorario() {
		return dataHorario;
	}

	public void setDataHorario(LocalDateTime dataHorario) {
		this.dataHorario = dataHorario;
	}

	public LocalDateTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public String getDescProblema() {
		return descProblema;
	}

	public void setDescProblema(String descProblema) {
		this.descProblema = descProblema;
	}

	public String getObservacaoSolucao() {
		return observacaoSolucao;
	}

	public void setObservacaoSolucao(String observacaoSolucao) {
		this.observacaoSolucao = observacaoSolucao;
	}

	public LocalDateTime getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(LocalDateTime horarioFim) {
		this.horarioFim = horarioFim;
	}

	public int getCodArea() {
		return codArea;
	}

	public void setCodArea(int codArea) {
		this.codArea = codArea;
	}

	public int getCodProblema() {
		return codProblema;
	}

	public void setCodProblema(int codProblema) {
		this.codProblema = codProblema;
	}

	public int getCodUsuarioResolv() {
		return codUsuarioResolv;
	}

	public void setCodUsuarioResolv(int codUsuarioResolv) {
		this.codUsuarioResolv = codUsuarioResolv;
	}

	public int getCodUsuarioEmissor() {
		return codUsuarioEmissor;
	}

	public void setCodUsuarioEmissor(int codUsuarioEmissor) {
		this.codUsuarioEmissor = codUsuarioEmissor;
	}

	public String getNomeUsuarioResolv() {
		return nomeUsuarioResolv;
	}

	public void setNomeUsuarioResolv(String nomeUsuarioResolv) {
		this.nomeUsuarioResolv = nomeUsuarioResolv;
	}

	public String getNomeUsuarioEmissor() {
		return nomeUsuarioEmissor;
	}

	public void setNomeUsuarioEmissor(String nomeUsuarioEmissor) {
		this.nomeUsuarioEmissor = nomeUsuarioEmissor;
	}

	public String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public Integer getCodAreaProblema() {
		return codAreaProblema;
	}

	public void setCodAreaProblema(Integer codAreaProblema) {
		this.codAreaProblema = codAreaProblema;
	}

	public void setCodUsuarioResolv(Integer codUsuarioResolv) {
		this.codUsuarioResolv = codUsuarioResolv;
	}

	public void setCodUsuarioEmissor(Integer codUsuarioEmissor) {
		this.codUsuarioEmissor = codUsuarioEmissor;
	}

	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}

	public void setCodProblema(Integer codProblema) {
		this.codProblema = codProblema;
	}

	public Integer getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(Integer codFesta) {
		this.codFesta = codFesta;
	}

	public String getNomeProblema() {
		return nomeProblema;
	}

	public void setNomeProblema(String nomeProblema) {
		this.nomeProblema = nomeProblema;
	}

	public String getStatusProblema() {
		return statusProblema;
	}

	public void setStatusProblema(String statusProblema) {
		this.statusProblema = statusProblema;
	}

}
