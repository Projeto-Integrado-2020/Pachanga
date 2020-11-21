package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.IngressoTO;

public class IngressoTOBuilder {

	private int codIngresso;
	private Lote lote;
	private Festa festa;
	private Usuario usuario;
	private String statusIngresso;
	private float preco;
	private String statusCompra;
	private LocalDateTime dataCompra;
	private LocalDateTime dataCheckin;
	
	public static IngressoTOBuilder getInstance() {
		return new IngressoTOBuilder();
	}
	
	public IngressoTOBuilder codIngresso(int codIngresso) {
		this.codIngresso = codIngresso;
		return this;
	}
	
	public IngressoTOBuilder lote(Lote lote) {
		this.lote = lote;
		return this;
	}
	
	public IngressoTOBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}
	
	public IngressoTOBuilder usuario(Usuario usuario) {
		this.usuario = usuario;
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
		ingressoTO.setLote(lote);
		ingressoTO.setPreco(preco);
		ingressoTO.setStatusCompra(statusCompra);
		ingressoTO.setStatusIngresso(statusIngresso);
		ingressoTO.setUsuario(usuario);
		return ingressoTO;
		
	}
}
