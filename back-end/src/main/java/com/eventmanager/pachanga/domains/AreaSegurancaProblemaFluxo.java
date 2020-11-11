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
	
	@Column(name = "horario_inicio")
	private LocalDateTime horarioInicio;
	
	@Column(name = "cod_usuario_emissor")
	private Integer codUsuarioEmissor;
	
	@Column(name = "descricao_prob")
	private String descProblema;
	
	@Column(name = "obs_solucao")
	private String observacaoSolucao;
	
	@Column(name = "horario_fim")
	private LocalDateTime horarioFim;
	
	@Column(name = "cod_area")
	private Integer codArea;
	
	@Column(name = "cod_problema")
	private Integer codProblema;
	
	@Column(name = "cod_area_problema")
	private Integer codAreaProblema;
	
	public AreaSegurancaProblemaFluxo() {
		
	}
	
	public AreaSegurancaProblemaFluxo(AreaSegurancaProblema areaProblema) {
		this.codUsuarioResolv = areaProblema.getCodUsuarioResolv() == null ? null : areaProblema.getCodUsuarioResolv().getCodUsuario();
		this.horarioInicio = areaProblema.getHorarioInicio();
		this.codUsuarioEmissor = areaProblema.getCodUsuarioEmissor() == null ? null : areaProblema.getCodUsuarioEmissor().getCodUsuario();
		this.descProblema = areaProblema.getDescProblema();
		this.observacaoSolucao = areaProblema.getObservacaoSolucao();
		this.horarioFim = areaProblema.getHorarioFim();
		this.codArea = areaProblema.getArea().getCodArea();
		this.codProblema = areaProblema.getProblema().getCodProblema();
		this.codAreaProblema = areaProblema.getCodAreaProblema();
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

}
