package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.IngressoTO;

public class IngressoTOBuilder {

	private String codIngresso;
	private int codLote;
	private FestaTO festa;
	private int codUsuario;
	private String statusIngresso;
	private float preco;
	private String statusCompra;
	private LocalDateTime dataCompra;
	private LocalDateTime dataCheckin;
	
	public static IngressoTOBuilder getInstance() {
		return new IngressoTOBuilder();
	}
	
	public IngressoTOBuilder codIngresso(String codIngresso) {
		this.codIngresso = codIngresso;
		return this;
	}
	
	public IngressoTOBuilder lote(int codLote) {
		this.codLote = codLote;
		return this;
	}
	
	public IngressoTOBuilder festa(FestaTO festa) {
		this.festa = festa;
		return this;
	}
	
	public IngressoTOBuilder usuario(int codUsuario) {
		this.codUsuario = codUsuario;
		return this;
	}
	
	public IngressoTOBuilder statusIngresso(String statusIngresso) {
		this.statusIngresso = statusIngresso;
		return this;
	}
	
	public IngressoTOBuilder preco(float preco) {
		this.preco = preco;
		return this;
	}
	
	public IngressoTOBuilder statusCompra(String statusCompra) {
		this.statusCompra = statusCompra;
		return this;
	}
	
	public IngressoTOBuilder dataCompra(LocalDateTime dataCompra) {
		this.dataCompra = dataCompra;
		return this;
	}
	
	public IngressoTOBuilder dataCheckin(LocalDateTime dataCheckin) {
		this.dataCheckin = dataCheckin;
		return this;
	}
	
	public IngressoTO build() {
		IngressoTO ingressoTO = new IngressoTO();
		ingressoTO.setCodIngresso(codIngresso);
		ingressoTO.setDataCheckin(dataCheckin);
		ingressoTO.setDataCompra(dataCompra);
		ingressoTO.setFesta(festa);
		ingressoTO.setCodLote(codLote);
		ingressoTO.setPreco(preco);
		ingressoTO.setStatusCompra(statusCompra);
		ingressoTO.setStatusIngresso(statusIngresso);
		ingressoTO.setCodUsuario(codUsuario);
		return ingressoTO;
		
	}
}
