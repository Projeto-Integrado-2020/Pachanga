package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

public class IngressoTO {

	private String codIngresso;
	private int codLote;
	private FestaTO festa;
	private int codUsuario;
	private String statusIngresso;
	private float preco;
	private String statusCompra;
	private LocalDateTime dataCompra;
	private LocalDateTime dataCheckin;
	private String nomeTitular;
	private String emailTitular;
	private String codBoleto;
	private Boolean boleto;
	
	public FestaTO getFesta() {
		return festa;
	}
	public void setFesta(FestaTO festa) {
		this.festa = festa;
	}
	public String getCodIngresso() {
		return codIngresso;
	}
	public void setCodIngresso(String codIngresso) {
		this.codIngresso = codIngresso;
	}

	public String getStatusIngresso() {
		return statusIngresso;
	}
	public void setStatusIngresso(String statusIngresso) {
		this.statusIngresso = statusIngresso;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public String getStatusCompra() {
		return statusCompra;
	}
	public void setStatusCompra(String statusCompra) {
		this.statusCompra = statusCompra;
	}
	public LocalDateTime getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(LocalDateTime dataCompra) {
		this.dataCompra = dataCompra;
	}
	public LocalDateTime getDataCheckin() {
		return dataCheckin;
	}
	public void setDataCheckin(LocalDateTime dataCheckin) {
		this.dataCheckin = dataCheckin;
	}
	public int getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}
	public int getCodLote() {
		return codLote;
	}
	public void setCodLote(int codLote) {
		this.codLote = codLote;
	}
	public String getNomeTitular() {
		return nomeTitular;
	}
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	public String getEmailTitular() {
		return emailTitular;
	}
	public void setEmailTitular(String emailTitular) {
		this.emailTitular = emailTitular;
	}
	public String getCodBoleto() {
		return codBoleto;
	}
	public void setCodBoleto(String codBoleto) {
		this.codBoleto = codBoleto;
	}
	public Boolean getBoleto() {
		return boleto;
	}
	public void setBoleto(Boolean boleto) {
		this.boleto = boleto;
	}

}
