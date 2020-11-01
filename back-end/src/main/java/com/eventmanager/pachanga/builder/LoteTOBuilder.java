package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.dtos.LoteTO;

public class LoteTOBuilder {
	
	private int codLote;

	private int codFesta;

	private int numeroLote;

	private int quantidade;

	private float preco;

	private String nomeLote;

	private String descLote;

	private LocalDateTime horarioInicio;

	private LocalDateTime horarioFim;
	
	public static LoteTOBuilder getInstance() {
		return new LoteTOBuilder();
	}

	public LoteTOBuilder codLote(int codLote) {
		this.codLote = codLote;
		return this;
	}

	public LoteTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public LoteTOBuilder numeroLote(int numeroLote) {
		this.numeroLote = numeroLote;
		return this;
	}

	public LoteTOBuilder quantidade(int quantidade) {
		this.quantidade = quantidade;
		return this;
	}

	public LoteTOBuilder preco(float preco) {
		this.preco = preco;
		return this;
	}

	public LoteTOBuilder nomeLote(String nomeLote) {
		this.nomeLote = nomeLote;
		return this;
	}

	public LoteTOBuilder descLote(String descLote) {
		this.descLote = descLote;
		return this;
	}

	public LoteTOBuilder horarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
		return this;
	}

	public LoteTOBuilder horarioFim(LocalDateTime horarioFim) {
		this.horarioFim = horarioFim;
		return this;
	}
	
	public LoteTO build() {
		LoteTO loteTo = new LoteTO();
		loteTo.setCodFesta(codFesta);
		loteTo.setCodLote(codLote);
		loteTo.setDescLote(descLote);
		loteTo.setHorarioFim(horarioFim);
		loteTo.setHorarioInicio(horarioInicio);
		loteTo.setNomeLote(nomeLote);
		loteTo.setNumeroLote(numeroLote);
		loteTo.setPreco(preco);
		loteTo.setQuantidade(quantidade);
		return loteTo;
	}
	
}
