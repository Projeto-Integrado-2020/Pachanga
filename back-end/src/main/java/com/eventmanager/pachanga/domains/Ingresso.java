package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ingresso")
public class Ingresso {
	
	@Id
	@Column(name = "cod_ingresso")
	private String codIngresso;
	
	@ManyToOne
	@JoinColumn(name = "cod_lote")
	private Lote lote;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@ManyToOne
	@JoinColumn(name = "cod_usuario")
	private Usuario usuario;
	
	@Column(name = "status_ingresso")
	private String statusIngresso;
	
	@Column(name = "preco")
	private float preco;
	
	@Column(name = "status_compra")
	private String statusCompra;
	
	@Column(name = "dthr_compra")
	private LocalDateTime dataCompra;
	
	@Column(name = "dthr_checkin")
	private LocalDateTime dataCheckin;

	public String getCodIngresso() {
		return codIngresso;
	}

	public void setCodIngresso(String codIngresso) {
		this.codIngresso = codIngresso;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
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
