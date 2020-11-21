package com.eventmanager.pachanga.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.RelatorioAreaSegurancaTOBuilder;
import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;

@Component(value = "relatorioAreaSegurancaTOFactory")
public class RelatorioAreaSegurancaTOFactory {

	public RelatorioAreaSegurancaTO getProblemasArea(Map<String, Integer> problemasArea) {
		return RelatorioAreaSegurancaTOBuilder.getInstance().problemasArea(problemasArea).build();
	}

	public RelatorioAreaSegurancaTO getChamadasProblema(
			Map<String, Map<Integer, Integer>> chamadasEmitidasFuncionario) {
		return RelatorioAreaSegurancaTOBuilder.getInstance().chamadasEmitidasFuncionario(chamadasEmitidasFuncionario)
				.build();
	}

	public RelatorioAreaSegurancaTO getUsuarioSolucionador(Map<String, Integer> solucionadorAlertasSeguranca) {
		return RelatorioAreaSegurancaTOBuilder.getInstance().solucionadorAlertasSeguranca(solucionadorAlertasSeguranca)
				.build();
	}

}
