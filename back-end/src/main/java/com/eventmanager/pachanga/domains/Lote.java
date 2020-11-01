package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lote")
public class Lote {
	
	@Id
	@Column(name = "cod_lote")
	private int codLote;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@Column(name = "numero_lote")
	private int numeroLote;
	
	@Column(name = "quantidade")
	private int quantidade;
	
	@Column(name = "preco")
	private float preco;
	
	@Column(name = "nome_lote")
	private String nomeLote;
	
	@Column(name = "desc_lote")
	private String descLote;
	
	@Column(name = "dthr_inicio")
	private LocalDateTime horarioInicio;
	
	@Column(name = "dthr_fim")
	private LocalDateTime horarioFim;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "lote")
	private Set<Ingresso> ingressos;

	public int getCodLote() {
		return codLote;
	}

	public void setCodLote(int codLote) {
		this.codLote = codLote;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public int getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(int numeroLote) {
		this.numeroLote = numeroLote;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public String getNomeLote() {
		return nomeLote;
	}

	public void setNomeLote(String nomeLote) {
		this.nomeLote = nomeLote;
	}

	public String getDescLote() {
		return descLote;
	}

	public void setDescLote(String descLote) {
		this.descLote = descLote;
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

	public Set<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(Set<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}

}
