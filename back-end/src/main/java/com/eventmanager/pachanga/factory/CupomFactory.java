package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.CupomBuilder;
import com.eventmanager.pachanga.builder.CupomTOBuilder;
import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CupomTO;

@Component(value = "cupomFactory")
public class CupomFactory {
	
	public CupomTO getCupomTO(Cupom cupom) {
		return CupomTOBuilder.getInstance()
				             .codCupom(cupom.getCodCupom())
				             .nomeCupom(cupom.getNomeCupom().toUpperCase())
				             .codfesta(cupom.getFesta().getCodFesta())
				             .precoDesconto(cupom.getPrecoDesconto())
				             .porcentagemDesc(cupom.getPorcentagemDesc())
				             .tipoDesconto(cupom.getTipoDesconto())
				             .build();
	}
	
	public List<CupomTO> getCuponsTO(List<Cupom> cupons){
		return cupons.stream().map(e -> this.getCupomTO(e)).collect(Collectors.toList());
	}
	
	public Cupom getCupom(CupomTO cupomTO, Festa festa) {	
		return CupomBuilder.getInstance()
				             .codCupom(cupomTO.getCodCupom())
				             .nomeCupom(cupomTO.getNomeCupom())
				             .festa(festa)
				             .precoDesconto(cupomTO.getPrecoDesconto())
				             .porcentagemDesc(cupomTO.getPorcentagemDesc())
				             .tipoDesconto(cupomTO.getTipoDesconto())
				             .build();
	}
}
