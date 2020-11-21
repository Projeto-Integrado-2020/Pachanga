package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;

public class IngressoTO {

	private int codIngresso;
	private Lote lote;
	private Festa festa;
	private Usuario usuario;
	private String statusIngresso;
	private float preco;
	private String statusCompra;
	private LocalDateTime dataCompra;
	private LocalDateTime dataCheckin;
	
	public Festa getFesta() {
		return festa;
	}
	public void setFesta(Festa festa) {
		this.festa = festa;
	}
	public int getCodIngresso() {
		return codIngresso;
	}
	public void setCodIngresso(int codIngresso) {
		this.codIngresso = codIngresso;
	}
	public Lote getLote() {
		return lote;
	}
	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

}
