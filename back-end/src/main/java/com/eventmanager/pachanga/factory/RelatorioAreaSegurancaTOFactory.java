package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.ChamadasEmitidasFuncionarioTOBuilder;
import com.eventmanager.pachanga.builder.RelatorioAreaSegurancaTOBuilder;
import com.eventmanager.pachanga.dtos.ChamadasEmitidasFuncionarioTO;
import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;

@Component(value = "relatorioAreaSegurancaTOFactory")
public class RelatorioAreaSegurancaTOFactory {

	public RelatorioAreaSegurancaTO getProblemasArea(Map<String, Integer> problemasArea) {
		return RelatorioAreaSegurancaTOBuilder.getInstance().problemasArea(problemasArea).build();
	}

	public RelatorioAreaSegurancaTO getChamadasProblema(List<ChamadasEmitidasFuncionarioTO> chamadas) {
		return RelatorioAreaSegurancaTOBuilder.getInstance().chamadasEmitidasFuncionario(chamadas).build();
	}

	public RelatorioAreaSegurancaTO getUsuarioSolucionador(Map<String, Integer> solucionadorAlertasSeguranca) {
		return RelatorioAreaSegurancaTOBuilder.getInstance().solucionadorAlertasSeguranca(solucionadorAlertasSeguranca)
				.build();
	}

	public ChamadasEmitidasFuncionarioTO getChamadasEmitidas(String nomeUsuario, Integer chamadasFinalizadas,
			Integer chamadasEngano, int codFuncionario) {
		return ChamadasEmitidasFuncionarioTOBuilder.getInstance().chamadasEngano(chamadasEngano)
				.codUsuario(codFuncionario).nomeUsuario(nomeUsuario).chamadasFinalizadas(chamadasFinalizadas).build();
	}

}
