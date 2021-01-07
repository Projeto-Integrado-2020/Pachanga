package com.eventmanager.pachanga.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.RelatorioCheckInTOBuilder;
import com.eventmanager.pachanga.dtos.RelatorioCheckInTO;

@Component(value = "relatorioCheckInFactory")
public class RelatorioCheckInFactory {

	public RelatorioCheckInTO relatorioIngressosCompradosEntradas(
			Map<String, Map<Integer, Integer>> ingressosLoteFesta) {
		return RelatorioCheckInTOBuilder.getInstance().ingressosLoteFesta(ingressosLoteFesta).build();
	}

	public RelatorioCheckInTO relatorioFaixaEtariaFesta(Map<Integer, Integer> quantidadeFaixaEtaria) {
		return RelatorioCheckInTOBuilder.getInstance().quantidadeFaixaEtaria(quantidadeFaixaEtaria).build();
	}

	public RelatorioCheckInTO relatorioGeneroFesta(Map<String, Integer> quantidadeGenero) {
		return RelatorioCheckInTOBuilder.getInstance().quantidadeGenero(quantidadeGenero).build();
	}

	public RelatorioCheckInTO relatorioEntradaHora(Map<String, Integer> quantidadeEntradaHora) {
		return RelatorioCheckInTOBuilder.getInstance().quantidadePessoasHora(quantidadeEntradaHora).build();
	}
	
	public RelatorioCheckInTO relatorioCheckedUnchecked(Map<String, Map<Integer, Integer>> ingressosLoteFesta) {
		return RelatorioCheckInTOBuilder.getInstance().ingressoFestaCheckedUnchecked(ingressosLoteFesta).build();
	}

}
