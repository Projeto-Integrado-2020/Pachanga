package com.eventmanager.pachanga.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.RelatorioDeVendaTOBuilder;
import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;

@Component(value = "relatorioDeVendaFactory")
public class RelatorioDeVendaFactory {
	
	private RelatorioDeVendaFactory() {
	}

	public RelatorioDeVendaTO getRelatorioDeVenda(Map<String, Integer> ingressos) {	
		return RelatorioDeVendaTOBuilder.getInstance()
				.ingressosVendidos(ingressos)
				.build();
	}
}
