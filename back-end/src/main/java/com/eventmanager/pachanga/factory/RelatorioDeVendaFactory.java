package com.eventmanager.pachanga.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.InfoLucroFestaBuilder;
import com.eventmanager.pachanga.builder.RelatorioDeVendaTOBuilder;
import com.eventmanager.pachanga.dtos.InfoLucroFesta;
import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;

@Component(value = "relatorioDeVendaFactory")
public class RelatorioDeVendaFactory {

	private RelatorioDeVendaFactory() {
	}

	public RelatorioDeVendaTO getRelatorioDeVenda(Map<String, Integer> ingressos) {
		return RelatorioDeVendaTOBuilder.getInstance().ingressosVendidos(ingressos).build();
	}

	public RelatorioDeVendaTO getIngressosPagosComprados(Map<String, Map<Integer, Integer>> ingressos) {
		return RelatorioDeVendaTOBuilder.getInstance().ingressosCompradosPagos(ingressos).build();
	}

	public InfoLucroFesta getRelatorioLucroFesta(Float lucroTotal, Map<String, Float> lucroLote) {
		return InfoLucroFestaBuilder.getInstance().lucroLote(lucroLote).lucroTotal(lucroTotal).build();
	}

	public RelatorioDeVendaTO getRelatorioLucroTotalFesta(InfoLucroFesta infoLucroEsperado,
			InfoLucroFesta infoLucroReal, String nomeFesta) {
		return RelatorioDeVendaTOBuilder.getInstance().nomeFesta(nomeFesta) 
				.infoLucroEsperado(infoLucroEsperado)
				.infoLucroReal(infoLucroReal)
				.build();
	}
}
