package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Lote;

public class LoteBuilder {
	
	private int codLote;

	private Festa festa;

	private int numeroLote;

	private int quantidade;

	private float preco;

	private String nomeLote;

	private String descLote;

	private LocalDateTime horarioInicio;

	private LocalDateTime horarioFim;
	
	public static LoteBuilder getInstance() {
		return new LoteBuilder();
	}

	public LoteBuilder codLote(int codLote) {
		this.codLote = codLote;
		return this;
	}

	public LoteBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}

	public LoteBuilder numeroLote(int numeroLote) {
		this.numeroLote = numeroLote;
		return this;
	}

	public LoteBuilder quantidade(int quantidade) {
		this.quantidade = quantidade;
		return this;
	}

	public LoteBuilder preco(float preco) {
		this.preco = preco;
		return this;
	}

	public LoteBuilder nomeLote(String nomeLote) {
		this.nomeLote = nomeLote;
		return this;
	}

	public LoteBuilder descLote(String descLote) {
		this.descLote = descLote;
		return this;
	}

	public LoteBuilder horarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
		return this;
	}

	public LoteBuilder horarioFim(LocalDateTime horarioFim) {
		this.horarioFim = horarioFim;
		return this;
	}
	
	public Lote build() {
		Lote lote = new Lote();
		lote.setCodLote(codLote);
		lote.setDescLote(descLote);
		lote.setFesta(festa);
		lote.setHorarioFim(horarioFim);
		lote.setHorarioInicio(horarioInicio);
		lote.setNomeLote(nomeLote);
		lote.setNumeroLote(numeroLote);
		lote.setPreco(preco);
		lote.setQuantidade(quantidade);
		return lote;
	}

}
