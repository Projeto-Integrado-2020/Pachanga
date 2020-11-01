package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.LoteBuilder;
import com.eventmanager.pachanga.builder.LoteTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.dtos.LoteTO;

@Component(value = "loteFactory")
public class LoteFactory {
	
	public Lote getLote(LoteTO loteTo, Festa festa) {
		return LoteBuilder.getInstance().festa(festa).codLote(loteTo.getCodLote()).descLote(loteTo.getDescLote())
				.horarioFim(loteTo.getHorarioFim()).horarioInicio(loteTo.getHorarioInicio()).nomeLote(loteTo.getNomeLote())
				.numeroLote(loteTo.getNumeroLote()).preco(loteTo.getPreco()).quantidade(loteTo.getQuantidade()).build();
	}
	
	public LoteTO getLoteTO(Lote lote) {
		return LoteTOBuilder.getInstance().codFesta(lote.getFesta().getCodFesta()).codLote(lote.getCodLote()).descLote(lote.getDescLote())
				.horarioFim(lote.getHorarioFim()).horarioInicio(lote.getHorarioInicio()).nomeLote(lote.getNomeLote())
				.numeroLote(lote.getNumeroLote()).preco(lote.getPreco()).quantidade(lote.getQuantidade()).build();
	}

}
