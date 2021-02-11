package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaHistorico;

public class AreaSegurancaProblemaHistoricoBuilder {

	private Integer codArea;

	private String nomeArea;

	private String statusProblema;

	private String descProblema;

	private Integer codUsuarioResolv;
	
	private LocalDateTime dataInicialProblema;
	
	public static AreaSegurancaProblemaHistoricoBuilder getInstance() {
		return new AreaSegurancaProblemaHistoricoBuilder();
	}
	
	public AreaSegurancaProblemaHistoricoBuilder codArea(Integer codArea) {
		this.codArea = codArea;
		return this;
	}
	
	public AreaSegurancaProblemaHistoricoBuilder nomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
		return this;
	}
	
	public AreaSegurancaProblemaHistoricoBuilder statusProblema(String statusProblema) {
		this.statusProblema = statusProblema;
		return this;
	}
	
	public AreaSegurancaProblemaHistoricoBuilder descProblema(String descProblema) {
		this.descProblema = descProblema;
		return this;
	}
	
	public AreaSegurancaProblemaHistoricoBuilder codUsuarioResolv(Integer codUsuarioResolv) {
		this.codUsuarioResolv = codUsuarioResolv;
		return this;
	}
	
	public AreaSegurancaProblemaHistoricoBuilder dataInicialProblema(LocalDateTime dataInicialProblema) {
		this.dataInicialProblema = dataInicialProblema;
		return this;
	}
	
	public AreaSegurancaProblemaHistorico build() {
		AreaSegurancaProblemaHistorico area = new AreaSegurancaProblemaHistorico();
		area.setCodArea(codArea);
		area.setCodUsuarioResolv(codUsuarioResolv);
		area.setDescProblema(descProblema);
		area.setNomeArea(nomeArea);
		area.setStatusProblema(statusProblema);
		area.setDataInicialProblema(dataInicialProblema);
		return area;
	}

}
