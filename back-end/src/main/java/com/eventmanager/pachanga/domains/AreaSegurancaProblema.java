package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "area_seguranca_x_problema")
public class AreaSegurancaProblema {
	
	@Id
	@Column(name = "cod_area_problema")
	private int codAreaProblema;
	
	@ManyToOne
	@JoinColumn(name = "cod_problema")
	private Problema problema;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@ManyToOne
	@JoinColumn(name = "cod_area")
	private AreaSeguranca area;
	
	@ManyToOne
	@JoinColumn(name = "cod_usuario_resolv")
	private Usuario codUsuarioResolv;
	
	@Column(name = "status_problema")
	private String statusProblema;
	
	@Column(name = "horario_inicio")
	private LocalDateTime horarioInicio;
	
	@Column(name = "horario_fim")
	private LocalDateTime horarioFim;
	
	@ManyToOne
	@JoinColumn(name = "cod_usuario_emissor")
	private Usuario codUsuarioEmissor;
	
	@Column(name = "descricao_prob")
	private String descProblema;

	public Problema getProblema() {
		return problema;
	}

	public void setProblema(Problema problema) {
		this.problema = problema;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public AreaSeguranca getArea() {
		return area;
	}

	public void setArea(AreaSeguranca area) {
		this.area = area;
	}

	public String getStatusProblema() {
		return statusProblema;
	}

	public void setStatusProblema(String statusProblema) {
		this.statusProblema = statusProblema;
	}

	public LocalDateTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public LocalDateTime getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(LocalDateTime horarioFim) {
		this.horarioFim = horarioFim;
	}

	public String getDescProblema() {
		return descProblema;
	}

	public void setDescProblema(String descProblema) {
		this.descProblema = descProblema;
	}

	public Usuario getCodUsuarioResolv() {
		return codUsuarioResolv;
	}

	public void setCodUsuarioResolv(Usuario codUsuarioResolv) {
		this.codUsuarioResolv = codUsuarioResolv;
	}

	public Usuario getCodUsuarioEmissor() {
		return codUsuarioEmissor;
	}

	public void setCodUsuarioEmissor(Usuario codUsuarioEmissor) {
		this.codUsuarioEmissor = codUsuarioEmissor;
	}

	public int getCodAreaProblema() {
		return codAreaProblema;
	}

	public void setCodAreaProblema(int codAreaProblema) {
		this.codAreaProblema = codAreaProblema;
	}

}
