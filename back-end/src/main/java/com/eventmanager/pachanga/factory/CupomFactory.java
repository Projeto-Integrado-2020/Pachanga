package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import com.eventmanager.pachanga.builder.CupomBuilder;
import com.eventmanager.pachanga.builder.CupomTOBuilder;
import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CupomTO;

public class CupomFactory {
	
	private CupomFactory() {
	}
	
	
	public static CupomTO getCupomTO(Cupom cupom) {
		return CupomTOBuilder.getInstance()
				             .codCupom(cupom.getCodCupom())
				             .nomeCupom(cupom.getNomeCupom())
				             .codfesta(cupom.getFesta().getCodFesta())
				             .precoDesconto(cupom.getPrecoDesconto())
				             .build();
	}
	
	public static List<CupomTO> getCuponsTO(List<Cupom> cupons){
		return cupons.stream().map(e -> CupomFactory.getCupomTO(e)).collect(Collectors.toList());
	}
	
	public static Cupom getCupom(CupomTO cupomTO, Festa festa) {	
		return CupomBuilder.getInstance()
				             .codCupom(cupomTO.getCodCupom())
				             .nomeCupom(cupomTO.getNomeCupom())
				             .festa(festa)
				             .precoDesconto(cupomTO.getPrecoDesconto())
				             .build();
	}
}
