package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

public class AreaSegurancaProblemaTO {
	private int codAreaProblema;
	private int codAreaSeguranca;
	private int codFesta;
	private int codProblema;
	private int codUsuarioResolv;
    private String statusProblema ;
	private LocalDateTime horarioInicio;
	private LocalDateTime horarioFim;
	private int codUsuarioEmissor;
    private String descProblemaEmissor;
    private String observacaoSolucao;
    private String descProblema;
    private byte[] imagemProblema;
    private boolean hasImagem;
    
	public int getCodAreaSeguranca() {
		return codAreaSeguranca;
	}
	public void setCodAreaSeguranca(int codAreaSeguranca) {
		this.codAreaSeguranca = codAreaSeguranca;
	}
	public int getCodFesta() {
		return codFesta;
	}
	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
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
	public int getCodUsuarioEmissor() {
		return codUsuarioEmissor;
	}
	public void setCodUsuarioEmissor(int codUsuarioEmissor) {
		this.codUsuarioEmissor = codUsuarioEmissor;
	}
	public String getDescProblema() {
		return descProblema;
	}
	public void setDescProblema(String descProblema) {
		this.descProblema = descProblema;
	}
	public int getCodAreaProblema() {
		return codAreaProblema;
	}
	public void setCodAreaProblema(int codAreaProblema) {
		this.codAreaProblema = codAreaProblema;
	}
	public String getObservacaoSolucao() {
		return observacaoSolucao;
	}
	public void setObservacaoSolucao(String observacaoSolucao) {
		this.observacaoSolucao = observacaoSolucao;
	}
	public String getDescProblemaEmissor() {
		return descProblemaEmissor;
	}
	public void setDescProblemaEmissor(String descProblemaEmissor) {
		this.descProblemaEmissor = descProblemaEmissor;
	}
	public byte[] getImagemProblema() {
		return imagemProblema;
	}
	public void setImagemProblema(byte[] imagemProblema) {
		this.imagemProblema = imagemProblema;
	}
	public boolean isHasImagem() {
		return hasImagem;
	}
	public void setHasImagem(boolean hasImagem) {
		this.hasImagem = hasImagem;
	}
    
}
